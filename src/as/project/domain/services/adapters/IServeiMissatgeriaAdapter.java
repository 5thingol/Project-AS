package as.project.domain.services.adapters;

import as.project.domain.excepcions.ServeiNoDisponible;

import java.util.Date;
import java.util.List;

/**
 * Created by guillemc on 07/06/2016.
 */
public interface IServeiMissatgeriaAdapter {

    void enviarDadesReserva(String nomRecurs, Date data, int horaInici, int horaFi, String username, String comentari, List<String> emails) throws ServeiNoDisponible;

}
