package tramwatch.utils;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tramwatch.pojo.BusLine;
import tramwatch.pojo.BusStop;
import tramwatch.pojo.BusTime;

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
    List<BusLine> busLineList;
    List<BusTime> busTimeList;
    BusStop busStop;
    BusLine busLine;
    String value;

    public SAXParser() {}

    public void parseDocument(String xml) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        busStopList = new ArrayList<BusStop>();
        busLineList = new ArrayList<BusLine>();
        busTimeList = new ArrayList<BusTime>();
        busStop = null;
        busLine = null;

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

        if (elementName.equalsIgnoreCase("line")) {
            busLine = new BusLine();
        }
    }

    @Override
    public void endElement(String s, String s1, String element) throws SAXException {
        if (element.equalsIgnoreCase("busstop")) {
            busStopList.add(busStop);
            busStop = null;
        }

        if (element.equalsIgnoreCase("line")) {
            if (busLine != null) {
                busLine.setLine(value);
                busLineList.add(busLine);
                busLine = null;
            }
        }

        if (element.equalsIgnoreCase("timetable")) {
            String[] times = value.split(",");

            for (String time: times) {
                BusTime busTime = new BusTime();
                busTime.setNextTime(time);
                busTimeList.add(busTime);
            }
        }

        if (element.equalsIgnoreCase("id")) {
            if (busStop != null) {
                busStop.setId(Integer.parseInt(value));
            }
        }

        if (element.equalsIgnoreCase("name")) {
            if (busStop != null) {
                busStop.setName(value);
            }
        }

        if (element.equalsIgnoreCase("direction")) {
            if (busLine != null) {
                busLine.setDirection(value);
            }
        }
    }

    @Override
    public void characters(char[] ac, int i, int j) throws SAXException {
        value = new String(ac, i, j);
    }

    public List<BusStop> getBusStopList() {
        return busStopList;
    }

    public List<BusLine> getBusLineList() {
        return busLineList;
    }

    public List<BusTime> getBusTimeList() {
        return busTimeList;
    }
}