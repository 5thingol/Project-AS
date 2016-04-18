import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by guillemc on 05/04/2016.
 */
@Entity
@Table(name = "Reserva")
public class Reserva implements java.io.Serializable {

    @Id
    @Column(name = "nomRecurs")
    private String nomRecurs;

    @Id
    @Column(name = "data")
    private Date data;

    @Id
    @Column(name = "horaInici")
    private int horaInici;

    @Column(name = "horaFi")
    private int horaFi;

    @Column(name = "comentaris")
    private String comentaris;

    @Column(name = "idUsuariCreador")
    private String idUsuariCreador;

    // CREADORES

    public Reserva() {}

    public Reserva(String nomRecurs, Date data, int horaInici, int horaFi, String comentaris, String idUsuariCreador) {
        this.nomRecurs = nomRecurs;
        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
        this.comentaris = comentaris;
        this.idUsuariCreador = idUsuariCreador;
    }

    // GETTERS & SETTERS

    public String getNomRecurs() {
        return nomRecurs;
    }

    public void setNomRecurs(String nomRecurs) {
        this.nomRecurs = nomRecurs;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getHoraInici() {
        return horaInici;
    }

    public void setHoraInici(int horaInici) {
        this.horaInici = horaInici;
    }

    public int getHoraFi() {
        return horaFi;
    }

    public void setHoraFi(int horaFi) {
        this.horaFi = horaFi;
    }

    public String getComentaris() {
        return comentaris;
    }

    public void setComentaris(String comentaris) {
        this.comentaris = comentaris;
    }

    public String getIdUsuariCreador() {
        return idUsuariCreador;
    }

    public void setIdUsuariCreador(String idUsuariCreador) {
        this.idUsuariCreador = idUsuariCreador;
    }

    // FUNCIONS DE LA CLASSE

    /**
     *    Comprova si reserva se solapa amb algun interval de la data i les hores donades
     *    @param  data: data en que es pot solapar la reserva
     *    @param hi: hora d'inici en que es pot solapar la reserva
     *    @param hf: hora fi en que es pot solapar la reserva
     *    @return true si alguna hora de l'interval donat coincideix amb alguna hora de l'interval
     *    d'us de la reserva, i false en qualsevol altre cas
     */
    public Boolean solapa(Date data, int hi, int hf) {
        if ((horaInici < hi && hi < horaFi) || (horaInici < hf && hf < horaFi)) return true;
        else return false;
    }

    /**
     *    Comprova si la reserva esta caducada
     *    @return true si la reserva esta caducada, es a dir, la seva data es anterior a la data actual.
     *    False en qualsevol altre cas
     */
    public Boolean reservaEstaCaducada() {
        Date currentDate = Calendar.getInstance().getTime();
        if (currentDate.after(data)) {
            return true;
        } else
            return false;
    }

}
