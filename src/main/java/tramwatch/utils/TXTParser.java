package tramwatch.utils;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by krzysztofowczarek on 05/06/15.
 */
public class TXTParser {

    private final Path path;
    private final static Charset ENCODING = StandardCharsets.UTF_8;

    private StopObject stopObject = null;
    private String name = null;

    private PrintWriter writer;

    final Pattern patternWithSpaces = Pattern.compile("([^\\s]+)");
    final Pattern patternWithCoords = Pattern.compile("");

    private boolean PR = false;

    public TXTParser(String file) throws FileNotFoundException, UnsupportedEncodingException {
        path = Paths.get(file);

        writer = new PrintWriter("/Users/krzysztofowczarek/Desktop/test2.csv", "UTF-8");

    }

    public final void processLineByLine() throws IOException {
        try {
            Scanner scanner =  new Scanner(path, ENCODING.name());
            while (scanner.hasNextLine()){
                //System.out.println(scanner.nextLine());
                processLine(scanner.nextLine());
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }

    @Transactional
    protected void processLine(String aLine){
        if (aLine.contains("--")) {
            name = aLine.split(",")[0].split("  +")[2];
            System.out.println("NAME: " + name);
        }

        if (aLine.contains("*PR")) {
            //System.out.println(aLine);
            PR = true;

            this.stopObject = new StopObject();


            System.out.println("SETTING: " + name);
            this.stopObject.setName(name);

            /*final Matcher matcher = patternWithSpaces.matcher(aLine);
            stopObject.setCode(Integer.parseInt(matcher.group(0)));
            stopObject.setName(matcher.group(1));*/
        }

        if (PR) {
            if (aLine.contains("Y=") && aLine.contains("X=")) {
                //System.out.println(aLine);
                String[] test = aLine.split(",");
                for (String s: test) {
                    if (s.contains("Ul.")) {
                        List<String> test2 = new ArrayList<String>();
                        for (String ss: s.split("  ")) {
                            if(!ss.isEmpty()) {
                                test2.add(ss);
                            }
                        }

                        this.stopObject.setId(Integer.parseInt(test2.get(0).replaceAll("\\s+", "")));
                        this.stopObject.setNumber(test2.get(1).trim().replaceAll(" +", " "));
                        //this.stopObject.setName(test2.get(2).trim().replaceAll(" +", " "));
                        //System.out.println(stopObject.getName() + " " + stopObject.getId());
                    }else if(s.contains("Kier")) {
                        String[] aa = s.split(":");
                        aa[1] = aa[1].trim().replaceAll(" +", " ");
                        this.stopObject.setDirection(aa[1]);
                    }else if(s.contains("Y=") && s.contains("X=") && !s.contains("xxx.") && !s.contains("yyy.")) {
                        s = s.replaceAll("\\s+","");

                        stopObject.setLat(s.substring(2, 11));
                        stopObject.setLon(s.substring(13, s.length()));

                        //System.out.println(stopObject.getLat() + " AND " + stopObject.getLon());
                    }
                }
            }
        }

        if (aLine.contains("#PR")) {
            if(stopObject.getLat() != null && stopObject.getLon() != null) {
                writer.append(String.valueOf(stopObject.getId()));
                writer.append(',');
                writer.append(stopObject.getNumber());
                writer.append(',');
                writer.append(stopObject.getName());
                writer.append(',');
                writer.append(stopObject.getDirection());
                writer.append(',');
                writer.append(stopObject.getLat());
                writer.append(',');
                writer.append(stopObject.getLon());
                writer.append('\n');
            }

            this.stopObject = null;
            this.name = null;
        }
    }
}
