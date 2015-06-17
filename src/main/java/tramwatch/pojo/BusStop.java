package tramwatch.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by krzysztofowczarek on 17/06/15.
 */
@XmlRootElement
public class BusStop {

    @XmlElement
    private Integer id;

    @XmlElement
    private String name;

    private List<BusLine> busLineList;

    public BusStop() {}

    public BusStop(Integer id, String name, List<BusLine> busLineList) {
        this.id = id;
        this.name = name;
        this.busLineList = busLineList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BusLine> getBusLineList() {
        return busLineList;
    }

    public void setBusLineList(List<BusLine> busLineList) {
        this.busLineList = busLineList;
    }
}
