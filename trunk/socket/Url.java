package socket;

import java.io.BufferedReader;
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader;
import java.net.URL; 
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Url { 
    static Pattern p = Pattern.compile("\\d+");
    static Player[] players;
    static boolean debug = false;
    static int pagemode = 0;
    
    public static void main( String[] args ) {
        
        createPlayers(parseParas(args));
        startParsing(players);
        printStats();
    }
    
    /**
     * prüfe auf kommandozeilenoptionen.
     * bisher nicht implementiert.
     * nicht-options-strings sind spielernamen.
     */
    private static LinkedList<String> parseParas(String[] args) {
        int i = 0;
        LinkedList<String> playernames = new LinkedList<String>();
        
        if (args.length < 1) {
            System.exit(-1);
        }
        
        for(String s : args) {
            if (s.equalsIgnoreCase("-d")) {
                debug = true;
                i++;
            } else if (s.equalsIgnoreCase("-o")) {
                pagemode = 0;
                i++;
            } else if (s.equalsIgnoreCase("-no")) {
                pagemode = 1;
                i++;
            } else if (s.equalsIgnoreCase("-ono")) {
                pagemode = 2;
                i++;
            } else {
                playernames.add(s);
            }
        }
        return playernames;
    }
    
    private static void startParsing(Player[] players) {
        if (pagemode == 0) {
            for(Player p : players) {
                parseOfficialStats(p);
            }
        }
    }
    
    private static void printStats() {
        if (pagemode == 0) {
            for(Player p : players) {
                System.out.println(p.officialString());;
            }
        }
    }

    private static void createPlayers(LinkedList<String> playerNames) {
        players = new Player[playerNames.size()];
        
        for (int i = 0; i < playerNames.size(); i++) {
            players[i] = new Player(playerNames.get(i));
        }
    } 

    private static void parseOfficialStats(Player player) {

        boolean foundRating = false;
        boolean foundRank = false;
        boolean foundWins = false;
        boolean foundLosses = false;
        boolean complete = false;

        String rank = "";

        try {
            URL url = new URL("http://competitive.euw.leagueoflegends.com/ladders/euw/current/rankedsolo5x5?summoner_name=" + URLEncoder.encode(player.getName(), "UTF-8"));
            InputStream is = url.openStream();
            InputStreamReader isr = new  InputStreamReader(is);
            BufferedReader in = new BufferedReader(isr);
            String zeile = null;
            

            while ((zeile = in.readLine()) != null) {

                if (foundRating) {
                    Matcher m = p.matcher(zeile);
                    if (m.find()) {
                        player.setOfficialRating(Integer.parseInt(m.group()));
                    }
                    foundRating = false;
                }
                if (foundWins) {
                    Matcher m = p.matcher(zeile);
                    if (m.find()) {
                        player.setOfficialWins(Integer.parseInt(m.group()));
                    }
                    foundWins = false;
                }
                if (foundLosses) {
                    Matcher m = p.matcher(zeile);
                    if (m.find()) {
                        player.setOfficialLosses(Integer.parseInt(m.group()));
                    }
                    foundLosses = false;
                }
                if (foundRank) {
                    Matcher m = p.matcher(zeile);
                    while (m.find()) {
                        rank += m.group();
                    }
                    player.setOfficialRank(Integer.parseInt(rank));
                    foundRank = false;
                }

                if (!complete) {
                    if (zeile.contains("<td class=\"views-field views-field-rating views-align-center\" >")) {
                        complete = true;
                        foundRating = true;
                    }
                    if (zeile.contains("<td class=\"views-field views-field-rank views-align-center\" >")) {
                        foundRank = true;
                    }
                    if (zeile.contains("<td class=\"views-field views-field-wins views-align-center\" >")) {
                        foundWins = true;
                    }
                    if (zeile.contains("<td class=\"views-field views-field-losses views-align-center\" >")) {
                        foundLosses = true;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}