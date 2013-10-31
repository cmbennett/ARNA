package edu.ycp.cs481.arna.shared.model;

public class CompassMode {
	
	private Waypoint destination;
	private int direction;
	private double distance;
	private User user;
	
	public CompassMode(User u, Waypoint w) {
		 user = u;
		 destination = w;
		 direction = 0;
	}
	
	public Waypoint getDestination() {
		return destination;
	}
	
	public void setDestination(Waypoint w) {
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
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int i) {
		direction = i;
	}
}
