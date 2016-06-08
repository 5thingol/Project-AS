package as.project.domain.model;

/**
 * Created by romabejar on 08/06/16.
 */
public class InfoReservaAmbNotificacio {

    private String username;
    private int horaFi;
    private String comentari;

    public InfoReservaAmbNotificacio() {
    }

    public InfoReservaAmbNotificacio(String username, int horaFi, String comentari) {
        this.username = username;
        this.horaFi = horaFi;
        this.comentari = comentari;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHoraFi() {
        return horaFi;
    }

    public void setHoraFi(int horaFi) {
        this.horaFi = horaFi;
    }

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }
}
