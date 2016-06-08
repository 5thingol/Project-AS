package as.project.domain.model;

/**
 * Created by romabejar on 08/06/16.
 */
public class InfoSala {

    private int aforament;
    private String ubicacio;

    public InfoSala() {
    }

    public InfoSala(int aforament, String ubicacio) {
        this.aforament = aforament;
        this.ubicacio = ubicacio;
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
}
