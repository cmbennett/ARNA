package edu.ycp.cs481.arna.shared.model;

import java.util.ArrayList;
import java.util.List;


public class TourMode extends Mode {
	
	private List<POI> onScreen;
	private static double CUTOFF = 9999999; //400
	
	public TourMode(User u, List<POI> wpList) {
		super(u, wpList); 
		onScreen = new ArrayList<POI>(); 
	}
	
	public TourMode() {
		super(new User(), new ArrayList<POI>()); 
		onScreen = new ArrayList<POI>(); 
	}

	public List<POI> getOnScreen() {
		return onScreen;
	}

	public void setOnScreen(List<POI> onScreen) {
		this.onScreen = onScreen;
	}
	
	public void addWaypoint(POI w) {
		wpList.add(w);
	}
	
	public void removeWaypoint(POI w) {
		onScreen.remove(w); 
	}
	
	public void populateOnScreen(double cameraAngle) {
		onScreen.clear();
		
		/*if(onScreen.isEmpty()){
			System.out.println("SUCCESS"); 
		}*/
		
		double halfAngle = cameraAngle/2; 
		for(POI w: wpList){
			double distance = user.getDistanceTo(w); 
			
			if(distance < CUTOFF){				
				double bearing = user.getBearingTo(w); 
				if(bearing < user.getOrient().getAzimuth() + halfAngle && bearing > user.getOrient().getAzimuth() - halfAngle ) {
					onScreen.add(w); 					
				}
			}
		}
	}
	
	public void computePOIVector(double horzCamAngle, double vertCamAngle, double maxX, double maxY) {
		
		double dy; 
		double dx; 
		double dz; 
		
		double roll = user.getOrient().getRoll()-90; 
		
		//System.out.println(roll + " roll"); 
		//System.out.println(vertCamAngle); 
		
		//Populate array of vectors (each i vector in the list corresponds with the i POI in the onScreen list)
		for(POI w: onScreen) {
			
			//Use arc lengths and to compute a ratio of distance from azimuth to
			//total distance at cutoff to map the coordinates to the screen
			
			//Map X using arc lengths
			double twoPiR = 2 * Math.PI * user.getDistanceTo(w); 
		//	System.out.println(user.getDistanceTo(w) +  " USER"); 
			double totalArc = twoPiR * (horzCamAngle / 360); 
			
			double azimuth = user.getOrient().getAzimuth(); 
			double bearing = user.getBearingTo(w); 
			double POIAngle = Math.abs(azimuth - bearing); 
			System.out.println(POIAngle +  " POI"); 
			double POIArc = twoPiR * (POIAngle / 360); 
			
			//(poiarc / totalarc) = (dx / DX)
			dx = (POIArc * maxX / totalArc); 		
			
			if(azimuth - bearing > 0){
				dx = dx * -1; 
			}	
			dx = (maxX/2) + dx;
			
			//Map Y using trigonometry
			double POIheight = w.getLocation().getElevation() - user.getLocation().getElevation(); 
			double YtotalArc = twoPiR * (vertCamAngle / 360);
			double yBearing = Math.toDegrees(Math.atan(Math.abs(POIheight)/user.getDistanceTo(w)));
			double POIYAngle = Math.abs(roll - yBearing);
			double POIYArc = twoPiR * (Math.abs(POIYAngle) / 360);
			dy = (POIYArc * maxY) / YtotalArc; 
			
			if(roll - yBearing > 0){
				dy = dy * -1; 
			}
			
			dy = (maxY/2) + dy;
			dz = 0.0; 
			w.setVector((float) dx, (float) dy, (float) dz); 
			
			System.out.println("dx: " + dx); 
			//System.out.println("dy: " + dy); 
		}
	}
}
