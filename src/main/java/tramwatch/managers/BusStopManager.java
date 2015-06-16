package tramwatch.managers;

import tramwatch.pojo.BusStop;

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

    public List<BusStop> getStopsForLoc(String lat, String lon, Integer radius) {
        List<BusStop> busStops = new ArrayList<BusStop>();
        //busStops.add(new BusStop("TEST", "TEST", 20));
        return null;
    }

    public BusStop getTest() {
        return new BusStop(1, "TEST", "TEST", "TEST", "TEST");
    }
}
