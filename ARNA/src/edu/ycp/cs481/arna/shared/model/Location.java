package edu.ycp.cs481.arna.shared.model;

public class Location {
	
	private double latitude, longitude, elevation;
	
	public Location(double x, double y, double z) {
		latitude = x;
		longitude = y;
		elevation = z;
	}

	public Location() {
		latitude = 0; 
		longitude = 0; 
		elevation = 0; 
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double x) {
		latitude = x;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double y) {
		longitude = y;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double z) {
		elevation = z;
	}
	
	// Compares this Location object to another given Location object.
	public int compareTo(Location loc) {
		if(latitude == loc.getLatitude() && longitude == loc.getLongitude() && elevation == loc.getElevation()) {
			return 1;
		} else {
			return -1;
		}
	}
}
