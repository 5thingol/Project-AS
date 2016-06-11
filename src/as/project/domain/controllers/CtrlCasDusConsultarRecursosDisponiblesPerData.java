package as.project.domain.controllers;

import as.project.datalayer.FactoriaDades;
import as.project.domain.excepcions.NoHiHaRecursos;
import as.project.domain.model.InfoRecurs;
import as.project.domain.model.Recurs;
import as.project.domain.model.Reserva;
import as.project.domain.model.Sala;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guillemc on 07/06/2016.
 */
public class CtrlCasDUsConsultarRecursosDisponiblesPerData {

    public List<InfoRecurs> obteRecursosDisponiblesPerData(Date data, int horaInici, int horaFi) throws NoHiHaRecursos {

        // Obtenim la sessió actual
        Session session = FactoriaDades.getInstance().getCurrentSession();

        // Obtenim tots els recursos i les reserves
        List<Recurs> recursosDisponibles = session.createCriteria(Recurs.class).list();
        List<Reserva> totesReserves = session.createCriteria(Reserva.class).list();

        // Esborrem de la llista de tots els recursos aquells que ja estiguin reservats en l'interval donat
        // i també els que es troben en una sala i per tant no poden ser reservats per separat
        for (Reserva reserva : totesReserves) {
            Recurs recursReservat = reserva.getRecurs();
            if (reserva.solapa(data,horaInici,horaFi)) recursosDisponibles.remove(recursReservat);
            /*if (recursReservat.recEsSala()) {
                Sala sala = (Sala) recursReservat;
                if (sala.getOrdinador() != null) recursosDisponibles.remove(sala.getOrdinador());
                if (sala.getProjector() != null) recursosDisponibles.remove(sala.getProjector());
            }*/
        }

        if (recursosDisponibles.size() == 0) throw new NoHiHaRecursos();

        List<InfoRecurs> recursosInfo = new ArrayList<>();

        for (Recurs recurs : recursosDisponibles) {
            recursosInfo.add(recurs.getInfo());
        }

        return recursosInfo;

    }

}
