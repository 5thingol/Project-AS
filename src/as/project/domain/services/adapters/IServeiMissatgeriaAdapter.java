package as.project.domain.services.adapters;

import java.util.Date;
import java.util.List;

/**
 * Created by guillemc on 07/06/2016.
 */
public interface IServeiMissatgeriaAdapter {

    void enviarDadesReserva(String nomReserva, Date data, int horaInici, int horaFi, String username, String comentari, List<String> emails);

}
