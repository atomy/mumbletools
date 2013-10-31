package org.dss.mumbleIceConnector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import Murmur.Channel;
import Murmur.InvalidChannelException;
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
						logger.log(Level.FINE, "Found LoL-Channel: " + chan.name);
						ArrayList<Integer> subChans = getSubChannelsOfID(serv, chan.id);

						if (!MumbleIceConnector.checkApiLimit()) {
							String err = String.format("API Limit reached!");
							logger.log(Level.WARNING, err);
							chan.description = err;
							serv.setChannelState(chan);
						} else if (subChans.size() > MumbleIceConnector.maxLolCannels) {
							String err = String.format("Found too much LoL-Channels (%d)", subChans.size());
							logger.log(Level.WARNING, err);
							chan.description = err;
							serv.setChannelState(chan);
						} else {
							for (Integer i : subChans) {
								Thread t = new Thread(new LoLHandler(serv, i, logger));
								t.start();
							}

							DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");							
							Date date = new Date();
							String up = String.format("Last update: %s", dateFormat.format(date));
							
							chan.description = up;
							serv.setChannelState(chan);							
						}
					}
				}

				if (MumbleIceConnector.weather) {
					if (chan.name.endsWith(MumbleIceConnector.weatherTopchannel) && false) { // TODO
						logger.log(Level.INFO, "Found Weather-Channel: " + chan.name);
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
			logger.log(Level.SEVERE, "InvalidSecretException! Check murmur ice config!!!");
		} catch (ServerBootedException e) {
			logger.log(Level.SEVERE, "Server booted? Could not derive critical information!");			
		} catch (InvalidChannelException e) {
			logger.log(Level.SEVERE, "Tried to set non-existant channel!");
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
