import javax.persistence.*;

/**
 * Created by guillemc on 05/04/2016.
 */
@Entity
@Table(name = "USUARI")
public class Usuari {

    // Un usuari es identificat pel seu username
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    public Usuari() {}

    public Usuari(String username, String nom, String email) {
        this.username = username;
        this.nom = nom;
        this.email = email;
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

}
