package tramwatch.pojo;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by krzysztofowczarek on 04/06/15.
 */
public class BusStop {

    //@XmlElement(name="id")
    private Integer id;

    //@XmlElement(name="name")

    private String number;

    private String name;

    private String direction;

    private String lat;

    private String lon;

    public BusStop() {}

    public BusStop(Integer id, String name, String direction, String lat, String lon) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.lat = lat;
        this.lon = lon;
    }

    @XmlElement(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="direction")
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @XmlElement(name="lat")
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @XmlElement(name="lon")
    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @XmlElement(name="number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
