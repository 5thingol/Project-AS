import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;


import java.sql.*;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by eduard on 17/04/16.
 */
public class DBTest {

    public static void main(String[] args) throws SQLException{
        addData();
        String url = "jdbc:postgresql://localhost:5432/postgres";
        Connection con = null;

        printMenu();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String option = scanner.next();
            switch (option) {
                case "1":
                    try {
                        String driver = "org.postgresql.Driver";
                        Class.forName(driver).newInstance();
                    } catch (Exception e) {
                        System.out.println("Failed to load mSQL driver.");
                        return;
                    }
                    try {
                        con = DriverManager.getConnection(url, "postgres", "postgres");

                        Statement select = con.createStatement();
                        ResultSet result = select
                                .executeQuery("SELECT * FROM Reserva");
                        System.out.println(" --------------------------------------------");
                        while (result.next()) { // process results one row at a time
                            String col1 = result.getString(1);
                            String col2 = result.getString(2);
                            String col3 = result.getString(3);
                            String col4 = result.getString(4);
                            String col5 = result.getString(5);
                            String col6 = result.getString(6);

                            System.out.println("Recurs: " +col1+ " | Data: " +col2+" | Hora Inici: " +col3+ " | Hora Fi: " +col4+ " | Comments: " +col5+ " | User: " +col6);
                        }
                        System.out.println(" --------------------------------------------");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    try {
                        String driver = "org.postgresql.Driver";
                        Class.forName(driver).newInstance();
                    } catch (Exception e) {
                        System.out.println("Failed to load mSQL driver.");
                        return;
                    }
                    try {
                        con = DriverManager.getConnection(url, "postgres", "postgres");
                        Statement select = con.createStatement();
                        ResultSet result = select
                                .executeQuery("SELECT * FROM Usuari");
                        System.out.println(" --------------------------------------------");
                        while (result.next()) { // process results one row at a time
                            String col1 = result.getString(1);
                            String col2 = result.getString(2);
                            String col3 = result.getString(3);

                            System.out.println("Mail: " +col1+ " | Name: " +col2+" | Username: " +col3);
                        }
                        System.out.println(" --------------------------------------------");
                    } catch (Exception e) {
                        e.printStackTrace();
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
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Reserva.class);
        configuration.addAnnotatedClass(Usuari.class);
        configuration.configure("hibernate.cfg.xml");

        new SchemaExport(configuration).create(true, true);

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        session.beginTransaction();

        Usuari u1 = new Usuari();
        u1.setEmail("eduard.borges@est.fib.upc.edu");
        u1.setNom("Eduard Borges");
        u1.setUsername("eduardborges");

        Usuari u2 = new Usuari();
        u2.setEmail("nom.inventat@est.fib.upc.edu");
        u2.setNom("Nom Inventat");
        u2.setUsername("nomInventat");

        Reserva r1 = new Reserva();
        r1.setNomRecurs("Projector");
        r1.setData(new Date(2016, 8, 01));
        r1.setHoraInici(8);
        r1.setHoraFi(10);
        r1.setComentaris("Comment1");
        r1.setIdUsuariCreador("eduardborges");

        Reserva r2 = new Reserva();
        r2.setNomRecurs("PC");
        r2.setData(new Date(2016, 10, 01));
        r2.setHoraInici(16);
        r2.setHoraFi(19);
        r2.setComentaris("Comment2");
        r2.setIdUsuariCreador("eduardborges");

        session.save(u1);
        session.save(u2);
        session.save(r1);
        session.save(r2);

        session.getTransaction().commit();

        session.close();
    }
}
