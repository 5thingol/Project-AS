
import javax.persistence.*;

/**
 * Created by guillemc on 19/04/2016.
 */
// Aixo es una primera versio de la classe recurs perque compili tot
// TO-DO: CANVIAR TOT PER LA SEGUENT ENTREGA!
@Entity
@Table(name= "Recurs" )
public class Recurs {

    @Id
    @Column(name = "nom")
    private String nom;

    public Recurs() {

    }

    public void setNom(String nom) { this.nom = nom; }

    public boolean recEsSala() {
        return false;
    }
}
