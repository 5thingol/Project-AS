package as.project.domain.model;

/**
 * Created by romabejar on 08/06/16.
 */
public class InfoOrdinador {
    private String marca;
    private String model;

    public InfoOrdinador() {
    }

    public InfoOrdinador(String marca, String model) {
        this.marca = marca;
        this.model = model;
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
}
