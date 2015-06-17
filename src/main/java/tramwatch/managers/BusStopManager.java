package tramwatch.managers;

import tramwatch.pojo.BusStopFromDB;
import tramwatch.pojo.BusStopFromDBEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysztofowczarek on 16/06/15.
 */
public class BusStopManager {

    @PersistenceContext(unitName = "localDS")
    private EntityManager entityManager;

    public List<BusStopFromDB> getStopsForLoc(String lat, String lon, Integer radius) {
        List<BusStopFromDB> busStops = new ArrayList<BusStopFromDB>();
        //busStops.add(new BusStop("TEST", "TEST", 20));
        return null;
    }

    public List<BusStopFromDBEntity> getName(String lat, String lon, Integer radius) {
        String sqlString = "SELECT *, earth_distance(ll_to_earth(" + lat + "," + lon + "), ll_to_earth(lat, lon))" +
                "as burrito_distance FROM test" +
                " WHERE earth_box(ll_to_earth(" + lat + "," + lon + "), " + radius + ") @> ll_to_earth(lat, lon) " +
                "ORDER by burrito_distance";

        List<BusStopFromDBEntity> busStopEntities =
                (List<BusStopFromDBEntity>) entityManager.createNativeQuery(sqlString, BusStopFromDBEntity.class).getResultList();

        for (BusStopFromDBEntity b : busStopEntities) {
            System.out.println(b.getName());
        }

        return busStopEntities;
    }

}
