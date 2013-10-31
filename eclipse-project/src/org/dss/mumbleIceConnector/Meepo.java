package org.dss.mumbleIceConnector;

import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * 
 * @author atomy
 * 
 */
public class Meepo {
	/**
	 * 
	 */
	private Player player;
	
	/**
	 * 
	 */
	private JSONObject result;

	/**
	 * 
	 * @param player
	 */
	public Meepo(Player player) {
		this.player = player;
		parse();
	}

	/**
	 * 
	 * @return
	 */
	public String getChannelName() 
	{
		String out = this.player.getName() + " ERR";
		
		if (this.player.getRank() == null || this.player.getRank().isEmpty()) {
			return out;
		}			
		
		if (this.player.getMiniSeries() != null && !this.player.getMiniSeries().isEmpty()) {
			out = String.format("%s [%s|%s] !SERIES!: %s LP: %d W/L: %d/%d", this.player.getName(), this.player.getTier(), this.player.getRank(), 
					this.player.getMiniSeries(), this.player.getLeaguePoints(), this.player.getWins(), this.player.getLosses());
		} else {
			out = String.format("%s [%s|%s] LP: %d W/L: %d/%d", this.player.getName(), this.player.getTier(), this.player.getRank(), 
					this.player.getLeaguePoints(), this.player.getWins(), this.player.getLosses());
		}	

		return out;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getChannelDescription() 
	{
		String out = "ERR";
		
		if (this.player.getRank() == null || this.player.getRank().isEmpty()) {
			return out;
		}
		
		return this.result.toString();
	}	
	
	/**
	 * 
	 * @return
	 */
	protected void parse()
	{
		this.result = getJson();

		parseJson();	
	}
	
	/**
	 * 
	 * @return
	 */
	protected void parseJson()
	{	
		if (this.result == null) {
			return;
		}
		
		JSONObject jsonPlayer, data;
		JSONArray summonerLeagues;
		
		try {
			jsonPlayer = this.result.getJSONObject("player");
			String playerName = jsonPlayer.getString("name");
			
			if (!this.player.getName().equalsIgnoreCase(playerName)) {
				throw new Exception("error, result is a different player");
			}
			
			data = this.result.getJSONObject("data");
			player.setTier(data.getString("tier"));
			player.setWins(data.getInt("wins"));
			player.setLosses(data.getInt("losses"));
			player.setRank(data.getString("rank"));
			
			if (!data.isNull("miniSeries")) {
				JSONObject miniSeries = data.getJSONObject("miniSeries");
				String progress = miniSeries.getString("progress");
				player.setMiniSeries(progress);
			}
			
			player.setLeaguePoints(data.getInt("leaguePoints"));
			player.setDemotionWarning(data.getInt("demotionWarning"));
			player.setQueueType(data.getString("queueType"));
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	protected JSONObject getJson()
	{
		try {
			String url = MumbleIceConnector.meepoUrl + MumbleIceConnector.lolRegion + "/" + this.player.getName() + "/leagues";
			
			HttpResponse<JsonNode> response = Unirest
					.get(url)
					.header("X-Mashape-Authorization", MumbleIceConnector.apiKey)
					.header("X-Options", "SingleEntity").asJson();
			
			MumbleIceConnector.logApiRequest(url);
			
			JsonNode n = response.getBody();
			int code = response.getCode();			
			JSONObject o = n.getObject();
			
			if (code != 200) {
				throw new Exception("Invalid return code! (" + code + ")");
			}
			
			if (o.getBoolean("success") == true) {
				return o;
			}
			
			System.out.println("code: " + code);
			System.out.println("success: " + o.getString("success"));
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
			//e.printStackTrace();			
		}
		
		return null;
	}
}
