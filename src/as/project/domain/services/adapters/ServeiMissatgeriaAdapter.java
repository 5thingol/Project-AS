package as.project.domain.services.adapters;

import as.project.domain.excepcions.ServeiNoDisponible;
import as.project.domain.services.ServeiMissatgeriaStub;
import as.project.domain.services.ServeiMissatgeriaStub.EnviarMissatgeReserva;
import as.project.domain.services.ServiceLocator;
import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Created by guillemc on 07/06/2016.
 */
public class ServeiMissatgeriaAdapter implements IServeiMissatgeriaAdapter {

    @Override
    public void enviarDadesReserva(String nomRecurs, Date data, int horaInici, int horaFi, String username, String comentari, List<String> emails) throws ServeiNoDisponible {
        try {

            // Buscar el servei
            ServeiMissatgeriaStub svMiss = ServiceLocator.getInstance().find("ServeiMissatgeria");
            EnviarMissatgeReserva enviarMissatge = new EnviarMissatgeReserva();

            // Afegir els paràmetres a la crida
            enviarMissatge.setNomRecurs(nomRecurs);
            enviarMissatge.setData(data);
            enviarMissatge.setHoraInici(horaInici);
            enviarMissatge.setHoraFi(horaFi);
            enviarMissatge.setUsername(username);
            enviarMissatge.setComentari(comentari);
            String emailsArray[] = new String[emails.size()];
            emailsArray = emails.toArray(emailsArray);
            enviarMissatge.setEmails(emailsArray);

            // Executar la crida
            svMiss.enviarMissatgeReserva(enviarMissatge);

        } catch (AxisFault e) {
            throw new ServeiNoDisponible();
        } catch (RemoteException e) {
            throw  new ServeiNoDisponible();
        }
    }
}
