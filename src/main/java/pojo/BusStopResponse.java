package pojo;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by krzysztofowczarek on 03/06/15.
 */
@XmlRootElement(name = "dataResult", namespace = "http://api.openmiddleware.pl/ztm/busstop")
@XmlAccessorType(XmlAccessType.FIELD)
public class BusStopResponse {

    @XmlElement(name = "busstop")
    private List<BusStop> busStopList;

    public List<BusStop> getBusStops() {
        return busStopList;
    }

    public void setBusStopList(List<BusStop> busStopList) {
        this.busStopList = busStopList;
    }
}
