package org.dss.mumbleIceConnector;

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
            logger.log(Level.FINEST, "Locationchannel detected: " + plz);
            Location location = new Location(plz, logger);
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
            URL url = new URL("http://www.google.de/ig/api?weather=" + location.getPlz() + "+Germany");
            logger.log(Level.FINEST, "Google URL: " + url.toExternalForm());
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "Cp1252"));
            String ort = in.readLine().replace("<?xml version=\"1.0\"?><xml_api_reply version=\"1\"><weather module_id=\"0\" tab_id=\"0\" mobile_row=\"0\" mobile_zipped=\"1\" row=\"0\" section=\"0\" ><forecast_information><city data=\"", "");
            ort = ort.split(",")[0];
            logger.log(Level.FINER, location.getPlz()+ ": " + ort);
            location.setLocationName(ort);

            URL newurl = new URL("http://thefuckingweather.com/?where="+ ort+"&unit=c");
            logger.log(Level.FINER, "Fucking URL: " + newurl.toExternalForm());
            BufferedReader in2 = new BufferedReader(new InputStreamReader(newurl.openStream()));
            logger.log(Level.FINER, "Fucking Stream opened: " + newurl.toExternalForm());
            String line;
            while ((line = in2.readLine()) != null) {
                
                
                if (line.contains("<p class=\"large\"><span class=\"temperature\" tempf=\"")) {
                    logger.log(Level.FINEST, location.getPlz() +  "'s templine: " + line);
                    line = line.replace("<p class=\"large\"><span class=\"temperature\" tempf=\"","");
                    line = line.replace("</span>&#176;?!</p><div class=\"remarkContainer\">", "");
                    line = line.replaceFirst("\\d+", "");
                    line = line.replace("\">", "");
                    try {
                        location.setTemp(Integer.parseInt(line.trim()));
                        logger.log(Level.FINEST, location.getPlz() +  "'s Fucking Temp: " + location.getTemp());
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
