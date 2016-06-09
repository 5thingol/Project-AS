package as.project.domain.model;

import javax.persistence.*;

/**
 * Created by romabejar on 08/06/16.
 */
@Entity
@Table(name= "as.project.domain.model.Projector" )
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

    public InfoRecurs getInfo(){
        return new InfoRecurs(null,null,null,0,null,resolucio);
    }
}
