package tramwatch.pojo;

import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by krzysztofowczarek on 16/06/15.
 */
@Entity
@Table(name="busstop")
public class BusStopFromDBEntity implements Serializable {

    @Id
    @Column(name="id")
    private String id;

    @Column(name="number")
    private String number;

    @Column(name="name")
    private String name;

    @Column(name="direction")
    private String direction;

    @Column(name="lat")
    private String lat;

    @Column(name="lon")
    private String lon;

    public BusStopFromDBEntity() {

    }

    public BusStopFromDBEntity(String id, String number, String name, String direction, String lat, String lon) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.direction = direction;
        this.lat = lat;
        this.lon = lon;
    }

    public BusStopFromDBEntity(BusStopFromDB object){
        BeanUtils.copyProperties(this, object);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return "0" + number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
}
