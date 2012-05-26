import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Murmur.Channel;
import Murmur.InvalidSecretException;
import Murmur.MetaPrx;
import Murmur.MetaPrxHelper;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;


public class MumbleIce implements Runnable{
    
    boolean running = true;
    
    // milliseconds - 1 hour is 3600000
    int sleeptimer = 3600000 / 2;
    
    // lolstats yes no
    boolean lol = true;
    // name of lolstattopchannel
    String lolTopchannel = "--- LoL Ranking (EUW) ---";
    
    //weather yes no
    boolean weather = true;
    // name of weathertopchannel
    String wetterTopchannel = "--- Wetterkontrollstation ---";
    
    //port
    String port = "6502";
    
    //ip
    String ip = "127.0.0.1";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Thread t = new Thread(new MumbleIce());
	    t.setDaemon(true);
	    t.run();
	}

	// return a list of all sub channel-ids of the given channel (specified by id)
	private static ArrayList<Integer> getSubChannelsOfID(ServerPrx serv, int id) throws InvalidSecretException, ServerBootedException {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Map<Integer, Channel> channels = serv.getChannels(null);
		for(Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {
			Channel chan = chanEntry.getValue();
			if(chan.parent == id) {
				result.add(chan.id);			
			}
		}
		return result;
	}

	// return channel object by id
	private static Channel getChanByID(ServerPrx serv, int id) throws InvalidSecretException, ServerBootedException {	
		Map<Integer, Channel> channels = serv.getChannels(null);
		for(Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {
			Channel chan = chanEntry.getValue();
			if(chan.id == id) {
				return chan;
			}
		}
		return null;		
	}
	
	/**
	 * parse playerstats from official database
	 *
	 * @param player
	 */
	private static void parseOfficialStats(Player player) {
	    
	    Pattern p = Pattern.compile("\\d+");

        boolean foundRating = false;
        boolean foundRank = false;
        boolean foundWins = false;
        boolean foundLosses = false;
        boolean complete = false;

        String rank = "";

        try {
            URL url = new URL("http://competitive.euw.leagueoflegends.com/ladders/euw/current/rankedsolo5x5?summoner_name=" + URLEncoder.encode(player.getName(), "UTF-8"));
            InputStream is = url.openStream();
            InputStreamReader isr = new  InputStreamReader(is);
            BufferedReader in = new BufferedReader(isr);
            String zeile = null;
            

            while ((zeile = in.readLine()) != null) {

                if (foundRating) {
                    Matcher m = p.matcher(zeile);
                    if (m.find()) {
                        player.setOfficialRating(Integer.parseInt(m.group()));
                    }
                    foundRating = false;
                }
                if (foundWins) {
                    Matcher m = p.matcher(zeile);
                    if (m.find()) {
                        player.setOfficialWins(Integer.parseInt(m.group()));
                    }
                    foundWins = false;
                }
                if (foundLosses) {
                    Matcher m = p.matcher(zeile);
                    if (m.find()) {
                        player.setOfficialLosses(Integer.parseInt(m.group()));
                    }
                    foundLosses = false;
                }
                if (foundRank) {
                    Matcher m = p.matcher(zeile);
                    while (m.find()) {
                        rank += m.group();
                    }
                    player.setOfficialRank(Integer.parseInt(rank));
                    foundRank = false;
                }

                if (!complete) {
                    if (zeile.contains("<td class=\"views-field views-field-rating views-align-center\" >")) {
                        complete = true;
                        foundRating = true;
                    }
                    if (zeile.contains("<td class=\"views-field views-field-rank views-align-center\" >")) {
                        foundRank = true;
                    }
                    if (zeile.contains("<td class=\"views-field views-field-wins views-align-center\" >")) {
                        foundWins = true;
                    }
                    if (zeile.contains("<td class=\"views-field views-field-losses views-align-center\" >")) {
                        foundLosses = true;
                    }
                }

            }
            isr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	private static void getWeather(Location location) {
	    try {

	        // Create a URL for the desired page
	        URL url = new URL("http://www.google.de/ig/api?weather=" + URLEncoder.encode(location.getPlz() + " germany", "UTF-8"));
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "Cp1252"));
	        String ort = in.readLine().replace("<?xml version=\"1.0\"?><xml_api_reply version=\"1\"><weather module_id=\"0\" tab_id=\"0\" mobile_row=\"0\" mobile_zipped=\"1\" row=\"0\" section=\"0\" ><forecast_information><city data=\"", "");
	        ort = ort.split(",")[0];

	        //	          System.out.println(URLEncoder.encode(ort, "UTF-8"));
	        //	          System.out.println("http://thefuckingweather.com/?where="+URLEncoder.encode(ort, "UTF-8")+"&unit=c");


	        URL newurl = new URL("http://thefuckingweather.com/?where="+URLEncoder.encode(ort, "UTF-8")+"&unit=c");
	        BufferedReader in2 = new BufferedReader(new InputStreamReader(newurl.openStream()));

	        // Read all the text returned by the server
	        //	            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        String line;
	        while ((line = in2.readLine()) != null) {
	            if (line.contains("<div style=\"float: left;\"><span id=\"locationDisplaySpan\" class=\"small\">")) {
	                line = line.replace("<div style=\"float: left;\"><span id=\"locationDisplaySpan\" class=\"small\">", "");
	                line = line.replace("</span></div>", "");
	                //line = line.trim().split("\\s+")[0];
	                location.setLocationName(line.trim().split("\\s+")[0].replace(",", ""));
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
	        //	            System.out.println(location.getLocationName());
	        //	            System.out.println(location.getTemp());
	        //	            System.out.println(location.getComment());
	        //	            System.out.println();
	        //	            System.out.println(location.toString());
	        in.close();
	    } catch (MalformedURLException e) {
	    } catch (IOException e) {
	    }
	}
	
	private static String getTimestamp() {
	    Date date = new Date(System.currentTimeMillis());
	    return date.toLocaleString();
	}

    @Override
    public void run() {
        while (running) {
            gatherAndParseInfoToMumble();
            try {
                Thread.sleep(sleeptimer);
            } catch (InterruptedException e) {
                System.err.println("Interrupted oO");
            }
        }
    }

    private void gatherAndParseInfoToMumble() throws Error {
        Ice.Communicator ic = null;
        try {
            // lets connect to the murmurs ice endpoint
            ic = Ice.Util.initialize();
            Ice.ObjectPrx base = ic.stringToProxy("Meta:tcp -h " + ip + " -p " + port);
            MetaPrx meta = MetaPrxHelper.checkedCast(base);
   
            // didnt work
            if (meta == null)
                throw new Error("Invalid proxy");

            // get all running servers on the murmur instance
            ServerPrx servs[] = meta.getBootedServers();
            
            // get config of our meta connection
            Map<String, String> config = meta.getDefaultConf();
            
            
            if(servs.length > 0) {
              ServerPrx serv = servs[0];          
                
                // get all channels
                Map<Integer, Channel> channels = serv.getChannels(null);
                
                // loop through channels
                for(Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {                  
                    Channel chan = chanEntry.getValue();
                    
                    
                    if (lol) {
                        if(chan.name.endsWith(lolTopchannel)) {
                            // get all sub channels and loop
                            ArrayList<Integer> subChans = getSubChannelsOfID(serv, chan.id);       
                            for(Integer i : subChans) {
                                // get subchan and work with it
                                Channel subChan = getChanByID(serv, i);
                                if(subChan != null) {
                                    String playername = subChan.name.split("\\s+")[0];
                                    Player player = new Player(playername);
                                    parseOfficialStats(player);
                                    subChan.name = player.officialString();
                                    subChan.description = "last updated: " + getTimestamp();
                                    serv.setChannelState(subChan);
                              }
                            }
                        }
                    }
                    if (weather) {
                        if (chan.name.endsWith(wetterTopchannel)) {
                            ArrayList<Integer> subChans = getSubChannelsOfID(serv, chan.id);   
                            for(Integer i : subChans) {
                                Channel subChan = getChanByID(serv, i);
                                if(subChan != null) {
                                    String plz = subChan.name.split("\\s+")[0];
                                    Location location = null;
                                    try {
                                        location = new Location(plz);
                                    } catch (Exception e) {
                                        System.err.println("Integer parse Error");
                                    }
                                    if (location != null) {
                                        getWeather(location);
                                        subChan.name = location.toString();
                                        subChan.description = location.getFlavour();
                                        serv.setChannelState(subChan);
                                    }
                              }
                            }
                        }
                    }
                }
            }
        } catch (Ice.LocalException e) {
            e.printStackTrace();
        } catch (InvalidSecretException e) {
            System.err.println("InvalidSecretException! check murmur ice config!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if (ic != null) {
            // Clean up
            try {
                ic.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}