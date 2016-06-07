package as.project.domain.controllers;

import as.project.datalayer.FactoriaDades;
import as.project.datalayer.HibernateUtil;
import as.project.domain.excepcions.*;
import as.project.domain.model.Recurs;
import as.project.domain.model.Reserva;
import as.project.domain.model.ReservaAmbNotificacio;
import as.project.domain.model.Usuari;
import as.project.domain.services.adapters.FactoriaAdapters;
import as.project.domain.services.adapters.IServeiMissatgeriaAdapter;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by guillemc on 07/06/2016.
 */
public class CtrlAssignarUsuarisANotificarUnaReserva {

    private String nomRecurs;
    private Date data;
    private int horaInici;

    public List<List<String>> obteUsuarisAAssignar(String nomRecurs, Date data, int horaInici) throws NoReservaAmbNotificacio, NoHiHaReserva, ReservaCaducada, ReservaATope, NoHiHaProusUsuaris {

        // Obtenim la sessió actual
        Session session = FactoriaDades.getInstance().getCurrentSession();

        // Creem una reserva buida excepte els seus ids per a que hibernate ens torni la reserva amb aquests ids
        Reserva reservaStub = new Reserva(new Recurs(nomRecurs),data,horaInici,0,"",null);

        Reserva reserva = (Reserva) session.get(Reserva.class, reservaStub);
        ReservaAmbNotificacio ran = (ReservaAmbNotificacio) session.get(ReservaAmbNotificacio.class, reservaStub);

        if (ran == null) {
            if (reserva == null) throw new NoHiHaReserva();
            else throw new NoReservaAmbNotificacio();
        }

        Date currentDate = Calendar.getInstance().getTime();

        data.setHours(horaInici);
        if (data.before(currentDate)) throw new ReservaCaducada();

        if (ran.estaATope()) throw new ReservaATope();

        List<Usuari> totsUsuaris = session.createCriteria(Usuari.class).list();

        List<Usuari> usuarisNotificats = ran.getUsuarisNotificats();

        for (Usuari u : usuarisNotificats) {
            totsUsuaris.remove(u);
        }

        if (totsUsuaris.size() == 0) throw new NoHiHaProusUsuaris();

        List<List<String>> usuarisAAssignar = new ArrayList<>();

        for (Usuari u : totsUsuaris) {
            List<String> infoU = new ArrayList<>();
            infoU.add(u.getUsername());
            infoU.add(u.getNom());
            infoU.add(u.getEmail());
            usuarisAAssignar.add(infoU);
        }

        this.nomRecurs = nomRecurs;
        this.data = data;
        this.horaInici = horaInici;

        return usuarisAAssignar;
    }

    public void afegirUsuarisAReserva(List<String> usernames) throws ReservaATope {

        // Obtenim la sessió actual
        Session session = FactoriaDades.getInstance().getCurrentSession();

        // Creem una reserva buida excepte els seus ids per a que hibernate ens torni la reserva amb aquests ids
        ReservaAmbNotificacio reservaStub = new ReservaAmbNotificacio(new Recurs(nomRecurs),data,horaInici,0,"",null);

        ReservaAmbNotificacio ran = (ReservaAmbNotificacio) session.get(ReservaAmbNotificacio.class, reservaStub);

        int nUsuarisNotificats = ran.getNumUsuarisNotificats();
        if (nUsuarisNotificats + usernames.size() > 10) throw new ReservaATope();

        List<Usuari> usuarisAAfegir = new ArrayList<>();
        List<String> emails = new ArrayList<>();

        for (String username : usernames) {
            Usuari usuari = (Usuari) session.get(Usuari.class, username);
            usuarisAAfegir.add(usuari);
            emails.add(usuari.getEmail());
        }

        ran.afegeixUsuaris(usuarisAAfegir);

        int horaFi = ran.getHoraFi();
        String usernameCreador = ran.getUsernameUsuari();
        String comentari = ran.getComentari();

        IServeiMissatgeriaAdapter adapter = FactoriaAdapters.getInstance().getServeiMissatgeriaAdapter();
        adapter.enviarDadesReserva(nomRecurs, data, horaInici, horaFi, usernameCreador, comentari, emails);
    }
}
