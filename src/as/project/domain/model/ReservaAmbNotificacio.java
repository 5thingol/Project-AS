package as.project.domain.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduard on 07/06/2016.
 */
public class ReservaAmbNotificacio extends Reserva {
    private Set<Usuari> usuarisNotificats = new HashSet<Usuari>();

    public int getNumUsuarisNotificats() { return usuarisNotificats.size(); }

    public void afegeixUsuaris (Set<Usuari> users) { usuarisNotificats.addAll(users); }

    public InfoReservaAmbNotificacio getInfo() {
        InfoReservaAmbNotificacio info = new InfoReservaAmbNotificacio();
        info.setUsername(getUsernameUsuari());
        info.setHoraFi(getHoraFi());
        info.setComentari(getComentari());
        return info;
    }

    public boolean estaATope() {
        if (usuarisNotificats.size() == 10) return true;
        else return false;
    }

    @Override
    public boolean esReservaAmbNotificacio() { return true; }

    public Set<Usuari> getUsuarisNotificats() { return usuarisNotificats; }
}
