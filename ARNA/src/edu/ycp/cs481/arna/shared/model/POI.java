package edu.ycp.cs481.arna.shared.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class POI {
	private long id;
	private Location loc;
	private String description, name;
	private Vector displacement;
	private List<Float> buffer;

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
		buffer = new LinkedList<Float>();
		
		// Populate buffer with nonzero values.
		for(int i = 0; i < 10; i++) {
			buffer.add(0.0f);
		}
	}

	public POI() {
		// TODO Auto-generated constructor stub
	}

	public void setID(long insertId) {
		this.id = insertId;
	}

	public long getID() {
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
	
	public void addBufferValue(float value) {
		buffer.remove(0);
		buffer.add(value);
	}
	
	public float getRollingAverage() {
		Iterator<Float> i = buffer.iterator();
		
		float sum = 0.0f;
		int count = 0;
		
		while(i.hasNext()) {
			sum += i.next();
			count++;
		}
		
		return sum /= count;
	}
}
