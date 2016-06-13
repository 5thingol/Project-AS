import as.project.datalayer.FactoriaDades;
import as.project.domain.model.*;
import as.project.presentation.CtrlCrearReservaAmbNotificacio;
import as.project.presentation.View;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

        FactoriaDades.getInstance().openSession();
        Session session = FactoriaDades.getInstance().getCurrentSession();
        session.beginTransaction();

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


        Sala s1 = new Sala();
        s1.setNom("nom_s1");
        s1.setProjector(recurs);
        s1.setUbicacio("ubicacio_s1");
        s1.setAforament(1);

        session.save(recurs);
        session.save(u1);
        session.save(u2);
        session.save(s1);

        for (int i = 0; i < 15; i++) {
            Ordinador o = new Ordinador("nom_o"+i, "marca_o"+i, "model_o"+i);
            Projector p = new Projector("resolucio_p"+i);
            p.setNom("nom_p"+i);
            session.save(o);
            session.save(p);
            if (i%2 == 0) {
                Sala s = new Sala ("nom_s"+i, i, "ubicacio_s"+i, o, p);
                session.save(s);
            }
            else {
                Usuari u = new Usuari ("username"+i, "nom_u"+i, "usuari."+(2+i)+"@est.fib.upc.edu");
                session.save(u);
            }
        }


        Reserva r1 = new Reserva();
        r1.setRecurs(recurs);
        r1.setData(new Date(2016, 8, 01));
        r1.setHoraInici(8);
        r1.setHoraFi(10);
        r1.setComentari("Comment1");
        r1.setUsuariCreador(u1);

        Reserva r2 = new Reserva();
        r2.setRecurs(recurs);
        r2.setData(new Date(2016, 10, 01));
        r2.setHoraInici(16);
        r2.setHoraFi(19);
        r2.setComentari("Comment2");
        r2.setUsuariCreador(u2);

        ReservaAmbNotificacio rn1 = new ReservaAmbNotificacio();
        Set<Usuari> aux = new HashSet<Usuari>();
        aux.add(u2);
        rn1.afegeixUsuaris(aux);
        rn1.setComentari("coment_rn1");
        rn1.setData(new Date(2016, 10, 03));
        rn1.setHoraFi(13);
        rn1.setHoraInici(10);
        rn1.setRecurs(s1);
        rn1.setUsuariCreador(u1);

        session.getTransaction().commit();
        FactoriaDades.getInstance().closeSession();
        FactoriaDades.getInstance().openSession();
        session = FactoriaDades.getInstance().getCurrentSession();
        session.beginTransaction();

        session.save(r1);
        session.save(r2);
        session.save(rn1);

        session.getTransaction().commit();
        FactoriaDades.getInstance().closeSession();
    }
}