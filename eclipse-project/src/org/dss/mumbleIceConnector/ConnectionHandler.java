package org.dss.mumbleIceConnector;

import java.util.logging.Level;
import java.util.logging.Logger;
import Ice.ConnectionRefusedException;
import Ice.ObjectAdapter;
import Murmur.InvalidCallbackException;
import Murmur.InvalidSecretException;
import Murmur.MetaPrx;
import Murmur.MetaPrxHelper;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;


public class ConnectionHandler implements Runnable {

	private boolean running;

	private Logger logger;

	private MetaPrx meta;
	
	private long lastrun;
	
	private ChannelUpdater chanUpdater;
	
	private ObjectAdapter adapter;
	
	private ServerPrx selectedServerProxy;

	public ConnectionHandler(Logger logger) {
		running = true;
		this.logger = logger;

		chanUpdater = new ChannelUpdater(logger);
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
		setup();
		lastrun = System.currentTimeMillis();

		while (isRunning()) {
			//logger.log(Level.FINE, "main loop - calling gatherAndParseInfoToMumble()");
			doChannelUpdate(System.currentTimeMillis() - lastrun);
			
			lastrun = System.currentTimeMillis();

			synchronized (this) {
				try {
					this.wait(MumbleIceConnector.sleeptimer);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void setup() {
		try {
			Ice.Communicator ic = null;

			// lets connect to the murmurs ice endpoint
			ic = Ice.Util.initialize();
			Ice.ObjectPrx base = ic.stringToProxy("Meta:tcp -h " + MumbleIceConnector.ip + " -p " + MumbleIceConnector.port);
			meta = MetaPrxHelper.checkedCast(base);
			
			// add adapter for callbacks
			//adapter = ic.createObjectAdapterWithEndpoints("Callback.Client", "tcp -h " + MumbleIceConnector.ip);
			//adapter.activate();			

			// did'nt work
			if (meta == null) {
				logger.log(Level.SEVERE, "could not create connection to the MetaProxy!");
				return;
			}
			logger.log(Level.INFO, "Connection established!");
			
			// get all running servers on the murmur instance
			ServerPrx servs[] = meta.getBootedServers();			
			
			// just grab the 1st server as long as we got any servers at all
			if (servs.length > 0) {
				selectedServerProxy = servs[0];
				//CallbackManager.installCallbacks(adapter, selectedServerProxy);				
			}
		} catch (Ice.DNSException e) {
			logger.log(Level.SEVERE, "Unable to resolve DNS address, check target server address!");			
		} catch (ConnectionRefusedException e) {
			logger.log(Level.SEVERE, "Connection refused: Wrong port/ip information or server not running!");
		} catch (InvalidSecretException e) {
			logger.log(Level.SEVERE, "InvalidSecretException! Check murmur ice config!!!!");			
		//} catch (InvalidCallbackException e) {
//			logger.log(Level.SEVERE, "InvalidCallbackException! Check code!");	
	//	} catch (ServerBootedException e) {
		//	logger.log(Level.SEVERE, "ServerBootedException! server unreachable!");	
		}
	}

	private void doChannelUpdate(long diff) {
		try {
			if (meta == null) {
				setup();
				return;
			}
			chanUpdater.update(diff, selectedServerProxy);
		} catch (Ice.ConnectionRefusedException e) {
			logger.log(Level.SEVERE, "ConnectionRefusedException! server not reachable!");		
		}
	}
}
