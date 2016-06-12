package as.project.presentation;

import as.project.datalayer.FactoriaDades;
import as.project.domain.controllers.CtrlCasDUsCrearReservaAmbNotificacio;
import as.project.domain.excepcions.*;
import as.project.domain.model.InfoRecurs;
import as.project.domain.model.InfoUsuari;
import org.hibernate.Session;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CtrlCrearReservaAmbNotificacio {

    Session session;

    /**
     * Launch the application.
     */
	View view;
	CtrlCasDUsCrearReservaAmbNotificacio ctrlRN;
	public CtrlCrearReservaAmbNotificacio (){
		view = new View (this);
		ctrlRN = new CtrlCasDUsCrearReservaAmbNotificacio();
	}
	
	public void PrAcceptObteRecursosDisponibles (Date data, int horaIni, int horaFi){

        FactoriaDades.getInstance().openSession();
        session = FactoriaDades.getInstance().getCurrentSession();
        session.beginTransaction();

        Date today = new Date();
		String msg = "";
		if (data == null || data.before(today)) msg += "Data incorrecte ";
		if (horaIni >= horaFi) msg += "periodeErroni ";
		List<InfoRecurs> recursos = null;
		if (msg == "") {
			try {
				recursos = ctrlRN.obteRecursosDisponibles(data, horaIni, horaFi);
			} catch (NoHiHaRecursos e) {
				view.MostraMissatge("NoHiHaRecursos", 0);
                session.getTransaction().rollback();
                return;
			}

			//if (recursos.size() == 0) msg += "noHiHaRecursos";
			//if (msg != "") view.MostraMissatge(msg, 0);
			//else {
			String[] ll_recursos = new String[recursos.size()];
			for (int i = 0; i < recursos.size(); i++) {
				ll_recursos[i] = (recursos.get(i).getNom());
			}
			view.mostraRecursos(ll_recursos);
			view.continua();
			//}
		}
		else view.MostraMissatge(msg, 0);
	}
	
	public void PrAcceptCreaReservaAmbNotificacio (String nomR, String username, String comentari){
		
		try {
			ctrlRN.creaReservaAmbNotificacio(nomR, username, comentari);
		}
		catch(UsuariNoExisteix excepcio) {
            view.MostraMissatge("UsuariNoExisteix", 1);
            session.getTransaction().rollback();
            return;
        }
		catch(RecursSalaSolapada excepcio) {
            view.MostraMissatge("RecursSalaSolapada", 1);
            session.getTransaction().rollback();
            return;
        }
		catch(ServeiNoDisponible excepcio) {
            view.MostraMissatge("ServeiNoDisponible", 1);
            session.getTransaction().rollback();
            return;
        }
		List<InfoUsuari> usuaris = null;
		try {
			usuaris = ctrlRN.obteUsuarisPerAssignar();
		}
		catch(NoHiHaProusUsuaris excepcio) {
            view.MostraMissatge("noHiHaUsuaris", 2);
            session.getTransaction().rollback();
            return;
        }
		String[] usernames = new String[usuaris.size()];
		for (int i = 0; i < usuaris.size(); i++){
			 usernames[i] = (usuaris.get(i).getNom());
		}
		view.mostraSeleccionaUsuarisPerNotificar(usernames);
		view.continua();
	}
	
	public void PrAcceptAssignarUsuarisAReserva (ArrayList <String> usernames){
		try {
			ctrlRN.assignarUsuarisAReserva(usernames);
		}
		catch(ServeiNoDisponible excepcio) {
            view.MostraMissatge("ServeiNoDisponible", 2);
            session.getTransaction().rollback();
            return;
        }
		catch(ReservaATope excepcio) {
            view.MostraMissatge("ReservaATope", 2);
            session.getTransaction().rollback();
            return;
        }
		view.continua();
        session.getTransaction().commit();
	}
}
