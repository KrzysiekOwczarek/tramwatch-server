package tramwatch.application;


import org.postgresql.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import utils.SAXParser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by krzysztofowczarek on 30/05/15.
 */
@RestController
public class ApplicationController {

    @PersistenceContext(unitName = "localDS")
    private EntityManager entityManager;

    @Autowired
    private SAXParser saxParser;

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
}
