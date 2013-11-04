package edu.ycp.cs481.arna.client.uicontroller;

import edu.ycp.cs481.arna.shared.model.CompassMode;
import edu.ycp.cs481.arna.shared.model.User;

public class CompassController {
	
	private CompassMode compass;
	
	public CompassController(CompassMode c) {
		compass = c;
	}
	
	public void setModel(CompassMode c){
		compass = c; 
	}
	
	public CompassMode getModel(){
		return compass; 
	}
	
	public void updateLocation(double lat, double lon, double elev){
		User u = compass.getUser();
		u.setLocation(lat, lon, elev);
	}
	
	//Need method to update the destination
	public void updateDestination(){
		
	}
	
	
	public void updateOrientation(double azimuth, double pitch, double roll){
		User u = compass.getUser();
		u.setOrient(azimuth, pitch, roll); 
	}
	
	// Calculates the distance to the current destination Waypoint.
	public void updateDistance() {
		double distance = compass.getUser().getDistanceTo(compass.getDestination());
		compass.setDistance(distance); 
	}
	
	// Calculates the direction that points to the destination Waypoint (two dimensions only).
	public void updateDirection() {
		double direction = compass.getUser().getBearingTo(compass.getDestination());
		compass.setDirection(direction); 
	}
}
