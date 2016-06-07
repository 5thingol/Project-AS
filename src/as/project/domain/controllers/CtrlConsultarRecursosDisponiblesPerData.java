package as.project.domain.controllers;

import as.project.datalayer.FactoriaDades;
import as.project.datalayer.HibernateUtil;
import as.project.domain.excepcions.NoHiHaRecursos;
import as.project.domain.model.Recurs;
import as.project.domain.model.Reserva;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guillemc on 07/06/2016.
 */
public class CtrlConsultarRecursosDisponiblesPerData {

    public List<List<String>> obteRecursosDisponiblesPerData(Date data, int horaInici, int horaFi) throws NoHiHaRecursos {

        // Obtenim la sessió actual
        Session session = FactoriaDades.getInstance().getCurrentSession();

        // Obtenim tots els recursos i les reserves
        List<Recurs> totsRecursos = session.createCriteria(Recurs.class).list();
        List<Reserva> totesReserves = session.createCriteria(Reserva.class).list();

        // Esborrem de la llista de tots els recursos aquells que ja estiguin reservats en l'interval donat
        for (Reserva reserva : totesReserves) {
            if (reserva.solapa(data,horaInici,horaFi)) totsRecursos.remove(reserva.getRecurs());
        }

        if (totsRecursos.size() == 0) throw new NoHiHaRecursos();

        List<List<String>> recursosInfo = new ArrayList<>();

        for (Recurs recurs : totsRecursos) {
            recursosInfo.add(recurs.getInfo());
        }

        return recursosInfo;

    }

}
