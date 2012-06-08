package org.dss.mumbleIceConnector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Murmur.InvalidChannelException;
import Murmur.InvalidSecretException;
import Murmur.ServerBootedException;
import Murmur.ServerPrx;

public class LoLHandler extends ChannelHandler {

	public LoLHandler(ServerPrx serv, int id, Logger logger) {
		super(serv, id, logger);
	}

	@Override
	public void run() {
		String playername = chan.name.split("\\s+")[0];
		logger.log(Level.FINEST, "Playerchannel detected: " + playername);
		Player player = new Player(playername, logger);
		logger.log(Level.FINE, "LOLparsing started for: " + player.getName());
		chan.name = grabStats(player, MumbleIceConnector.advancedLol);
		String desc = MumbleIceConnector.advancedLol ? "Summoner ID: "
				+ player.getId() + " Have a nice Day!"
				: "Sorry, no Information here today.";
		chan.description = desc;
		try {
			serv.setChannelState(chan);
			logger.log(Level.FINER,
					"lolstats parsed for player " + player.getName());

		} catch (InvalidChannelException e) {
			logger.log(Level.SEVERE,
					"Channel could not be modified - InvalidChannelException");
		} catch (InvalidSecretException e) {
			logger.log(Level.SEVERE,
					"InvalidSecretException! check murmur ice config!");
		} catch (ServerBootedException e) {
			logger.log(Level.SEVERE,
					"ServerBootedException - check serverstatus!");
		}
	}

	/**
	 * parse playerstats from official database
	 * 
	 * @param player
	 */
	private void parseOfficial(Player player) {

		boolean foundRating = false;
		boolean foundWins = false;
		boolean foundLosses = false;
		boolean complete = false;
		boolean foundPlayername = false;
		boolean rightPlayer = false;
		Pattern p = Pattern.compile("\\d+");
		try {
			URL url = new URL(
					"http://competitive.euw.leagueoflegends.com/ladders/euw/current/rankedsolo5x5?summoner_name="
							+ URLEncoder.encode(player.getName(), "UTF-8"));
			logger.log(Level.FINEST,
					"URL for comp - stats: " + url.toExternalForm());
			InputStream is = url.openStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			String zeile = null;

			while ((zeile = in.readLine()) != null) {

				if (foundPlayername) {
					zeile = zeile.replace("</td>", "");
					zeile = zeile.trim();
					if (zeile.equals(player.getName())) {
						rightPlayer = true;
					}
					foundPlayername = false;
				}

				if (foundRating && rightPlayer) {
					Matcher m = p.matcher(zeile);
					if (m.find()) {
						player.setOfficialRating(Integer.parseInt(m.group()));
					}
					foundRating = false;
				}
				if (foundWins && rightPlayer) {
					Matcher m = p.matcher(zeile);
					if (m.find()) {
						player.setOfficialWins(Integer.parseInt(m.group()));
					}
					foundWins = false;
				}
				if (foundLosses && rightPlayer) {
					Matcher m = p.matcher(zeile);
					if (m.find()) {
						player.setOfficialLosses(Integer.parseInt(m.group()));
					}
					foundLosses = false;
				}

				if (!complete) {

					if (zeile
							.contains("<td class=\"views-field views-field-summoner-name-1\" >")) {
						foundPlayername = true;
					}

					if (zeile
							.contains("<td class=\"views-field views-field-rating views-align-center\" >")) {
						complete = true;
						foundRating = true;
					}
					if (zeile
							.contains("<td class=\"views-field views-field-wins views-align-center\" >")) {
						foundWins = true;
					}
					if (zeile
							.contains("<td class=\"views-field views-field-losses views-align-center\" >")) {
						foundLosses = true;
					}
				}

			}
			isr.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Could not read/write");
		}
	}

	private void parseLoLKing(Player player) {

		boolean parsedRanking = false;
		boolean parsedWins = false;
		boolean parsedLosses = false;

		player.retrieveId();
		if (player.getId() == null) {
			return;
		}
		String compareToWin = "<div style=\"font-size: 18px; color: #6C0; height: 28px;\">";
		String compareToLos = "<div style=\"font-size: 18px; color: #D20; height: 28px;\">";
		boolean foundSoloQ = false;
		try {
			URL url = new URL("http://www.lolking.net/summoner/euw/"
					+ URLEncoder.encode(player.getId(), "UTF-8"));
			InputStream is = url.openStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader in = new BufferedReader(isr);
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				if (foundSoloQ) {
					if (zeile.contains("<div style=\"font-size: 18px; color: ")) {
						zeile = zeile.trim();
						if (!parsedWins
								&& zeile.contains("<span style=\"color: white; font-size: 12px\"> Wins</span></div>")) {
							zeile = zeile.replace(compareToWin, "");
							zeile = zeile
									.replace(
											"<span style=\"color: white; font-size: 12px\"> Wins</span></div>",
											"");
							player.setLolKingWins(Integer.parseInt(zeile));
							parsedWins = true;
						}
						if (!parsedLosses
								&& zeile.contains("<span style=\"color: white; font-size: 12px\"> Losses</span></div>")) {
							zeile = zeile.replace(compareToLos, "");
							zeile = zeile
									.replace(
											"<span style=\"color: white; font-size: 12px\"> Losses</span></div>",
											"");
							player.setLolKingLosses(Integer.parseInt(zeile));
							return;
						}
					}
					if (!parsedRanking
							&& zeile.contains("<div style=\"color: rgb(255, 255, 255); margin-bottom: 10px; font-size: 26px; height: 41px;\">")) {

						zeile = zeile
								.replace(
										"<div style=\"color: rgb(255, 255, 255); margin-bottom: 10px; font-size: 26px; height: 41px;\">",
										"");
						zeile = zeile.replace("</div>", "");
						zeile = zeile.trim();
						if (zeile.contains("No Rating")) {
							player.setLolKingRating(0);
						} else {
							player.setLolKingRating(Integer.parseInt(zeile));
						}
						parsedRanking = true;
					}
				}
				if (zeile
						.contains("<div style=\"font-size: 20px; color: #FFCD03; margin-bottom: 10px; height: 31px;\">Solo/Duo Queue</div>")) {
					foundSoloQ = true;
				}
			}

		} catch (IOException e) {
		}
	}

	private String grabStats(Player player, boolean advanced) {
		parseOfficial(player);
		if (player.getOfficialRating() != 0 && advanced) {
			parseLoLKing(player);
			if (player.getId() != null) {
				int offgames = player.getOfficialWins()
						+ player.getOfficialLosses();
				int lolgames = player.getLolKingWins()
						+ player.getLolKingLosses();
				return offgames < lolgames ? player.lolKingString() : player
						.officialString();
			} else {
				return player.officialString();
			}
		} else if (player.getOfficialRating() == 0 && advanced) {
			parseLoLKing(player);
			int lolgames = player.getLolKingWins() + player.getLolKingLosses();
			if (lolgames > 0) {
				return player.lolKingString();
			} else {
				return player.officialString();
			}
		} else {
			return player.officialString();
		}
	}
}
