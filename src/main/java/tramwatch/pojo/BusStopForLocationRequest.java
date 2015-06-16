package tramwatch.pojo;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by krzysztofowczarek on 16/06/15.
 */
public class BusStopForLocationRequest {

    @XmlElement(name="lat")
    private String lat;

    @XmlElement(name="lon")
    private String lon;

    @XmlElement(name="radius")
    private Integer radius;

    public BusStopForLocationRequest() {}

    public BusStopForLocationRequest(String lat, String lon, Integer radius) {
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }
}
