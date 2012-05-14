import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Murmur.Channel;
import Murmur.InvalidSecretException;
import Murmur.Meta;
import Murmur.MetaPrx;
import Murmur.MetaPrxHelper;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;


public class MumbleIce {
    
//    public static void main(String[] args) {
//        
//        
//        
//        int status = 0;
//        Ice.Communicator ic = null;
//        ServerPrx serv = null;
//        ic = Ice.Util.initialize();
//        Ice.ObjectPrx base = ic.stringToProxy("Meta:tcp -h 127.0.0.1 -p 6502");
//        MetaPrx meta = MetaPrxHelper.checkedCast(base);
//        
//        if (meta == null) {
//            throw new Error("Invalid proxy");
//        }
//        ServerPrx servs[] = null;
//        
//        try {
//            servs = meta.getBootedServers();
//            Map<String, String> config = meta.getDefaultConf();
//        } catch (InvalidSecretException e) {
//            System.err.println("InvalidSecretException! check murmur ice config!");
//            status = 1;
//        }
//        
//        if((servs != null) && servs.length > 0) {
//            serv = servs[0];
//            Map<Integer, Channel> channels = null;
//            
//            try {
//                channels = serv.getChannels(null);
//            } catch (InvalidSecretException e) {
//                System.err.println("InvalidSecretException! check murmur ice config!");
//                status = 1;
//            } catch (ServerBootedException e) {
//                System.err.println("Server not booted?");
//                status = 1;
//            }
//            
//            for(Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {                  
//                Channel chan = chanEntry.getValue();
//                System.out.println("main() chan name: " + chan.name + " chan id: " + chan.id);
//              
//                // look for channel with name "..."
//                if(chan.name.endsWith("--- trolololol ---")) {
//            
//                    ArrayList<Integer> subChans = null;
//                    try {
//                        subChans = getSubChannelsOfID(serv, chan.id);
//                    } catch (InvalidSecretException e) {
//                        System.err.println("InvalidSecretException! check murmur ice config!");
//                        status = 1;
//                    } catch (ServerBootedException e) {
//                        System.err.println("Server not booted?");
//                        status = 1;
//                    }     
//                    
//                    
//                    for(Integer i : subChans) {
//                        
//                        Channel subChan = null;
//                        
//                        try {
//                            subChan = getChanByID(serv, i);
//                        } catch (InvalidSecretException e) {
//                            System.err.println("InvalidSecretException! check murmur ice config!");
//                            status = 1;
//                        } catch (ServerBootedException e) {
//                            System.err.println("Server not booted?");
//                            status = 1;
//                        }
//                        
//                        if(subChan != null) {
//                            if(subChan.name.charAt(0) == 'a') {
//                                subChan.name = "aDOLF";
//                            }
//                            String playername = subChan.name.split("\\s+")[0];
//                            Player player = new Player(playername);
//                            parseOfficialStats(player);
//                            subChan.name = player.officialString();
//                            subChan.description = player.officialString();
//                            System.out.println(subChan.name);
//                            serv.setChannelState(subChan);
//                        }
//                        
//                    }
//                }
//            }
//        }
//        else {
//            System.err.println("server not found");
//        }
//        if (ic != null) {
//            // Clean up
//            try {
//                ic.destroy();
//            } catch (Exception e) {
//                System.err.println(e.getMessage());
//                status = 1;
//            }
//        }
//        System.exit(status);          
//    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        int status = 0;
        Ice.Communicator ic = null;
        try {
        	// lets connect to the murmurs ice endpoint
            ic = Ice.Util.initialize();
            Ice.ObjectPrx base = ic.stringToProxy("Meta:tcp -h 127.0.0.1 -p 6502");
            MetaPrx meta = MetaPrxHelper.checkedCast(base);
   
            // didnt work
            if (meta == null)
                throw new Error("Invalid proxy");

            // get all running servers on the murmur instance
            ServerPrx servs[] = meta.getBootedServers();
            
            // get config of our meta connection
            Map<String, String> config = meta.getDefaultConf();
            
            // debug, printout found servers
            System.out.println("main() found servers: " + servs.length);
            
            if(servs.length > 0) {
              ServerPrx serv = servs[0];          
            	
            	// get all channels
            	Map<Integer, Channel> channels = serv.getChannels(null);
            	
            	// loop through channels
            	for(Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {            		
            		Channel chan = chanEntry.getValue();
            		System.out.println("main() chan name: " + chan.name + " chan id: " + chan.id);
            		
            		// look for channel with name "..."
            		if(chan.name.endsWith("--- trolololol ---")) {
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
                                subChan.description = player.officialString();
                                System.out.println(subChan.name);
                                serv.setChannelState(subChan);
                          }
            			}
            		}
            	}
            }
        } catch (Ice.LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (InvalidSecretException e) {
            System.err.println("InvalidSecretException! check murmur ice config!");
            status = 1;                 
        } catch (Exception e) {
            System.err.println(e.getMessage());
            status = 1;
        }
        if (ic != null) {
            // Clean up
            try {
                ic.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);		
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}