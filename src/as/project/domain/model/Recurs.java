package as.project.domain.model;

import javax.persistence.*;

/**
 * Created by guillemc on 19/04/2016.
 */
@Entity
@Table(name= "recurs" )
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Recurs {

    @Id
    @Column(name = "nom")
    private String nom;

    public Recurs() {

    }

    public Recurs(String nom) {
        this.nom = nom;
    }

    public void setNom(String nom) { this.nom = nom; }

    public String getNom() {
        return nom;
    }

    public boolean recEsSala() {
        return false;
    }

    public abstract InfoRecurs getExtraInfo();

    public InfoRecurs getInfo() {
        InfoRecurs info = getExtraInfo();
        info.setNom(nom);
        return info;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recurs recurs = (Recurs) obj;
        return recurs.getNom() == nom;
    }
}
