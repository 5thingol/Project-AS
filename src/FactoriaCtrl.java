/**
 * Created by guillemc on 19/04/2016.
 */
// Aixo es una primera versio de la factoria de controladors de dades perque compili tot
// TO-DO: CANVIAR TOT PER LA SEGUENT ENTREGA!
public class FactoriaCtrl {

    private static FactoriaCtrl instance;

    private FactoriaCtrl() {

    }

    public static FactoriaCtrl getInstance() {
        if (instance == null) {
            instance = new FactoriaCtrl();
        }
        return instance;
    }

    public CtrlReserva getCtrlReserva() {
        return new CtrlReserva();
    }

    public CtrlRecurs getCtrlRecurs() {
        return new CtrlRecurs();
    }

}
