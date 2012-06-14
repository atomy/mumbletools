package org.dss.mumbleIceConnector;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import Murmur.Channel;
import Murmur.InvalidSecretException;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;

public class ChannelUpdater {
	private Logger logger;

	public ChannelUpdater(Logger logger) {
		this.logger = logger;
	}

	public void update(long diff, ServerPrx serv) {
		try {
			// get all channels
			Map<Integer, Channel> channels;

			channels = serv.getChannels(null);

			// loop through channels
			for (Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {
				Channel chan = chanEntry.getValue();

				if (MumbleIceConnector.lol) {
					if (chan.name.endsWith(MumbleIceConnector.lolTopchannel)) {
						ArrayList<Integer> subChans = getSubChannelsOfID(serv, chan.id);

						for (Integer i : subChans) {
							Thread t = new Thread(new LoLHandler(serv, i, logger));
							t.start();
						}
					}
				}

				if (MumbleIceConnector.weather) {
					if (chan.name.endsWith(MumbleIceConnector.weatherTopchannel)) {
						ArrayList<Integer> subChans = getSubChannelsOfID(serv, chan.id);

						for (Integer i : subChans) {
							Thread t = new Thread(new WeatherHandler(serv, i, logger));
							t.start();
						}
					}
				}
				// ///////////////////////////////////////
				// //
				// insert additional subsystems here //
				// //
				// ///////////////////////////////////////
			}
		} catch (InvalidSecretException e) {
			logger.log(Level.SEVERE, "InvalidSecretException! Check murmur ice config!");
		} catch (ServerBootedException e) {
			logger.log(Level.SEVERE, "Server booted? Could not derive critical information!");			
		}
	}

	// return a list of all sub channel-ids of the given channel (specified by
	// id)
	private static ArrayList<Integer> getSubChannelsOfID(ServerPrx serv, int id) throws InvalidSecretException, ServerBootedException {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Map<Integer, Channel> channels = serv.getChannels(null);
		for (Map.Entry<Integer, Channel> chanEntry : channels.entrySet()) {
			Channel chan = chanEntry.getValue();
			if (chan.parent == id) {
				result.add(chan.id);
			}
		}
		return result;
	}
}
