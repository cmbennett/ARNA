package edu.ycp.cs481.arna.shared.model;

import java.util.List;

public class Mode {
	protected User user; 
	protected List<POI> wpList;
	
	public Mode(User u, List<POI> wpList){
		this.setUser(u); 
		this.wpList = wpList; 
	}

	public List<POI> getWpList() {
		return wpList;
	}

	public void setWpList(List<POI> wpList) {
		this.wpList = wpList;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void addWaypoint(POI w) {
		wpList.add(w); 
	}
}
