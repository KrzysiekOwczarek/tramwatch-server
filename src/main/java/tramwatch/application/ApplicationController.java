package tramwatch.application;


import org.postgresql.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tramwatch.managers.BusStopManager;
import tramwatch.pojo.BusStop;
import tramwatch.pojo.BusStopForLocationRequest;
import tramwatch.utils.SAXParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by krzysztofowczarek on 30/05/15.
 */
@RestController
public class ApplicationController {

    @Autowired
    private SAXParser saxParser;

    @Autowired
    private BusStopManager busStopManager;

    @POST
    @Path("getStopsForLocation")
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<BusStop> getStopsForLocation(BusStopForLocationRequest busStopForLocationRequest) {
        return busStopManager.getStopsForLoc(busStopForLocationRequest.getLat(),
                busStopForLocationRequest.getLon(), busStopForLocationRequest.getRadius());
    }

    @RequestMapping(value = "getTest", method = RequestMethod.POST)
    //@Consumes({ MediaType.APPLICATION_JSON })
    //@Produces({ MediaType.APPLICATION_JSON })
    public BusStop getTest(@RequestBody BusStopForLocationRequest busStopForLocationRequest) {
        System.out.println(busStopForLocationRequest.getLat() + busStopForLocationRequest.getLon());
        return busStopManager.getTest();
    }

    @GET
    @RequestMapping("xml")
    String xml() throws IOException, JAXBException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        StringBuilder authentication = new StringBuilder().append("pw").append(":").append("testpw4$");
        String result = Base64.encodeBytes(authentication.toString().getBytes());

        httpHeaders.add("Authorization", "Basic " + result);

        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.bihapi.pl/orange/oracle/ztm/busstop?busstop=Umi%",
                HttpMethod.GET, request, String.class);

        saxParser.parseDocument(response.getBody());
        saxParser.printData();

        return "OK";
    }

    /*@GET
    @RequestMapping("test")
    String test() throws IOException {

        TXTParser txtParser = new TXTParser("/Users/krzysztofowczarek/Desktop/timesheet.txt", entityManager);
        txtParser.processLineByLine();

        return "OK";
    }*/

    static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
