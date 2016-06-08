package as.project.domain.model;

import javax.persistence.*;

/**
 * Created by romabejar on 08/06/16.
 */
@Entity
@Table(name= "as.project.domain.model.Sala" )
public class Sala extends Recurs {

    private int aforament;
    private String ubicacio;
    public Ordinador ordinador;
    public Projector projector;


    public Sala() {
    }

    public Sala(String nom, int aforament, String ubicacio, Ordinador ordinador, Projector projector) {
        super(nom);
        this.aforament = aforament;
        this.ubicacio = ubicacio;
        this.ordinador = ordinador;
        this.projector = projector;
    }

    public int getAforament() {
        return aforament;
    }

    public void setAforament(int aforament) {
        this.aforament = aforament;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

    @Override
    public boolean recEsSala(){
        return true;
    }

    public InfoRecurs getInfo(){
        return new InfoRecurs(super.getNom(),ordinador.getMarca(),ordinador.getModel(),aforament,ubicacio,projector.getResolucio());
    }
}
