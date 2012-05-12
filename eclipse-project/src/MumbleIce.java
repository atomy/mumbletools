import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import Murmur.Channel;
import Murmur.InvalidSecretException;
import Murmur.Meta;
import Murmur.MetaPrx;
import Murmur.MetaPrxHelper;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;


public class MumbleIce {

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
            
            // work with server index 0 if any found
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
            					// found a channel starting with "s" (name)
            					if(subChan.name.charAt(0) == 's') {
            						// set name so something different
            						subChan.name = "supidopimo!";
            					} else if(subChan.name.charAt(0) == 'a') {
            						subChan.name = "aDOLF";
            					}
            					// save channel data structure and apply
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
}