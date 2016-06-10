package as.project.domain.model;

import javax.persistence.*;
import java.util.*;

/**
 * Created by guillemc on 05/04/2016.
 */
@Entity
@Table(name = "as.project.domain.model.Usuari")
public class Usuari {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "nom")
    private String nom;

    @Column(name = "email")
    private String email;

    //@OneToMany(mappedBy="usuariCreador")
    //private Set<Reserva> reservesCreades;

    // CONSTRUCTORS

    public Usuari() {

        //reservesCreades = new HashSet<Reserva>();
    }

    public Usuari(String username, String nom, String email) {
        this.username = username;
        this.nom = nom;
        this.email = email;
        //reservesCreades = new HashSet<Reserva>();
    }

    // GETTERS & SETTERS

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //public Set<Reserva> getReservesCreades() { return reservesCreades; }

    //public void setReservesCreades(Set<Reserva> reservesCreades) { this.reservesCreades = reservesCreades; }

    // FUNCIONS DE LA CLASSE

    /**
     * Obte la informacio de l'usuari
     * @return una llista amb els seguents strings, per ordre: l'username, el nom i l'email de l'usuari
     */
    public InfoUsuari getInfo() {
        return new InfoUsuari(username, nom, email);
    }

    /** CANVIAT: COMENTAT PERQUE EN PRINCIPI NO NECESSITEM LES RESERVES CREADES DE L'USUARI, SI NO PODEM FER EL TRIGGER
     * SHA DE DESCOMENTAR
     */
    /**
     * Comprova si l'usuari ja te reservada una altra sala en un periode que se solapa amb l'indicat
     * @param data: la data del periode que es vol comprovar
     * @param horaInici: l'hora d'inici del periode que es vol comprovar
     * @param horaFi: l'hora de fi del periode que es vol comprovar
     * @return true si l'usuari te una reserva d'una sala que se solapa amb el periode,
     * false en qualsevol altre cas

    public boolean usuariSolapaReservaDeSala(Date data, int horaInici, int horaFi) {
        Boolean salaSolapada = false;

        // Recorrer la llista de reserves que ha creat l'usuari i comprovar si alguna se solapa
        // amb el periode passat per parametre
        for (Reserva reserva : reservesCreades) {
            // Comprovar si la reserva es d'una sala i si hi ha solapacio
            if (reserva.esReservaDeSala() && reserva.solapa(data, horaInici, horaFi)) salaSolapada = true;
        }
        return salaSolapada;
    }

    /**
     * Afegeix la reserva passada com a parametre a la llista de reserves creades per l'usuari
     * @param reserva: la reserva que s'afegira a les reserves creades per l'usuari

    public void assignaReserva(Reserva reserva) {
        reservesCreades.add(reserva);
    }


    **/
}
