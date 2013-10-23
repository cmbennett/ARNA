package edu.ycp.cs481.arna.shared.model;

public class Waypoint {
	
	private Location loc;
	private String description;
	
	public Waypoint(double x, double y, double z) {
		loc = new Location(x, y, z);
		description = "No Description.";
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void setLocation(double x, double y, double z) {
		loc = new Location(x, y, z);
	}
	
}
