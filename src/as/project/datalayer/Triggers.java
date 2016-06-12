package as.project.datalayer;

import as.project.domain.model.Reserva;
import as.project.domain.model.ReservaAmbNotificacio;
import as.project.domain.model.Sala;
import as.project.domain.model.Usuari;
import org.hibernate.CallbackException;
import org.hibernate.Criteria;
import org.hibernate.EmptyInterceptor;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.*;

/**
 * Created by romabejar on 11/06/16.
 */
public class Triggers extends EmptyInterceptor {


    private boolean checkReservaSalaUsuariSolapa( Reserva a, Reserva b ){
        boolean solapa = false;

        if ( a.getUsernameUsuari() == b.getUsernameUsuari() && b.esReservaDeSala()){
            solapa = checkReservaRecursSolapa(a,b);
        }

        return solapa;
    }

    private boolean checkReservaRecursSolapa( Reserva a, Reserva b){
        boolean solapa = false;

        if( (a.getHoraFi() > b.getHoraInici() && b.getHoraInici() >= a.getHoraInici()) || (a.getHoraInici() <
                b.getHoraFi() && b.getHoraFi() <= a.getHoraFi()) ) solapa = true;

        return solapa;
    }


    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
            throws CallbackException{

        if (entity instanceof Reserva){

            if (entity instanceof ReservaAmbNotificacio) {
                Reserva reserva = (Reserva) FactoriaDades.getInstance().getCurrentSession().get(Reserva.class, (Reserva) entity);

                if (reserva != null) {
                    throw new CallbackException("ReservaJaExisteix");
                }
            } else {
                Reserva reserva = (Reserva) FactoriaDades.getInstance().getCurrentSession().get(ReservaAmbNotificacio.class, (Reserva) entity);

                if (reserva != null) {
                    throw new CallbackException("ReservaAmbNotificacioJaExisteix");
                }

            }

            Reserva reserva = (Reserva) entity;

            if(FactoriaDades.getInstance().getCurrentSession().get(Usuari.class, (Serializable) reserva.getUsuariCreador().getUsername()) == null)
                throw new CallbackException("UsuariNoExisteix");

            if(entity instanceof ReservaAmbNotificacio && ((ReservaAmbNotificacio) entity).getUsuarisNotificats().size() > 10)
                throw new CallbackException("ReservaATope");

            if (reserva.getHoraInici() >= reserva.getHoraFi() || reserva.getHoraInici() < 0 ||
                    reserva.getHoraInici() > 23 || reserva.getHoraFi() < 1 || reserva.getHoraFi() > 24)
                throw new CallbackException("PeriodeErroni");

            Criteria criteria = FactoriaDades.getInstance().getCurrentSession().createCriteria(Reserva.class)
                    .add(Restrictions.eq("recurs.nom", reserva.getRecurs().getNom()))
                    .add(Restrictions.eq("data", reserva.getData()));

            List<Reserva> reservaList =
                    FactoriaDades.getInstance().getCurrentSession().createCriteria(Reserva.class)
                            .add(Restrictions.eq("recurs.nom", reserva.getRecurs().getNom()))
                            .add(Restrictions.eq("data", reserva.getData()))
                            .list();
            List<ReservaAmbNotificacio> reservaAmbNotificacioList = (List<ReservaAmbNotificacio>)
                    FactoriaDades.getInstance().getCurrentSession().createCriteria(ReservaAmbNotificacio.class)
                            .add(Restrictions.eq("recurs.nom", reserva.getRecurs().getNom()))
                            .add(Restrictions.eq("data", reserva.getData()))
                            .list();

            if(reserva.getRecurs().recEsSala()) {

                boolean solapa = false;
                for (int i = 0; !solapa && i < reservaList.size(); ++i)
                    solapa = checkReservaSalaUsuariSolapa(reserva, reservaList.get(i));
                for (int i = 0; !solapa && i < reservaAmbNotificacioList.size(); ++i)
                    solapa = checkReservaSalaUsuariSolapa(reserva, reservaList.get(i));
                if (solapa) throw new CallbackException("RecursSalaSolapada");
            }

            boolean solapa = false;
            for (int i = 0; !solapa && i < reservaList.size(); ++i) solapa =
                    checkReservaRecursSolapa(reserva, reservaList.get(i));
            for (int i = 0; !solapa && i < reservaAmbNotificacioList.size(); ++i) solapa =
                    checkReservaRecursSolapa(reserva, (Reserva) reservaAmbNotificacioList.get(i));
            if (solapa) throw new CallbackException("ReservaRecursSolapada");

            if(entity instanceof ReservaAmbNotificacio){
                Set<Usuari> usuaris = new HashSet<>();
                usuaris.add(reserva.getUsuariCreador());
                ((ReservaAmbNotificacio) entity).afegeixUsuaris(usuaris);
                return true;
            }
        } else if (entity instanceof Sala){
            Sala sala = (Sala) entity;
            if(sala.getAforament() < 0) throw new CallbackException("AforamentSalaErroni");
        }

        return false;

    }

}