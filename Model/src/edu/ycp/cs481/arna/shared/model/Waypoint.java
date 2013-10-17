package edu.ycp.cs481.arna.shared.model;

public class Waypoint {
	
	private Location loc;
	private String description;
	private double distanceFromUser; 
	
	public Waypoint() {
		loc = new Location(-1,-1,-1);
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
	
	public void setLocation(int x, int y, int z) {
		loc = new Location(x, y, z);
	}
	
	public void setDistance(double d){
		distanceFromUser = d; 
	}
	
	public double getDistance(){
		return distanceFromUser; 
	}
	
	public void updateDistanceFromUser(User u){
		//TODO: Haversine formula
	}
}
