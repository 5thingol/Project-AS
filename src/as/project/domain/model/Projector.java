package as.project.domain.model;

import javax.persistence.*;

/**
 * Created by romabejar on 08/06/16.
 */
@Entity
@Table(name= "projector" )
@PrimaryKeyJoinColumn(name="nom")
public class Projector extends Recurs {

    @Column(name = "resolucio")
    private String resolucio;

    public Projector() {
    }

    public Projector(String resolucio) {
        this.resolucio = resolucio;
    }

    public String getResolucio() {
        return resolucio;
    }

    public void setResolucio(String resolucio) {
        this.resolucio = resolucio;
    }

    @Override
    public InfoRecurs getExtraInfo(){
        return new InfoRecurs( null, null, null, 0, null, resolucio);
    }

    /*
    public InfoRecurs getInfo(){
        return new InfoRecurs(null,null,null,0,null,resolucio);
    }
    */
}
