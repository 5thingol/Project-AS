package as.project.datalayer;

import as.project.domain.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.internal.DefaultLoadEventListener;
import org.hibernate.event.spi.LoadEventListener;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class FactoriaDades {

    private static FactoriaDades instance;
    private static SessionFactory sessionFactory;
    private static Session currentSession;

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Recurs.class);
            configuration.addAnnotatedClass(Sala.class);
            configuration.addAnnotatedClass(Ordinador.class);
            configuration.addAnnotatedClass(Projector.class);
            configuration.addAnnotatedClass(Reserva.class);
            configuration.addAnnotatedClass(ReservaAmbNotificacio.class);
            configuration.addAnnotatedClass(Usuari.class);
            configuration.configure("hibernate.cfg.xml");

            // TODO: ROMAAA AFEGEIX EL INTERCEPTOR AQUI
            //configuration.setInterceptor(new Triggers());

            new SchemaExport(configuration).create(true, true);

            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();

            SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);

            return factory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private FactoriaDades() {
        sessionFactory = buildSessionFactory();
    }

    public static FactoriaDades getInstance() {
        if (instance == null) {
            instance = new FactoriaDades();
        }
        return instance;
    }

    //public static SessionFactory getSessionFactory() {return sessionFactory; }

    public void openSession() {
        currentSession = sessionFactory.openSession();
    }

    public void closeSession() {
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

}
