package edu.ycp.cs481.arna.shared.model;

import java.util.List;

public class Mode {
	private User user; 
	private List<Waypoint> wpList;
	
	public Mode(User u, List<Waypoint> wpList){
		this.setUser(u); 
		this.wpList = wpList; 
	}

	public List<Waypoint> getWpList() {
		return wpList;
	}

	public void setWpList(List<Waypoint> wpList) {
		this.wpList = wpList;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void addWaypoint(Waypoint w) {
		wpList.add(w); 
	}
}
