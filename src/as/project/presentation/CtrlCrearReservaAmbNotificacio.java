package as.project.presentation;

import as.project.domain.controllers.CtrlCasDUsCrearReservaAmbNotificacio;
import as.project.domain.excepcions.*;
import as.project.domain.model.InfoRecurs;
import as.project.domain.model.InfoUsuari;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CtrlCrearReservaAmbNotificacio {


    /**
     * Launch the application.
     */
  /*  public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    View frame = new View(this);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
	View view;
	CtrlCasDUsCrearReservaAmbNotificacio ctrlRN;
	public CtrlCrearReservaAmbNotificacio (){
		view = new View (this);
		ctrlRN = new CtrlCasDUsCrearReservaAmbNotificacio();
	}
	
	public void PrAcceptObteRecursosDisponibles (Date data, int horaIni, int horaFi){
		Date today = new Date();
		String msg = "";
		if (data.before(today)) msg += "Data passada "; 
		if (horaIni >= horaFi) msg += "periodeErroni ";
		List<InfoRecurs> recursos = null;
		try {
			recursos = ctrlRN.obteRecursosDisponibles(data, horaIni, horaFi);
		} catch (NoHiHaRecursos e) {
			view.MostraMissatge("NoHiHaRecursos", 1);
		}
		//if (recursos.size() == 0) msg += "noHiHaRecursos";
		//if (msg != "") view.MostraMissatge(msg, 0);
		//else {
			String[] ll_recursos = new String[recursos.size()];
			for (int i = 0; i < recursos.size(); i++){
				 ll_recursos[i] = (recursos.get(i).getNom());
			}
			view.mostraRecursos(ll_recursos);
			view.continua();
		//}
	}
	
	public void PrAcceptCreaReservaAmbNotificacio (String nomR, String username, String comentari){
		
		try {
			ctrlRN.creaReservaAmbNotificacio(nomR, username, comentari);
		}
		catch(UsuariNoExisteix excepcio) {view.MostraMissatge("UsuariNoExisteix", 1);}
		catch(RecursSalaSolapada excepcio) {view.MostraMissatge("RecursSalaSolapada", 1);}
		catch(ServeiNoDisponible excepcio) {view.MostraMissatge("ServeiNoDisponible", 1);}
		List<InfoUsuari> usuaris = null;
		try {
			usuaris = ctrlRN.obteUsuarisPerAssignar();
		}
		catch(NoHiHaProusUsuaris excepcio) {view.MostraMissatge("noHiHaUsuaris", 2);}
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
		catch(ServeiNoDisponible excepcio) {view.MostraMissatge("ServeiNoDisponible", 2);}
		catch(ReservaATope excepcio) {view.MostraMissatge("ReservaATope", 2);}
		view.continua();
	}
}
