package pojo;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by krzysztofowczarek on 04/06/15.
 */
public class BusStop {

    //@XmlElement(name="id")
    private Integer id;

    //@XmlElement(name="name")
    private String name;

    public BusStop() {}

    public BusStop(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
