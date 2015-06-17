package tramwatch.utils;

import org.springframework.web.client.RestTemplate;
import tramwatch.pojo.BusStopForLocationRequest;

/**
 * Created by krzysztofowczarek on 16/06/15.
 */
public class RestClient {
    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            BusStopForLocationRequest busStopForLocationRequest = new BusStopForLocationRequest();

            busStopForLocationRequest.setLat("52.224010");
            busStopForLocationRequest.setLon("21.071149");
            busStopForLocationRequest.setRadius(1000);

            /*BusStop busStop = restTemplate.postForObject("http://127.0.0.1:8080/getTest", busStopForLocationRequest,
                    BusStop.class);

            System.out.println(busStop.getName());*/

            String name = restTemplate.postForObject("http://127.0.0.1:8080/getResponse", busStopForLocationRequest,
                    String.class);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
