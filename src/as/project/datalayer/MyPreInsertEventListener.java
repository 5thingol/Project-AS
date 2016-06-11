package as.project.datalayer;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.SaveOrUpdateEventListener;

/**
 * Created by romabejar on 11/06/16.
 */
public class MyPreInsertEventListener implements PreInsertEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        return false;
    }
}
