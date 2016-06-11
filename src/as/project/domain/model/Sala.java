package as.project.domain.model;

import as.project.datalayer.FactoriaDades;
import org.hibernate.Session;

import javax.persistence.*;

/**
 * Created by romabejar on 08/06/16.
 */
@Entity
@Table(name= "sala" )
@PrimaryKeyJoinColumn(name="nom")
public class Sala extends Recurs {

    @Column(name = "aforament")
    private int aforament;

    @Column(name = "ubicacio")
    private String ubicacio;

    @OneToOne
    private Ordinador ordinador;

    @OneToOne
    private Projector projector;


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

    public Ordinador getOrdinador() { return ordinador; }

    public void setOrdinador(Ordinador o ) { this.ordinador = o; }

    public Projector getProjector() { return projector; }

    public void setProjector(Projector o ) { this.projector = o; }

    @Override
    public boolean recEsSala(){
        return true;
    }

    @Override
    public InfoRecurs getExtraInfo(){
        InfoRecurs infoOrd = ordinador.getExtraInfo();
        InfoRecurs infoProj = projector.getExtraInfo();
        return new InfoRecurs(null,infoOrd.getMarca(),infoOrd.getModel(),aforament,ubicacio,infoProj.getResolucio());
    }

    /*
    public InfoRecurs getInfo(){
        return new InfoRecurs(super.getNom(),ordinador.getMarca(),ordinador.getModel(),aforament,ubicacio,projector.getResolucio());
    }
    */
}
