package edu.ycp.cs481.arna.shared.model;

import java.util.ArrayList;
import java.util.List;


public class TourMode extends Mode {
	
	List<Waypoint> onScreen; 
	
	public TourMode(User u, List<Waypoint> wpList){
		super(u, wpList); 
		onScreen = new ArrayList<Waypoint>(); 
	}
	
	public TourMode() {
		super(new User(), new ArrayList<Waypoint>()); 
		onScreen = new ArrayList<Waypoint>(); 
	}

	public List<Waypoint> getOnScreen() {
		return onScreen;
	}

	public void setOnScreen(List<Waypoint> onScreen) {
		this.onScreen = onScreen;
	}
	
	public void addWaypoint(Waypoint w){
		onScreen.add(w); 
	}
	
	public void removeWaypoint(Waypoint w){
		onScreen.remove(w); 
	}
	
}
