package tramwatch.application;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tramwatch.managers.BusStopManager;
import tramwatch.pojo.*;
import tramwatch.utils.SAXParser;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<BusStopFromDB> getStopsForLocation(BusStopForLocationRequest busStopForLocationRequest) {
        return busStopManager.getStopsForLoc(busStopForLocationRequest.getLat(),
                busStopForLocationRequest.getLon(), busStopForLocationRequest.getRadius());
    }

    /*@RequestMapping(value = "getTest", method = RequestMethod.POST)
    public BusStopFromDBEntity getTest(@RequestBody BusStopForLocationRequest busStopForLocationRequest) {
        System.out.println(busStopForLocationRequest.getLat() + busStopForLocationRequest.getLon());

        BusStopFromDBEntity busStop = busStopManager.getName(busStopForLocationRequest.getLat(),
                busStopForLocationRequest.getLon(), busStopForLocationRequest.getRadius());

        System.out.println("CLOSE STOP: " + busStop.getName());

        List<BusStop> busStops = getStops("Afry%");

        Integer id = busStops.get(0).getId();

        System.out.println("REQ with " + id + " AND 0" + busStop.getNumber());

        List<String> lines = getLines(id, "0" + busStop.getNumber());

        for (String s: lines) {
            //System.out.println("ASKING WITH " + id + " , " + busStop.getNumber() + " , " + s);
            //System.out.println("LINE " + s + " " + getTime(id, "0"+busStop.getNumber(), s));
        }

        return busStop;
    }*/

    @RequestMapping(value = "getResponse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public String getResponse(@RequestBody BusStopForLocationRequest busStopForLocationRequest) {

        //TODO: wyciągnij jakoś  sprytniej numbery/wszystkie
        List<BusStopFromDBEntity> busStopFromDBEntityList = busStopManager.getName(busStopForLocationRequest.getLat(),
                busStopForLocationRequest.getLon(), busStopForLocationRequest.getRadius());

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonStops = new JSONArray();

        for (BusStopFromDBEntity busStopFromDBEntity: busStopFromDBEntityList) {
            List<BusStop> busStops = getStops("Afry%");

            for (BusStop busStop: busStops) {

                JSONObject jsonBusstop = new JSONObject();
                jsonBusstop.put("name", busStop.getName());

                //TODO: popraw
                JSONArray jsonVehicles = new JSONArray();




                System.out.println("ASKING FOR ID: " + busStop.getId());
                List<BusLine> busLines = getLines(busStop.getId(), "02");
                busStop.setBusLineList(busLines);

                for (BusLine busLine: busLines) {

                    JSONObject jsonVehicle = new JSONObject();

                    System.out.println("LINE " + busLine.getLine());

                    List<BusTime> busTimeList = getTime(busStop.getId(), "02", busLine.getLine());

                    if (busTimeList.size() != 0) {
                        busLine.setBusTime(busTimeList.get(0));
                        //TODO: to nie next
                        System.out.println("NEXT at: " + busLine.getBusTime().getNextTime());
                        try {
                            jsonVehicle.put("number", busLine.getLine());
                            jsonVehicle.put("type", "bus");
                            jsonVehicle.put("time", busLine.getBusTime().getNextTime());
                            jsonVehicle.put("timeToGo", Math.ceil(Integer.parseInt(busLine.getBusTime().getNextToGo())/60));
                            jsonVehicle.put("direction", busLine.getBusTime().getDirection());
                        }catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    jsonVehicles.add(jsonVehicle);

                    break;
                }

                jsonBusstop.put("vehicles", jsonVehicles);

                jsonStops.add(jsonBusstop);
                break;
            }



            break;
        }

        jsonObject.put("stops", jsonStops);

        System.out.println(jsonObject.toJSONString());

        return jsonObject.toString();
    }

    private List<BusStop> getStops(String name) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        StringBuilder authentication = new StringBuilder().append("pw").append(":").append("testpw4$");
        String result = Base64.encodeBytes(authentication.toString().getBytes());

        httpHeaders.add("Authorization", "Basic " + result);

        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://api.bihapi.pl/orange/oracle/ztm/busstop?busstop="+name,
                HttpMethod.GET, request, String.class);

        saxParser.parseDocument(response.getBody());
        saxParser.printData();

        return saxParser.getBusStopList();
    }

    private List<BusLine> getLines(Integer id, String number) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        StringBuilder authentication = new StringBuilder().append("pw").append(":").append("testpw4$");
        String result = Base64.encodeBytes(authentication.toString().getBytes());

        httpHeaders.add("Authorization", "Basic " + result);

        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.bihapi.pl/orange/oracle/ztm/lines?busstopId=" + id + "&busstopNr=" + number,
                    HttpMethod.GET, request, String.class);

            saxParser.parseDocument(response.getBody());
        } catch(HttpClientErrorException ex) {
            System.out.println("No result for id: " + id + " and no: " + number);
        }

        return saxParser.getBusLineList();
    }

    List<BusTime> getTime(Integer id, String number, String line) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();

        StringBuilder authentication = new StringBuilder().append("pw").append(":").append("testpw4$");
        String result = Base64.encodeBytes(authentication.toString().getBytes());

        httpHeaders.add("Authorization", "Basic " + result);

        HttpEntity<String> request = new HttpEntity<String>(httpHeaders);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    "https://api.bihapi.pl/orange/oracle/ztm/time?busstopId=" + id + "&busstopNr=" + number + "&line=" + line,
                    HttpMethod.GET, request, String.class);

            saxParser.parseDocument(response.getBody());
        } catch(HttpClientErrorException ex) {
            System.out.println("No result for line: " + line);
        }

        return saxParser.getBusTimeList();
    }

    /*@GET
    @RequestMapping("test")
    String test() throws IOException {

        TXTParser txtParser = new TXTParser("/Users/krzysztofowczarek/Desktop/timesheet.txt", entityManager);
        txtParser.processLineByLine();

        return "OK";
    }*/

    /*static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
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
    }*/
}
