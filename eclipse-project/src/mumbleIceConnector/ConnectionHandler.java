package org.dss.mumbleIceConnector;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import Ice.ConnectionRefusedException;
import Murmur.Channel;
import Murmur.InvalidSecretException;
import Murmur.MetaPrx;
import Murmur.MetaPrxHelper;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;

public class ConnectionHandler implements Runnable {
    
    private boolean running;
    
    private int loops;
    
    private Logger logger;
    

    public ConnectionHandler(Logger logger) {
        loops = 0;
        running = true;
        this.logger = logger;
        
        logger.log(Level.FINE, "ConnectionHandler created");
    }
    
    
    public boolean isRunning() {
        return running;
    }
    
    public synchronized void deactivate() {
        running = false;
        this.notify();
    }

    @Override
    public void run() {
        logger.log(Level.FINE, "trying to establish connection...");
        
        while (isRunning()) {
            
            gatherAndParseInfoToMumble();
            loops++;
            
            synchronized(this) {
                try {
                    this.wait(MumbleIceConnector.sleeptimer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
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

    private void gatherAndParseInfoToMumble() throws Error {
        Ice.Communicator ic = null;
        try {
            // lets connect to the murmurs ice endpoint
            ic = Ice.Util.initialize();
            Ice.ObjectPrx base = ic.stringToProxy("Meta:tcp -h " + MumbleIceConnector.ip + " -p " + MumbleIceConnector.port);
            MetaPrx meta = MetaPrxHelper.checkedCast(base);
   
            // didnt work
            if (meta == null) {
                logger.log(Level.SEVERE, "could not create connection to the MetaProxy!");
            } else {
                logger.log(Level.FINE, "Connection established!");
            }

            // get all running servers on the murmur instance
            ServerPrx servs[] = meta.getBootedServers();
            
            if(servs.length > 0) {
              ServerPrx serv = servs[0];
                
                // get all channels
                Map<Integer, Channel> channels = serv.getChannels(null);
                
                // loop through channels
                for(Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {                  
                    Channel chan = chanEntry.getValue();
                    
                    if (MumbleIceConnector.lol) {
                        if(chan.name.endsWith(MumbleIceConnector.lolTopchannel)) {
                            ArrayList<Integer> subChans = getSubChannelsOfID(serv, chan.id);
                            
                            for(Integer i : subChans) {
                                Thread t = new Thread(new LoLHandler(serv, i, logger));
                                t.start();
                            }
                        }
                    }
                    
                    if (MumbleIceConnector.weather) {
                        if(chan.name.endsWith(MumbleIceConnector.weatherTopchannel)) {
                            ArrayList<Integer> subChans = getSubChannelsOfID(serv, chan.id);
                            
                            for(Integer i : subChans) {
                                Thread t = new Thread(new WeatherHandler(serv, i, logger));
                                t.start();
                            }
                        }
                    }
                    
                     /////////////////////////////////////////
                     //                                     //
                     //  insert additional subsystems here  //
                     //                                     //
                     /////////////////////////////////////////
                    
                }
            }
        } catch (ConnectionRefusedException e) {
            logger.log(Level.SEVERE, "Connection refused: Wrong port/ip information or server not running!");
        } catch (ServerBootedException e) {
            logger.log(Level.SEVERE, "Server booted? Could not derive critical information!");
        } catch (InvalidSecretException e) {
            logger.log(Level.SEVERE, "InvalidSecretException! Check murmur ice config!");
        }
    }
}
