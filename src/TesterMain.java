import as.project.domain.model.Usuari;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by guillemc on 05/04/2016.
 */
public class TesterMain {
    private static SessionFactory ourSessionFactory;
    private static ServiceRegistry serviceRegistry;

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(String args[]) {

        // Configuracio d'hibernate
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Usuari.class);
        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        ourSessionFactory = configuration.buildSessionFactory(serviceRegistry);



    }
}
