package mumbleIceConnector;

import java.util.logging.Logger;


public class MumbleIceConnector {
    
    // milliseconds - 1 hour is 3600000
    final static int sleeptimer = 678688678 / 2;
     
    // lolstats yes no
    static final boolean lol = true;
    
    // name of lolstattopchannel
    static final String lolTopchannel = "--- LoL Ranking (EUW) ---";
    
    // more lol-parsing for more accurate information. unexpected results possible.
    static final boolean advancedLol = true;
    
    //weather yes no
    static final boolean weather = true;
    
    // name of weathertopchannel
    static final String weatherTopchannel = "--- Wetterkontrollstation ---";
    
    //port
    static final String port = "6502";
    
    //ip
    static final String ip = "127.0.0.1";
    
    private static Logger logger;
    
    public static void main(String[] args) {
        
        logger = ConnectorUtil.createLogger();
        
        
        ConnectionHandler connectionHandler = new ConnectionHandler(logger);
        Thread t = new Thread(connectionHandler);
        t.start();
    }
}
