package edu.ycp.cs481.arna.shared.model;

public class POI {
	private Location loc;
	private String description, name;
	
	public POI(double x, double y, double z) {
		loc = new Location(x, y, z);
		description = "No Description.";
		name = "No name.";
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void setLocation(double x, double y, double z) {
		loc = new Location(x, y, z);
	}
	
	public void calculateVector(User u){
		double y = u.getLocation().getElevation() - loc.getElevation(); 
	}
}
