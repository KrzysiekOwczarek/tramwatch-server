package tramwatch.utils;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by krzysztofowczarek on 15/06/15.
 */
public class StopObjectMgr {

    //@PersistenceContext(unitName = "localDS")
    //private EntityManager entityManager;

    @Transactional
    public void insertState(StopObject stopObject, EntityManager entityManager) {
        entityManager.merge(stopObject);
        entityManager.flush();

        //System.out.println(entityManager.toString());

        if (entityManager == null) {
            System.out.println("NULL :<");
        } else {
            System.out.println("NIE NULL");
        }

        //System.out.println("Inserting " + stopObject.toString());
    }
}
