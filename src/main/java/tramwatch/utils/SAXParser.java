package tramwatch.utils;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tramwatch.pojo.BusStop;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by krzysztofowczarek on 05/06/15.
 */
public class SAXParser extends DefaultHandler {

    List<BusStop> busStopList;
    BusStop busStop;
    String value;

    public SAXParser() {}

    public void parseDocument(String xml) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        busStopList = new ArrayList<BusStop>();
        busStop = null;

        try {
            javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new InputSource(new StringReader(xml)), this);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printData() {
        for (BusStop b : busStopList) {
            System.out.println(b.getName());
        }
    }

    @Override
    public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException {
        if (elementName.equalsIgnoreCase("busstop")) {
            busStop = new BusStop();
        }
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        if (element.equalsIgnoreCase("busstop")) {
            busStopList.add(busStop);
        }

        if (element.equalsIgnoreCase("id")) {
            busStop.setId(Integer.parseInt(value));
        }

        if (element.equalsIgnoreCase("name")) {
            busStop.setName(value);
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        value = new String(ac, i, j);
    }
}