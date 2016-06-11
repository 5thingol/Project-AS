package as.project.domain.excepcions;

import as.project.domain.model.ReservaAmbNotificacio;

/**
 * Created by romabejar on 11/06/16.
 */
public class ReservaAmbNotificacioJaExisteix extends Exception{
    public static final long serialVersionUID = 1L;

    /* Mètode creador de la classe, el qual invoca a super que  és la cosntructura
     * de la classe Exception */
    public ReservaAmbNotificacioJaExisteix() {
        super();
    }
}
