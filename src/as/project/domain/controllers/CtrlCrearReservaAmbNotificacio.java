package as.project.domain.controllers;

import as.project.datalayer.FactoriaDades;
import as.project.domain.excepcions.NoHiHaProusUsuaris;
import as.project.domain.excepcions.NoHiHaRecursos;
import as.project.domain.excepcions.ReservaATope;
import as.project.domain.model.ReservaAmbNotificacio;
import as.project.domain.model.Usuari;
import as.project.domain.services.adapters.FactoriaAdapters;
import as.project.domain.services.adapters.IServeiMissatgeriaAdapter;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guillemc on 07/06/2016.
 */
public class CtrlCrearReservaAmbNotificacio {

    private Date data;
    private int horaInici;
    private int horaFi;
    private String nomRecurs;
    private String comentari;
    private String username;
    private String email;

    private CtrlAssignarUsuarisANotificarUnaReserva instanciaCtrlAssignar;

    public List<List<String>> obteRecursosDisponibles(Date data, int horaInici, int horaFi) throws NoHiHaRecursos {
        CtrlConsultarRecursosDisponiblesPerData ctrlConsultarRecursos = new CtrlConsultarRecursosDisponiblesPerData();
        ctrlConsultarRecursos.obteRecursosDisponiblesPerData(data, horaInici, horaFi);

        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
    }

    public void creaReservaAmbNotificacio(String nomRecurs, String username, String comentari) {

        // Obtenim la sessió actual
        Session session = FactoriaDades.getInstance().getCurrentSession();

        Usuari usuari = (Usuari) session.get(Usuari.class, username);
        // Recurs recurs = (Recurs) session.get(Recurs.class, nomRecurs);

        // TODO: SI NO S'ACONSEGUEIX IMPLEMENTAR EL TRIGGER DE L'EXCEPCIÓ RECURSSALASOLAPADA, SE N'HA D'IMPLEMENTAR
        // LA LÒGICA AQUÍ

        // Crea la nova reserva amb notificació i hi afegeix l'usuari creador
        ReservaAmbNotificacio ran = new ReservaAmbNotificacio(nomRecurs, data, horaInici, horaFi, comentari, usuari);

        // TODO: SI NO S'ACONSEGUEIX IMPLEMENTAR EL TRIGGER DE L'EXCEPCIÓ RECURSSALASOLAPADA,
        // S'HA D'AFEGIR LA RESERVA A L'USUARI AQUÍ

        List<Usuari> usuaris = new ArrayList<>();
        usuaris.add(usuari);

        ran.afegeixUsuaris(usuaris);

        // Guarda la nova reserva amb notificació
        session.save(ran);

        // Notifica la reserva a l'usuari creador

        List<String> emails = new ArrayList<>();
        emails.add(usuari.getEmail());

        IServeiMissatgeriaAdapter adapter = FactoriaAdapters.getInstance().getServeiMissatgeriaAdapter();
        adapter.enviarDadesReserva(nomRecurs, data, horaInici, horaFi, usuari.getUsername(), comentari, emails);

    }

    public List<List<String>> obteUsuarisPerAssignar() throws NoHiHaProusUsuaris {
        // Salvem la instància del controlador per a poder-lo fer servir en la funció assignarUsuarisAReserva
        instanciaCtrlAssignar = new CtrlAssignarUsuarisANotificarUnaReserva();
        List<List<String>> result = null;
        try {
            result = instanciaCtrlAssignar.obteUsuarisAAssignar(nomRecurs, data, horaInici);
        } catch (NoHiHaProusUsuaris e) {
            throw e;
        } catch (Exception e) {} // Cap de les altres excepcions de la funcio cridada es podra donar en aquest punt del codi

        return result;
    }

    public void assignarUsuarisAReserva(List<String> usuarisUsernames) throws ReservaATope {
        instanciaCtrlAssignar.afegirUsuarisAReserva(usuarisUsernames);
    }

}
