package as.project.domain.model;

import javax.persistence.*;

/**
 * Created by romabejar on 08/06/16.
 */
@Entity
@Table(name= "ordinador" )
@PrimaryKeyJoinColumn(name="nom")
public class Ordinador extends Recurs{

    @Column(name = "marca")
    private String marca;

    @Column(name = "model")
    private String model;

    public Ordinador() {
        super();
    }

    public Ordinador(String nomOrdinador) {
        super(nomOrdinador);
    }

    public Ordinador(String nom, String marca, String model){
        super(nom);
        this.marca = marca;
        this.model = model;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    public String getMarca(){
        return marca;
    }

    public void setModel(String model){
        this.model = model;
    }

    public String getModel(){
        return model;
    }

    @Override
    public InfoRecurs getExtraInfo(){
        return new InfoRecurs( null, marca, model, 0, null, null );
    }

    /*public InfoRecurs getInfo(){
        return new InfoRecurs( null, marca, model, 0, null, null );
    }*/

}
