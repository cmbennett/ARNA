package edu.ycp.cs481.arna.shared.model;

import java.util.ArrayList;
import java.util.List;

public class CompassMode {
	
	private POI destination;
	private double direction;
	private double distance;
	private User user;
	private ArrayList<Integer> queue;
	private int sum, limit;
	private List<POI> wpList;
	
	public CompassMode(User u, POI w) {
		user = u;
		destination = w;
		direction = 0;
		sum = 0;
		limit = 10;
		queue = new ArrayList<Integer>();
		wpList = new ArrayList<POI>(); 
	}

	public void addWaypoint(POI w) {
		wpList.add(w); 
	}
	
	public List<POI> getWpList() {
		return wpList;
	}

	public void setWpList(List<POI> wpList) {
		this.wpList = wpList;
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
	
	public void setDirection(double dir) {
		direction = dir; 
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int l) {
		limit = l;
	}
	
	public double computeAverage() {
		sum = 0; // Reset sum
		
		for(int i = 0; i < limit; i++) {
			sum += queue.get(i);
		}
		
		return (double)sum/(double)limit;
	}
	
	public void addValue(int value) {
		queue.add(value);
	}
	
	public void removeValue() {
		queue.remove(0);
	}
}
