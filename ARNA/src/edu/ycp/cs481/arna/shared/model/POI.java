package edu.ycp.cs481.arna.shared.model;

public class POI {
	private int id;
	private Location loc;
	private String description, name;
	private Vector displacement; 

	public POI(int id, String name, String desc, double x, double y, double z) {
		this.id = id;
		this.name = name;
		this.description = desc;
		loc = new Location(x, y, z);
		displacement = new Vector(); 
	}

	public POI(double x, double y, double z) {
		loc = new Location(x, y, z);
		description = "No Description.";
		name = "No name.";
		displacement = new Vector(); 
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return loc;
	}

	public void setLocation(double x, double y, double z) {
		loc = new Location(x, y, z);
	}

	public void calculateVector(User user){
		double y = user.getLocation().getElevation() - loc.getElevation(); 
	}

	public void setVector(float x, float y, float z){
		displacement.set(x, y, z); 
	}

	public Vector getVector(){
		return displacement;
	}
}
