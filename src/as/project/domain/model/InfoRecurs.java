package as.project.domain.model;

/**
 * Created by romabejar on 08/06/16.
 */
public class InfoRecurs {

    private String nom;
    private String marca;
    private String model;
    private int aforament;
    private String ubicacio;
    private String resolucio;

    public InfoRecurs() {
    }

    public InfoRecurs(String nom, String marca, String model, int aforament, String ubicacio, String resolucio) {
        this.nom = nom;
        this.marca = marca;
        this.model = model;
        this.aforament = aforament;
        this.ubicacio = ubicacio;
        this.resolucio = resolucio;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getResolucio() {
        return resolucio;
    }

    public void setResolucio(String resolucio) {
        this.resolucio = resolucio;
    }
}
