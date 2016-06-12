import as.project.presentation.CtrlCrearReservaAmbNotificacio;
import as.project.presentation.View;

/**
 * Created by sergi on 12/06/16.
 */
public class Main {
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater (
                new Runnable() {
                    public void run() {
                        CtrlCrearReservaAmbNotificacio ctrl = new CtrlCrearReservaAmbNotificacio();
                    }});
    }
}
