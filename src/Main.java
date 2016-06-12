import as.project.datalayer.FactoriaDades;
import as.project.domain.model.Projector;
import as.project.domain.model.Reserva;
import as.project.domain.model.Usuari;
import as.project.presentation.CtrlCrearReservaAmbNotificacio;
import as.project.presentation.View;
import org.hibernate.Session;

import java.util.Date;

/**
 * Created by sergi on 12/06/16.
 */
public class Main {
    public static void main (String[] args) {
        addData();
        javax.swing.SwingUtilities.invokeLater (
                new Runnable() {
                    public void run() {
                        CtrlCrearReservaAmbNotificacio ctrl = new CtrlCrearReservaAmbNotificacio();
                    }});
    }

    static void addData() {

        Projector recurs = new Projector();
        recurs.setNom("Projector");
        recurs.setResolucio("800x600");

        Usuari u1 = new Usuari();
        u1.setEmail("guillem.cordoba@est.fib.upc.edu");
        u1.setNom("as.project.domain.model.Usuari 1");
        u1.setUsername("usuari1");

        Usuari u2 = new Usuari();
        u2.setEmail("usuari.2@est.fib.upc.edu");
        u2.setNom("as.project.domain.model.Usuari 2");
        u2.setUsername("usuari2");

        Reserva r1 = new Reserva();
        r1.setRecurs(recurs);
        r1.setData(new Date(2016, 8, 01));
        r1.setHoraInici(8);
        r1.setHoraFi(10);
        r1.setComentari("Comment1");
        r1.setUsuariCreador(u1);
        //u1.assignaReserva(r1);

        Reserva r2 = new Reserva();
        r2.setRecurs(recurs);
        r2.setData(new Date(2016, 10, 01));
        r2.setHoraInici(16);
        r2.setHoraFi(19);
        r2.setComentari("Comment2");
        r2.setUsuariCreador(u2);
        //u2.assignaReserva(r2);

        FactoriaDades.getInstance().openSession();
        Session session = FactoriaDades.getInstance().getCurrentSession();
        session.beginTransaction();

        session.save(recurs);
        session.save(u1);
        session.save(u2);
        session.save(r1);
        session.save(r2);

        session.getTransaction().commit();
        FactoriaDades.getInstance().closeSession();
    }
}
