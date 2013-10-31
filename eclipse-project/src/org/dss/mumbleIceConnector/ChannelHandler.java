package org.dss.mumbleIceConnector;

import java.util.Map;
import java.util.logging.Logger;

import Murmur.Channel;
import Murmur.InvalidSecretException;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;

public abstract class ChannelHandler implements Runnable{
    
    
    ServerPrx serv = null;
    Channel chan = null;
    static Logger logger = null;
    
    public ChannelHandler(ServerPrx serv, int id, Logger logger) {
        ChannelHandler.logger = logger;
        this.serv = serv;
        try {
            this.chan = getChanByID(serv, id);
        } catch (InvalidSecretException e) {
            System.err.println("InvalidSecretException! check murmur ice config!!");
        } catch (ServerBootedException e) {
            System.err.println("ServerBootedException! check server status!");
        }
    }
    
//    protected static String getTimestamp() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//        Date now = new Date();
//        String strDate = sdf.format(now);
//        return strDate;
//    }

    
    protected static Channel getChanByID(ServerPrx serv, int id) throws InvalidSecretException, ServerBootedException {   
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
