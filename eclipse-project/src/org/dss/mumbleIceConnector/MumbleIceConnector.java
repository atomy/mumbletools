package org.dss.mumbleIceConnector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class MumbleIceConnector {

	private static final Object apiCountLock = new Object();

	// milliseconds - 1 hour is 3600000
	final static int sleeptimer = 1000 * 60 * 15; // 1000ms, 60s, 15m

	// lolstats yes no
	static final boolean lol = true;

	// name of lolstattopchannel
	static final String lolTopchannel = "--- LoL Ranking (EUW) ---";

	// more lol-parsing for more accurate information. unexpected results
	// possible.
	static final boolean advancedLol = true;

	// weather yes no
	static final boolean weather = true;

	// name of weathertopchannel
	static final String weatherTopchannel = "--- Wetterkontrollstation ---";

	// port
	static final String port = "6502";

	// ip
	static final String ip = "127.0.0.1";

	static String apiKey = "";

	static final String lolRegion = "euw";

	static final String meepoUrl = "https://teemojson.p.mashape.com/player/";

	static final int maxLolCannels = 12;

	static final int apiMaxRequests = 1200;

	public static Logger logger;

	public static void main(String[] args) {
		try {
			MumbleIceConnector.apiKey = retrieveApiKey();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		logger = ConnectorUtil.createLogger();

		ConnectionHandler connectionHandler = new ConnectionHandler(logger);
		Thread t = new Thread(connectionHandler);
		t.start();
	}

	/**
	 * 
	 * @param api
	 */
	public static void logApiRequest(String msg) {
		synchronized (apiCountLock) {
			int count = 0;

			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
			Date date = new Date();

			String filename = String.format("apilimit.%s.txt",
					dateFormat.format(date));
			MumbleIceConnector.touch(new File(filename));

			try {
				String in = FileUtils.readFileToString(new File(filename));

				if (in.length() > 0) {
					count = Integer.parseInt(in);
				}

				count++;
				FileUtils.writeStringToFile(new File(filename),
						String.format("%d", count));
			} catch (IOException e) {
				e.printStackTrace();
			}

			MumbleIceConnector.logger.log(Level.WARNING, "API-REQUEST: " + msg);
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String retrieveApiKey() throws Exception {
		String filename = "apiKey.txt";
		File file = new File(filename);

		if (!file.exists()) {
			throw new Exception(
					"apikey not found! place a file named \"apiKey.txt\" next to me with the apiKey in it!");
		}

		String in = FileUtils.readFileToString(file);

		if (in.length() != 32) {
			throw new Exception("apikey should be 32 chars, it is not!");
		}

		return in;
	}

	/**
	 * 
	 * @return
	 */
	public static boolean checkApiLimit() {
		synchronized (apiCountLock) {
			int count = 0;

			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
			Date date = new Date();

			String filename = String.format("apilimit.%s.txt",
					dateFormat.format(date));
			MumbleIceConnector.touch(new File(filename));

			try {
				String in = FileUtils.readFileToString(new File(filename));

				if (in.length() > 0) {
					count = Integer.parseInt(in);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (count > MumbleIceConnector.apiMaxRequests) {
				return false;
			}

			return true;
		}
	}

	/**
	 * 
	 * @param file
	 * @param timestamp
	 */
	public static void touch(File file) {
		long unixTime = System.currentTimeMillis() / 1000L;

		try {
			if (!file.exists())
				new FileOutputStream(file).close();
			file.setLastModified(unixTime);
		} catch (IOException e) {
		}
	}
}
