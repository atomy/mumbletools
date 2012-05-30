package mumbleIceConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import Murmur.InvalidChannelException;
import Murmur.InvalidSecretException;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;


public class WeatherHandler extends ChannelHandler{
    
    public WeatherHandler(ServerPrx serv, int id, Logger logger) {
        super(serv, id, logger);
    }

    @Override
    public void run() {
        if(chan != null) {
            String plz = chan.name.split("\\s+")[0];
            Location location = null;
            try {
                location = new Location(plz);
            } catch (Exception e) {
                System.err.println("Integer parse Error");
            }
            if (location != null) {
                parse(location);
                chan.name = location.toString();
                chan.description = location.getFlavour();
                try {
                    serv.setChannelState(chan);
                    logger.log(Level.FINER, "weather parsed for location " + location.getPlz() + ": " + (location.getTemp() != -9000));
                    
                } catch (InvalidChannelException e) {
                    logger.log(Level.SEVERE, "Channel could not be modified - InvalidChannelException");
                } catch (InvalidSecretException e) {
                    logger.log(Level.SEVERE, "InvalidSecretException! check murmur ice config!");
                } catch (ServerBootedException e) {
                    logger.log(Level.SEVERE, "ServerBootedException - check serverstatus!");
                }
            }
        }
        
    }

    private static void parse(Location location) {
        try {

            URL url = new URL("http://www.google.de/ig/api?weather=" + URLEncoder.encode(location.getPlz() + "+Germany", "UTF-8"));
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "Cp1252"));
            String ort = in.readLine().replace("<?xml version=\"1.0\"?><xml_api_reply version=\"1\"><weather module_id=\"0\" tab_id=\"0\" mobile_row=\"0\" mobile_zipped=\"1\" row=\"0\" section=\"0\" ><forecast_information><city data=\"", "");
            ort = ort.split(",")[0];



            URL newurl = new URL("http://thefuckingweather.com/?where="+URLEncoder.encode(ort, "UTF-8")+"&unit=c");
            BufferedReader in2 = new BufferedReader(new InputStreamReader(newurl.openStream()));

            String line;
            while ((line = in2.readLine()) != null) {
                
                System.out.println(line);
                
                if (line.contains("<div style=\"float: left;\"><span id=\"locationDisplaySpan\" class=\"small\">")) {
                    line = line.replace("<div style=\"float: left;\"><span id=\"locationDisplaySpan\" class=\"small\">", "");
                    line = line.replace("</span></div>", "");
                    location.setLocationName(line.trim().split(",")[0]);
                }
                if (line.contains("<p class=\"large\"><span class=\"temperature\" tempf=\"")) {
                    line = line.replace("<p class=\"large\"><span class=\"temperature\" tempf=\"","");
                    line = line.replace("</span>&#176;?!</p><div class=\"remarkContainer\">", "");
                    line = line.replaceFirst("\\d+", "");
                    line = line.replace("\">", "");
                    try {
                        location.setTemp(Integer.parseInt(line.trim()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                if (line.contains("<p class=\"remark\">")) {
                    line = line.replace("<p class=\"remark\">","");
                    line = line.replace("</p>","");
                    line = line.trim();
                    location.setComment(line);
                }
                if (line.contains("</div><p class=\"flavor\">")) {
                    line = line.replace("<</div><p class=\"flavor\">","");
                    line = line.replace("</p>","");
                    line = line.trim();
                    location.setFlavour(line);
                }
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        
    }
}
