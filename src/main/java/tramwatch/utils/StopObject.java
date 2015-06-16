package tramwatch.utils;


import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by krzysztofowczarek on 04/06/15.
 */
@Entity
@Table(name="busstops")
public class StopObject implements Serializable {

    @Id
    @Column(name="id")
    private Integer id;

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

    public StopObject() {}

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return getId() + "," + getNumber() + "," + getName() + "," + getDirection() + "," + getLat() + "," + getLon();
    }
}
