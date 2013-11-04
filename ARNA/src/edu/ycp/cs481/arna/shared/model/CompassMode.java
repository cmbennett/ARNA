package edu.ycp.cs481.arna.shared.model;

public class CompassMode {
	
	private POI destination;
	private double direction;
	private double distance;
	private User user;
	
	public CompassMode(User u, POI w) {
		 user = u;
		 destination = w;
		 direction = 0;
	}
	
	
	public POI getDestination() {
		return destination;
	}
	
	public void setDestination(POI w) {
		destination = w;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User u) {
		user = u;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double d) {
		distance = d;
	}
	
	public double getDirection() {
		return direction;
	}
	
	public void setDirection(double drctn) {
		direction = drctn; 
	}
}
