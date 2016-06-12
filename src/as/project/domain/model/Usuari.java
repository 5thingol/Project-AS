package as.project.domain.model;

import javax.persistence.*;
import java.util.*;

/**
 * Created by guillemc on 05/04/2016.
 */
@Entity
@Table(name = "usuari")
public class Usuari {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    //@OneToMany(mappedBy="usuariCreador")
    //private Set<Reserva> reservesCreades;

    // CONSTRUCTORS

    public Usuari() {

        //reservesCreades = new HashSet<Reserva>();
    }

    public Usuari(String username, String nom, String email) {
        this.username = username;
        this.nom = nom;
        this.email = email;
        //reservesCreades = new HashSet<Reserva>();
    }

    // GETTERS & SETTERS

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //public Set<Reserva> getReservesCreades() { return reservesCreades; }

    //public void setReservesCreades(Set<Reserva> reservesCreades) { this.reservesCreades = reservesCreades; }

    // FUNCIONS DE LA CLASSE

    /**
     * Obte la informacio de l'usuari
     * @return una llista amb els seguents strings, per ordre: l'username, el nom i l'email de l'usuari
     */
    public InfoUsuari getInfo() {
        return new InfoUsuari(username, nom, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuari user = (Usuari) obj;
        return user.getUsername() == username;
    }
}
