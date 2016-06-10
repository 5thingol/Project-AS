package as.project.domain.services;

import org.apache.axis2.AxisFault;

/**
 * Created by guillemc on 19/04/2016.
 */
public class ServiceLocator {

    private static ServiceLocator instance;

    private ServiceLocator() {

    }

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    public ServeiMissatgeriaStub find(String service) throws AxisFault {
        if (service.equals("ServeiMissatgeria")) {
            return new ServeiMissatgeriaStub();
        }

        return null;
    }

}
