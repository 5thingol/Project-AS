package as.project.domain.controllers;

import as.project.datalayer.FactoriaDades;
import as.project.domain.excepcions.*;
import as.project.domain.model.*;
import as.project.domain.services.adapters.FactoriaAdapters;
import as.project.domain.services.adapters.IServeiMissatgeriaAdapter;
import org.hibernate.Session;

import java.util.*;

/**
 * Created by guillemc on 07/06/2016.
 */
public class CtrlCasDUsAssignarUsuarisANotificarUnaReserva {

    private String nomRecurs;
    private Date data;
    private int horaInici;

    public List<InfoUsuari> obteUsuarisAAssignar(String nomRecurs, Date data, int horaInici) throws NoReservaAmbNotificacio, NoHiHaReserva, ReservaCaducada, ReservaATope, NoHiHaProusUsuaris {
        data.setHours(0);
        data.setMinutes(0);
        data.setSeconds(0);

        // Obtenim la sessió actual
        Session session = FactoriaDades.getInstance().getCurrentSession();

        // Creem una reserva buida excepte els seus ids per a que hibernate ens torni la reserva amb aquests ids
        Recurs recursStub = new Sala();
        recursStub.setNom(nomRecurs);
        Reserva reservaStub = new Reserva(recursStub, data, horaInici, 0, "", null);

        Reserva reserva = (Reserva) session.get(Reserva.class, reservaStub);
        ReservaAmbNotificacio reservaStub1 = new ReservaAmbNotificacio(recursStub, data, horaInici, 0, "", null);
        ReservaAmbNotificacio ran = (ReservaAmbNotificacio) session.get(ReservaAmbNotificacio.class, reservaStub1);

        if (ran == null) {
            if (reserva == null) throw new NoHiHaReserva();
            else throw new NoReservaAmbNotificacio();
        }

        Date currentDate = Calendar.getInstance().getTime();

        data.setHours(horaInici);
        if (data.before(currentDate)) throw new ReservaCaducada();

        Set<Usuari> usuarisNotificats = new HashSet<>();

        try {
            if (ran.estaATope()) throw new ReservaATope();

             usuarisNotificats = ran.getUsuarisNotificats();

        } catch (Exception e) {
            usuarisNotificats = new HashSet<>();
            usuarisNotificats.add(ran.getUsuariCreador());

        }

        List<Usuari> totsUsuaris = session.createCriteria(Usuari.class).list();

        if (totsUsuaris.size() == 0) throw new NoHiHaProusUsuaris();

        for (Usuari u : usuarisNotificats) {
            totsUsuaris.remove(u);
        }

        List<InfoUsuari> usuarisAAssignar = new ArrayList<>();

        for (Usuari u : totsUsuaris) {
            InfoUsuari infoU = u.getInfo();
            usuarisAAssignar.add(infoU);
        }
        data.setHours(0);

        this.nomRecurs = nomRecurs;
        this.data = data;
        this.horaInici = horaInici;
        return usuarisAAssignar;
    }

    public void afegirUsuarisAReserva(List<String> usernames) throws ReservaATope, ServeiNoDisponible {

        // Obtenim la sessió actual
        Session session = FactoriaDades.getInstance().getCurrentSession();

        // Creem una reserva buida excepte els seus ids per a que hibernate ens torni la reserva amb aquests ids
        Recurs recursStub = new Sala();
        recursStub.setNom(nomRecurs);
        ReservaAmbNotificacio reservaStub = new ReservaAmbNotificacio();
        reservaStub.setRecurs(recursStub);
        reservaStub.setData(data);
        reservaStub.setHoraInici(horaInici);

        ReservaAmbNotificacio ran = (ReservaAmbNotificacio) session.get(ReservaAmbNotificacio.class, reservaStub);

        int nUsuarisNotificats = ran.getNumUsuarisNotificats();
        if (nUsuarisNotificats + usernames.size() > 10) throw new ReservaATope();

        Set<Usuari> usuarisAAfegir = new HashSet<>();
        List<String> emails = new ArrayList<>();

        for (String username : usernames) {
            Usuari usuari = (Usuari) session.get(Usuari.class, username);
            usuarisAAfegir.add(usuari);
            emails.add(usuari.getEmail());
        }

        ran.afegeixUsuaris(usuarisAAfegir);
        session.merge(ran);

        InfoReservaAmbNotificacio info = ran.getInfo();

        IServeiMissatgeriaAdapter adapter = FactoriaAdapters.getInstance().getServeiMissatgeriaAdapter();
        adapter.enviarDadesReserva(nomRecurs, data, horaInici, info.getHoraFi(), info.getUsername(), info.getComentari(), emails);
    }
}
