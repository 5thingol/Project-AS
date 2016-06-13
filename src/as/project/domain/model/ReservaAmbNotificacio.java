package as.project.domain.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eduard on 07/06/2016.
 */
@Entity
@Table(name= "reservaAmbNotificacio")
@AttributeOverrides({
        @AttributeOverride(name="recurs_id", column=@Column(name="recurs_id")),
        @AttributeOverride(name="data", column=@Column(name="data")),
        @AttributeOverride(name="hora_inici", column=@Column(name="hora_inici")),
        @AttributeOverride(name="hora_fi", column=@Column(name="hora_fi")),
        @AttributeOverride(name="comentari", column=@Column(name="comentari")),
        @AttributeOverride(name="usuari_id", column=@Column(name="usuari_id"))
})
public class ReservaAmbNotificacio extends Reserva {

    @ManyToMany
    @JoinTable(
            name = "ES_NOTIFICA",
            joinColumns = {@JoinColumn(name = "recurs_id"),
            @JoinColumn(name = "data"),
            @JoinColumn(name = "hora_inici")},
            inverseJoinColumns = {@JoinColumn(name = "username")}
    )
    private Set<Usuari> usuarisNotificats = new HashSet<>();

    public ReservaAmbNotificacio() {}

    public ReservaAmbNotificacio(Recurs recurs, Date data, int horaInici, int horaFi, String comentari, Usuari usuariCreador) {
        inicialitza(recurs, data, horaInici, horaFi, comentari, usuariCreador);
    }

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