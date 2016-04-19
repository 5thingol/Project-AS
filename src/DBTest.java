import org.hibernate.Session;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by eduard on 17/04/16.
 */
public class DBTest {

    public static void main(String[] args) throws SQLException{
        addData();

        printMenu();
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext()) {
            String option = scanner.next();
            switch (option) {
                case "1":
                    Session session = HibernateUtil.getSessionFactory().openSession();

                    List<Reserva> reserves = session.createCriteria(Reserva.class).list();

                    session.close();

                    for (int i = 0; i < reserves.size(); i++) {
                        Reserva aux = reserves.get(i);
                        System.out.print("Data: " + aux.getData() + " | ");
                        System.out.print("Hora inici: " + aux.getHoraInici() + " | ");
                        System.out.print("Hora fi: " + aux.getHoraFi() + " | ");
                        System.out.print("Recurs: " + aux.getRecurs().getNom() + " | ");
                        System.out.print("Comentari: " + aux.getComentaris() + " | ");
                        System.out.println("Usuari: " + aux.getUsuariCreador().getUsername());
                    }
                    break;
                case "2":
                    session = HibernateUtil.getSessionFactory().openSession();

                    List<Usuari> usuaris = session.createCriteria(Usuari.class).list();

                    session.close();

                    for (int i = 0; i < usuaris.size(); i++) {
                        Usuari aux = usuaris.get(i);
                        System.out.print("Nom d'usuari: " + aux.getUsername() + " | ");
                        System.out.print("Nom: " + aux.getNom() + " | ");
                        System.out.println("Correu electronic: " + aux.getEmail() + " | ");
                    }
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

        Recurs recurs = new Recurs();
        recurs.setNom("Projector");

        Usuari u1 = new Usuari();
        u1.setEmail("usuari.1@est.fib.upc.edu");
        u1.setNom("Usuari 1");
        u1.setUsername("usuari1");

        Usuari u2 = new Usuari();
        u2.setEmail("usuari.2@est.fib.upc.edu");
        u2.setNom("Usuari 2");
        u2.setUsername("usuari2");

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
        r2.setUsuariCreador(u2);
        u2.assignaReserva(r2);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(recurs);
        session.save(u1);
        session.save(u2);
        session.save(r1);
        session.save(r2);

        session.getTransaction().commit();
        session.close();
    }
}