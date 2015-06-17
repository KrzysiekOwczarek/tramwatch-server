package tramwatch.pojo;

/**
 * Created by krzysztofowczarek on 17/06/15.
 */
public class BusTime {

    private String direction;

    private String nextTime;

    public BusTime() {}

    public BusTime(String direction, String nextTime) {
        this.direction = direction;
        this.nextTime = nextTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNextTime() {
        return nextTime;
    }

    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
    }
}
