import as.project.datalayer.FactoriaDades;
import as.project.domain.controllers.CtrlCasDUsCrearReservaAmbNotificacio;
import as.project.domain.model.*;
import org.hibernate.Session;
/*
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.Date;

/**
 * Created by eduard on 17/04/16.
 */
public class DBTest {
  /*  public static void enviarMissatgeReserva(String nomRecurs, Date data, int horaInici, int horaFi, String username, String comentari, List<String> emails) {

        String from = "guillem.cordoba@gmail.com";

        // Assuming you are sending email from localhost
        String host = "smtp.upc.edu";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        // Get the default Session object.
        javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("guillem.cordoba@est.fib.upc.edu", "5TH1NG0l");
                    }
                });


        //javax.mail.Session session = javax.mail.Session.getDefaultInstance(properties, null);

        for (String email : emails) {

            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

                // Set Subject: header field
                message.setSubject("Nova reserva!");

                // Now set the actual message
                message.setText("Tens programada una nova reserva. Aquestes són les seves dades: \n \n    Nom del recurs: " +
                        nomRecurs + "\n    Data: " + data + "\n    Hora d'inici: " + horaInici + "\n    Hora fi: " + horaFi +
                        "\n    Usuari creador: " + username + "\n    Comentari: " + comentari);

                // Send message
                Transport.send(message);
            } catch (Exception mex) {
                mex.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws Exception{
        addData();

        FactoriaDades.getInstance().openSession();
        Session session = FactoriaDades.getInstance().getCurrentSession();
        session.beginTransaction();

        //CtrlCasDUsConsultarRecursosDisponiblesPerData ctrl1 = new CtrlCasDUsConsultarRecursosDisponiblesPerData();
        Date d = new Date();
        CtrlCasDUsCrearReservaAmbNotificacio ctrl2 = new CtrlCasDUsCrearReservaAmbNotificacio();
        List<InfoRecurs> recursos = ctrl2.obteRecursosDisponibles(d, 0, 23);
        //ctrl2.creaReservaAmbNotificacio(recursos.get(0).getNom(),"usuari1","Reserva molt maca");

        Sala sala = new Sala("hola",10,"",null,null);
        session.save(sala);

        session.getTransaction().commit();
        FactoriaDades.getInstance().closeSession();

        //enviarMissatgeReserva(recursos.get(0).getNom(),d,0,23,"usuari1","bueenaas",emails);
        //} catch (Exception e) {
        //    System.out.println("Excepcio: " + e.getMessage());
        //}
        /*
        printMenu();
        Scanner scanner = new Scanner(System.in);


        while(scanner.hasNext()) {
            String option = scanner.next();
            switch (option) {
                case "1":
                    FactoriaDades.getInstance().openSession();
                    Session session = FactoriaDades.getInstance().getCurrentSession();

                    List<Reserva> reserves = session.createCriteria(Reserva.class).list();

                    FactoriaDades.getInstance().closeSession();

                    for (int i = 0; i < reserves.size(); i++) {
                        Reserva aux = reserves.get(i);
                        System.out.print("Data: " + aux.getData() + " | ");
                        System.out.print("Hora inici: " + aux.getHoraInici() + " | ");
                        System.out.print("Hora fi: " + aux.getHoraFi() + " | ");
                        System.out.print("as.project.domain.model.Recurs: " + aux.getRecurs().getNom() + " | ");
                        System.out.print("Comentari: " + aux.getComentari() + " | ");
                        System.out.println("as.project.domain.model.Usuari: " + aux.getUsuariCreador().getUsername());
                    }
                    break;
                case "2":
                    FactoriaDades.getInstance().openSession();
                    session = FactoriaDades.getInstance().getCurrentSession();

                    List<Usuari> usuaris = session.createCriteria(Usuari.class).list();

                    FactoriaDades.getInstance().closeSession();

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
        */
   /* }

    static void printMenu() {
        System.out.println("OPTIONS:");
        System.out.println("1. Show reserva table data");
        System.out.println("2. Show usuari table data");
        System.out.println("3. Exit");
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
    }*/
}