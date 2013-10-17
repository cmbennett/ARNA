package edu.ycp.cs481.arna.shared.model;

public class User {
	private Location loc; 
	private Orientation orient; 
	
	public User(Location l, Orientation o){
		this.loc = l; 
		this.orient = o; 
	}
	
	public void updateLoc(float x, float y, float z){
		loc.setX(x); 
		loc.setX(y); 
		loc.setZ(z);
	}
	
	public void updateOrient(float az, float p, float r){
		orient.setAzimuth(az); 
		orient.setPitch(p); 
		orient.setRoll(r); 
	}
	
	public void setOrient(Orientation o){
		this.orient = o; 
	}
	
	public void setLocation(Location l){
		this.loc = l; 
	}
	
	public Location getLocation(){
		return loc; 
	}
	
	public Orientation getOrient(){
		return orient; 
	}
}
