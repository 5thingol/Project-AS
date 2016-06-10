package as.project.domain.model;

/**
 * Created by guillemc on 10/06/2016.
 */
public class InfoUsuari {

    private String username;
    private String nom;
    private String email;

    public InfoUsuari() {
    }

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

    public InfoUsuari(String username, String nom, String email) {
        this.username = username;
        this.nom = nom;
        this.email = email;
    }


}
