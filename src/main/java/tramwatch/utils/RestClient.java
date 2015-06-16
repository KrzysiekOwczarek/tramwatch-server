package tramwatch.utils;

import org.springframework.web.client.RestTemplate;
import tramwatch.pojo.BusStop;
import tramwatch.pojo.BusStopForLocationRequest;

/**
 * Created by krzysztofowczarek on 16/06/15.
 */
public class RestClient {
    public static void main(String[] args) {
        try {
            /*HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet("http://127.0.0.1:8080/xml");
            getRequest.addHeader("accept", "application/json");
            HttpResponse httpResponse = httpClient.execute(getRequest);

            if(httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((httpResponse.getEntity().getContent())));
            String s = null;

            while ((s=br.readLine())!=null) {
                System.out.println(s);
            }*/

            RestTemplate restTemplate = new RestTemplate();
            BusStopForLocationRequest busStopForLocationRequest = new BusStopForLocationRequest();

            busStopForLocationRequest.setLon("TESTTEST");
            busStopForLocationRequest.setLat("TESTTEST");
            busStopForLocationRequest.setRadius(10);

            BusStop busStop = restTemplate.postForObject("http://127.0.0.1:8080/getTest", busStopForLocationRequest,
                    BusStop.class);

            System.out.println(busStop.getName());
        } catch(Exception ex) {

        }
    }
}
