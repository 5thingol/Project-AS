import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;


import java.sql.*;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by eduard on 17/04/16.
 */
public class DBTest {

    public static void main(String[] args) throws SQLException{
        addData();

       /* Session session = HibernateUtil.getSessionFactory().openSession();
        Usuari u = (Usuari) session.get(Usuari.class, "eduardborges");
        //Reserva r = (Reserva) session.get(Reserva.class,);
        Set<Reserva> reserves = u.getReservesCreades();
        for (Reserva reserva : reserves) {
            System.out.println(reserva.getComentaris());
        }
        session.close();
        */
        printMenu();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String option = scanner.next();
            switch (option) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Wrong option. Press 1, 2 or 3");
                    break;
            }
            printMenu();
        }
    }

    static void printMenu() {
        System.out.println("OPTIONS:");
        System.out.println("1. Show reserva table data");
        System.out.println("2. Show usuari table data");
        System.out.println("3. Exit");
    }

    static void addData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Recurs recurs = new Recurs();
        recurs.setNom("Projector");

        Usuari u1 = new Usuari();
        u1.setEmail("eduard.borges@est.fib.upc.edu");
        u1.setNom("Eduard Borges");
        u1.setUsername("eduardborges");

        Usuari u2 = new Usuari();
        u2.setEmail("nom.inventat@est.fib.upc.edu");
        u2.setNom("Nom Inventat");
        u2.setUsername("nomInventat");

        Reserva r1 = new Reserva();
        r1.setRecurs(recurs);
        r1.setData(new Date(2016, 8, 01));
        r1.setHoraInici(8);
        r1.setHoraFi(10);
        r1.setComentaris("Comment1");
        r1.setUsuariCreador(u1);
        u1.assignaReserva(r1);

        Reserva r2 = new Reserva();
        r2.setRecurs(recurs);
        r2.setData(new Date(2016, 10, 01));
        r2.setHoraInici(16);
        r2.setHoraFi(19);
        r2.setComentaris("Comment2");
        r2.setUsuariCreador(u1);
        u1.assignaReserva(r2);


        session.save(recurs);
        session.save(u1);
        session.save(u2);
        session.save(r1);
        session.save(r2);

        session.getTransaction().commit();

        session.close();
    }
}
