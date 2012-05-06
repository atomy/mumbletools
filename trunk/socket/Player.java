package socket;

public class Player {
    
    private String name;
    
    public int getOfficialRating() {
        return officialRating;
    }

    public void setOfficialRating(int officialRating) {
        this.officialRating = officialRating;
    }

    public int getOfficialRank() {
        return officialRank;
    }

    public void setOfficialRank(int officialRank) {
        this.officialRank = officialRank;
    }

    public int getOfficialWins() {
        return officialWins;
    }

    public void setOfficialWins(int officialWins) {
        this.officialWins = officialWins;
    }

    public int getOfficialLosses() {
        return officialLosses;
    }

    public void setOfficialLosses(int officialLosses) {
        this.officialLosses = officialLosses;
    }

    public int getLolKingRating() {
        return lolKingRating;
    }

    public void setLolKingRating(int lolKingRating) {
        this.lolKingRating = lolKingRating;
    }

    public int getLolKingRank() {
        return lolKingRank;
    }

    public void setLolKingRank(int lolKingRank) {
        this.lolKingRank = lolKingRank;
    }

    public int getLolKingWins() {
        return lolKingWins;
    }

    public void setLolKingWins(int lolKingWins) {
        this.lolKingWins = lolKingWins;
    }

    public int getLolKingLosses() {
        return lolKingLosses;
    }

    public void setLolKingLosses(int lolKingLosses) {
        this.lolKingLosses = lolKingLosses;
    }

    public String getName() {
        return name;
    }

    private int officialRating;
    private int officialRank;
    private int officialWins;
    private int officialLosses;
    private int lolKingRating;
    private int lolKingRank;
    private int lolKingWins;
    private int lolKingLosses;
    
    public Player(String name) {
        this.name = name;
    }
    
    public String officialString() {
        return name + ": " + officialRating + " "+ officialWins + " " + officialLosses + " " + officialRank;
    }
    
    public String lolKingString() {
        return name + ": " + lolKingRating + " "+ lolKingWins + " " + lolKingLosses + " " + lolKingRank;
    }
    
    public String toString(int i) {
        return null;
    }
}
