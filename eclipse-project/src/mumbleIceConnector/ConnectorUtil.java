package org.dss.mumbleIceConnector;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectorUtil {
    protected static Logger createLogger() {
        Logger logger = Logger.getLogger("MumbleIceConnectorLogger");
        FileHandler fh = null;
        Formatter form = new MessageFormatFormatter();
        try {
            fh = new FileHandler("MIConnector.log", true);
            fh.setFormatter(form);
        } catch (SecurityException e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
        
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
        logger.log(Level.FINE, "");
        logger.log(Level.FINE, "");
        logger.log(Level.FINE, "Connector starting up.....");
        return logger;
    }
}
