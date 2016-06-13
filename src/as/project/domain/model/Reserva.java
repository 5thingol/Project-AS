package as.project.domain.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by guillemc on 05/04/2016.
 */
@Entity
@Table(name = "reserva")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Reserva implements java.io.Serializable {

   /* @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Id
    @Column(name = "nom_recurs")
    private String nomRecurs;
*/
    @Id
    @ManyToOne
    @JoinColumn(name = "recurs_id", referencedColumnName = "nom")
    private Recurs recurs;

    @Id
    @Column(name = "data")
    private Date data;

    @Id
    @Column(name = "hora_inici")
    private int horaInici;

    @Column(name = "hora_fi")
    private int horaFi;

    @Column(name = "comentari")
    private String comentari;

    @ManyToOne
    @JoinColumn(name = "usuari_id", referencedColumnName = "username")
    private Usuari usuariCreador;


    // CONSTRUCTORS

    public Reserva() {}

    public Reserva(Recurs recurs, Date data, int horaInici, int horaFi, String comentari, Usuari usuariCreador) {
        inicialitza(recurs, data, horaInici, horaFi, comentari, usuariCreador);
    }

    public void inicialitza(Recurs recurs, Date data, int horaInici, int horaFi, String comentari, Usuari usuariCreador) {
        this.recurs = recurs;
        this.data = data;
        this.horaInici = horaInici;
        this.horaFi = horaFi;
        this.comentari = comentari;
        this.usuariCreador = usuariCreador;
    }

    // GETTERS & SETTERS

    public Recurs getRecurs() {
        return recurs;
    }

    public void setRecurs(Recurs recurs) {
        this.recurs = recurs;
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

    public String getComentari() {
        return comentari;
    }

    public void setComentari(String comentari) {
        this.comentari = comentari;
    }

    public Usuari getUsuariCreador() { return usuariCreador; }

    public void setUsuariCreador(Usuari usuariCreador) {
        this.usuariCreador = usuariCreador;
    }

    public String getUsernameUsuari() { return this.usuariCreador.getUsername(); }

    // FUNCIONS DE LA CLASSE

    /**
     * Comprova si la instancia es una reserva amb notificacio
     * @return false si es instancia de la superclasse as.project.domain.model.Reserva
     */
    public boolean esReservaAmbNotificacio() {
        return false;
    }

    /**
     *    Comprova si reserva se solapa amb algun interval de la data i les hores donades
     *    @param  data: data en que es pot solapar la reserva
     *    @param hi: hora d'inici en que es pot solapar la reserva
     *    @param hf: hora fi en que es pot solapar la reserva
     *    @return true si alguna hora de l'interval donat coincideix amb alguna hora de l'interval
     *    d'us de la reserva, i false en qualsevol altre cas
     */
    public boolean solapa(Date data, int hi, int hf) {
        if (data.equals(this.data))
            if ((horaInici <= hi && hi < horaFi) || (horaInici < hf && hf <= horaFi)) return true;
        return false;
    }

    /**
     *    Comprova si la reserva esta caducada
     *    @return true si la reserva esta caducada, es a dir, la seva data es anterior a la data actual.
     *    False en qualsevol altre cas
     */
    public boolean reservaEstaCaducada() {
        Date currentDate = Calendar.getInstance().getTime();
        if (currentDate.after(data)) {
            return true;
        } else
            return false;
    }

    /**
     * Comprova si el recurs de la reserva es una sala
     * @return true si el recurs associat a la reserva es una sala, fals en qualsevol altre cas
     */
    public boolean esReservaDeSala() {
        // Comprovar si el recurs es una sala o no
        return recurs.recEsSala();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reserva reserva2 = (Reserva) obj;
        return recurs.getNom().equals(reserva2.getRecurs().getNom()) && data.equals(reserva2.getData()) && horaInici == reserva2.getHoraInici();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.recurs.getNom() != null ? this.recurs.getNom().hashCode() : 0) + (this.data != null ? this.data.hashCode() : 0) + this.horaInici;
        return hash;
    }
}
