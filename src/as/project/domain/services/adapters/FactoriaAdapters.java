package as.project.domain.services.adapters;

/**
 * Created by guillemc on 07/06/2016.
 */
public class FactoriaAdapters {

    private static FactoriaAdapters instance;

    private FactoriaAdapters() {

    }

    public static FactoriaAdapters getInstance() {
        if (instance == null) {
            instance = new FactoriaAdapters();
        }
        return instance;
    }

    public IServeiMissatgeriaAdapter getServeiMissatgeriaAdapter() {
        return new ServeiMissatgeriaAdapter();
    }

}
