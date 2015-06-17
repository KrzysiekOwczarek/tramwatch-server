package tramwatch.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by krzysztofowczarek on 17/06/15.
 */
@XmlRootElement
public class BusLine {

    @XmlElement
    private String line;

    @XmlElement
    private String direction;

    private BusTime busTime;

    public BusLine() {}

    public BusLine(String line, String direction, BusTime busTime) {
        this.line = line;
        this.direction = direction;
        this.busTime = busTime;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BusTime getBusTime() {
        return busTime;
    }

    public void setBusTime(BusTime busTime) {
        this.busTime = busTime;
    }
}
