package as.project.datalayer;

import as.project.domain.model.*;
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
           Usuari user = reserva.getUsuariCreador();
            if(user == null || FactoriaDades.getInstance().getCurrentSession().get(Usuari.class, (Serializable) user.getUsername()) == null)
                throw new CallbackException("UsuariNoExisteix");

            if(entity instanceof ReservaAmbNotificacio && ((ReservaAmbNotificacio) entity).getUsuarisNotificats().size() > 10)
                throw new CallbackException("ReservaATope");

            if (reserva.getHoraInici() >= reserva.getHoraFi() || reserva.getHoraInici() < 0 ||
                    reserva.getHoraInici() > 23 || reserva.getHoraFi() < 1 || reserva.getHoraFi() > 24)
                throw new CallbackException("PeriodeErroni");

            StringBuilder queryRL = new StringBuilder("SELECT * FROM reserva r WHERE r.recurs_id = \'")
                    .append(reserva.getRecurs().getNom())
                    .append("\' and r.data = \'")
                    .append(reserva.getData())
                    .append("\' and r.hora_inici <> ")
                    .append(reserva.getHoraInici());

            StringBuilder queryRANL = new StringBuilder("SELECT * FROM reservaAmbNotificacio r where r.recurs_id = \'")
                    .append(reserva.getRecurs().getNom())
                    .append("\'and r.data = \'")
                    .append(reserva.getData())
                    .append("\' and r.hora_inici <> ")
                    .append(reserva.getHoraInici());


            List<Object[]> reservaList = FactoriaDades.getInstance().getCurrentSession().createSQLQuery(queryRL.toString()).list();

            List<Object[]> reservaAmbNotificacioList = FactoriaDades.getInstance().getCurrentSession().createSQLQuery(queryRANL.toString()).list();

            if(reserva.getRecurs().recEsSala()) {
                boolean solapa = false;
                for (Object[] o : reservaList) {
                    Reserva auxR = new Reserva();
                    auxR.setHoraInici((int)o[0]);
                    auxR.setData((Date)o[1]);
                    auxR.setHoraFi((int)o[3]);
                    Usuari aux = new Usuari();
                    aux.setUsername((String)o[5]);
                    auxR.setUsuariCreador(aux);

                    if (!solapa) {
                        solapa = checkReservaSalaUsuariSolapa(reserva, auxR);
                    }
                }
                for (Object[] o : reservaAmbNotificacioList) {
                    Reserva auxR = new Reserva();
                    auxR.setHoraInici((int)o[0]);
                    auxR.setData((Date)o[1]);
                    auxR.setHoraFi((int)o[3]);
                    Usuari aux = new Usuari();
                    aux.setUsername((String)o[5]);
                    auxR.setUsuariCreador(aux);

                    if (!solapa) {
                        solapa = checkReservaSalaUsuariSolapa(reserva, auxR);
                    }
                }
                if (solapa) throw new CallbackException("RecursSalaSolapada");
            }

            boolean solapa = false;
            for (Object[] o : reservaList) {
                Reserva auxR = new Reserva();
                auxR.setHoraInici((int)o[0]);
                auxR.setData((Date)o[1]);
                auxR.setHoraFi((int)o[3]);
                Usuari aux = new Usuari();
                aux.setUsername((String)o[5]);
                auxR.setUsuariCreador(aux);

                if (!solapa) {
                    solapa = checkReservaRecursSolapa(reserva, auxR);
                }
            }
            for (Object[] o : reservaAmbNotificacioList) {
                Reserva auxR = new Reserva();
                auxR.setHoraInici((int)o[0]);
                auxR.setData((Date)o[1]);
                auxR.setHoraFi((int)o[3]);
                Usuari aux = new Usuari();
                aux.setUsername((String)o[5]);
                auxR.setUsuariCreador(aux);

                if (!solapa) {
                    solapa = checkReservaRecursSolapa(reserva, auxR);
                }
            }
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