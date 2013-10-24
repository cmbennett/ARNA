package edu.ycp.cs481.arna.client.uicontroller;

import edu.ycp.cs481.arna.shared.model.CompassMode;

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
	
	// Calculates the distance to the current destination Waypoint.
	public void updateDistance() {
		double x1 = compass.getUser().getLocation().getLatitude();
		double x2 = compass.getDestination().getLocation().getLatitude();
		double y1 = compass.getUser().getLocation().getLongitude();
		double y2 = compass.getDestination().getLocation().getLongitude();
		
		// Distance formula for two dimensions
		compass.setDistance(Math.sqrt((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))));
	}
	
	// Calculates the direction that points to the destination Waypoint (two dimensions only).
	public void updateDirection() {
		double x1 = compass.getUser().getLocation().getLatitude();
		double x2 = compass.getDestination().getLocation().getLatitude();
		double y1 = compass.getUser().getLocation().getLongitude();
		double y2 = compass.getDestination().getLocation().getLongitude();
		
		// Inverse tangent to calculate angle
		compass.setDirection((int) Math.atan((y2-y1)/(x2-x1)));
	}
}
