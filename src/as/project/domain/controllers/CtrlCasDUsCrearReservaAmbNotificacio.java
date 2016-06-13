package as.project.domain.controllers;

import as.project.datalayer.FactoriaDades;
import as.project.domain.excepcions.*;
import as.project.domain.model.*;
import as.project.domain.services.ServeiMissatgeriaStub;
import as.project.domain.services.adapters.FactoriaAdapters;
import as.project.domain.services.adapters.IServeiMissatgeriaAdapter;
import org.hibernate.CallbackException;
import org.hibernate.Session;

import java.util.*;

/**
 * Created by guillemc on 07/06/2016.
 */
public class CtrlCasDUsCrearReservaAmbNotificacio {

    private Date data;
    private int horaInici;
    private int horaFi;
    private String nomRecurs;
    private String comentari;
    private String username;
    private String email;

    private CtrlCasDUsAssignarUsuarisANotificarUnaReserva instanciaCtrlAssignar;

    public List<InfoRecurs> obteRecursosDisponibles(Date data, int horaInici, int horaFi) throws NoHiHaRecursos {
        CtrlCasDUsConsultarRecursosDisponiblesPerData ctrlConsultarRecursos = new CtrlCasDUsConsultarRecursosDisponiblesPerData();
        List<InfoRecurs> infosRecursos = ctrlConsultarRecursos.obteRecursosDisponiblesPerData(data, horaInici, horaFi);

        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;

        return infosRecursos;
    }

    public void creaReservaAmbNotificacio(String nomRecurs, String username, String comentari) throws RecursSalaSolapada, UsuariNoExisteix, ServeiNoDisponible {

        // Obtenim la sessió actual
        Session session = FactoriaDades.getInstance().getCurrentSession();
        Usuari usuari = (Usuari) session.get(Usuari.class, username);
        Recurs recurs = (Recurs) session.get(Recurs.class, nomRecurs);

        // Crea la nova reserva amb notificació i hi afegeix l'usuari creador
        ReservaAmbNotificacio ran = new ReservaAmbNotificacio(recurs, data, horaInici, horaFi, comentari, usuari);

        try {

            // Guarda la nova reserva amb notificació
            session.save(ran);

            // Notifica la reserva a l'usuari creador
            List<String> emails = new ArrayList<>();
            emails.add(usuari.getEmail());

            IServeiMissatgeriaAdapter adapter = FactoriaAdapters.getInstance().getServeiMissatgeriaAdapter();
            adapter.enviarDadesReserva(nomRecurs, data, horaInici, horaFi, username, comentari, emails);

            this.nomRecurs = nomRecurs;
            this.comentari = comentari;
            this.username = username;
            this.email = usuari.getEmail();

        } catch (Exception exception) {
            if (exception.getMessage() != null) {
                if (exception.getMessage().equals("UsuariNoExisteix")) throw new UsuariNoExisteix();
                if (exception.getMessage().equals("RecursSalaSolapada")) throw new RecursSalaSolapada();
            }
        }

    }

    public List<InfoUsuari> obteUsuarisPerAssignar() throws NoHiHaProusUsuaris {
        // Salvem la instància del controlador per a poder-lo fer servir en la funció assignarUsuarisAReserva
        instanciaCtrlAssignar = new CtrlCasDUsAssignarUsuarisANotificarUnaReserva();
        List<InfoUsuari> result = null;
        try {
            result = instanciaCtrlAssignar.obteUsuarisAAssignar(nomRecurs, data, horaInici);
        } catch (NoHiHaProusUsuaris e) {
            throw e;
        } catch (Exception e) {} // Cap de les altres excepcions de la funcio cridada es podra donar en aquest punt del codi

        return result;
    }

    public void assignarUsuarisAReserva(List<String> usuarisUsernames) throws ReservaATope, ServeiNoDisponible {
        instanciaCtrlAssignar.afegirUsuarisAReserva(usuarisUsernames);
    }

}
