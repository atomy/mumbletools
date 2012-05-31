package org.dss.mumbleIceConnector;

import java.util.logging.Logger;

public class Location {
	
	private String plz;
	private int temp;
	private String comment;
	private String locationName;
	private String flavour;
	
	
    

    public Location(String postalcode, Logger logger) {
		plz = postalcode;
		temp = -9000;
		comment = "";
		locationName = "";
		flavour = "";
	}
	
	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName.replace("&#252;", "\u00FC").replace("&#228;", "\u00E4").replace("&#246;", "\u00F6");
	}
	
	public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPlz() {
		return plz;
	}
	
	public String toString() {
	    if (temp != -9000) { 
	        return getPlz()+ " - " + getLocationName()+ ": " + getTemp() + "\u00B0C - " + getComment();
	    } else {
	        return getPlz()+ " - sorry, the weather-control station broke";
	    }
	}

}
