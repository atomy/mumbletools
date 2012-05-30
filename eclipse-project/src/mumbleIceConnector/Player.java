package mumbleIceConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    
    private String name;
    private Logger logger;
    
    public int getOfficialRating() {
        return officialRating;
    }

    public void setOfficialRating(int officialRating) {
        this.officialRating = officialRating;
    }

    public int getOfficialWins() {
        return officialWins;
    }

    public void setOfficialWins(int officialWins) {
        this.officialWins = officialWins;
    }

    public int getOfficialLosses() {
        return officialLosses;
    }

    public void setOfficialLosses(int officialLosses) {
        this.officialLosses = officialLosses;
    }

    public int getLolKingRating() {
        return lolKingRating;
    }

    public void setLolKingRating(int lolKingRating) {
        this.lolKingRating = lolKingRating;
    }

    public int getLolKingWins() {
        return lolKingWins;
    }

    public void setLolKingWins(int lolKingWins) {
        this.lolKingWins = lolKingWins;
    }

    public int getLolKingLosses() {
        return lolKingLosses;
    }

    public void setLolKingLosses(int lolKingLosses) {
        this.lolKingLosses = lolKingLosses;
    }

    public String getName() {
        return name;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int officialRating;
    private int officialWins;
    private int officialLosses;
    private int lolKingRating;
    private int lolKingWins;
    private int lolKingLosses;
    private String id;
    
    public Player(String name, Logger logger) {
        if (name.endsWith(":")) {
            name = name.substring(0, name.length() - 1);
        }
        this.name = name;
        this.logger = logger;
    }
    
    public String officialString() {
        if (this.getOfficialRating() != 0)
            return name + " -  Elo: " + officialRating + " W: "+ officialWins + "  L: " + officialLosses;
        else {
            return name + "  -  stats unavailable :(";
        }
    }
    
    public String lolKingString() {
        return name + " -  Elo: " + (lolKingRating == 0 ? "-" : lolKingRating) + " W: "+ lolKingWins + "  L: " + lolKingLosses;
    }
    
    public String toString(int i) {
        return null;
    }
    
    public void retrieveId() {
        URL idgrabber = null;
        try {
            String stringurl = "http://lolmatches.com/playersearch/" + URLEncoder.encode(this.getName(), "UTF-8");
            idgrabber = new URL(stringurl);
            logger.log(Level.FINEST, "URL for ID-retrieval: " + stringurl);
            InputStream ins = idgrabber.openStream();
            InputStreamReader insr = new  InputStreamReader(ins);
            BufferedReader in0 = new BufferedReader(insr);
            String zeile0 = null;
            boolean foundID = false;
            while ((zeile0 = in0.readLine()) != null) {
                if (foundID) {
                    Pattern p = Pattern.compile("\\d+");
                    Matcher m = p.matcher(zeile0);
                    if (m.find()) {
                        this.setId((m.group()));
                        foundID = false;
                        logger.log(Level.FINER, "Id retrieved: " + this.getId());
                        return;
                    }
                }
                if (zeile0.contains("<td>EU West</td>")) {
                    foundID = true;
                }
            }
            if (this.id == null) {
                logger.log(Level.SEVERE, "Id-retrieval for " + this.name  + " failed!");
            }
        } catch (MalformedURLException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        }
    }
}
