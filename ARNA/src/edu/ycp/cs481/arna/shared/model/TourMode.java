package edu.ycp.cs481.arna.shared.model;

import java.util.ArrayList;
import java.util.List;


public class TourMode extends Mode {
	
	private List<POI> onScreen;
	private static double CUTOFF = 0.5;
	private List<Vector> displacementVectors; 
	
	public TourMode(User u, List<POI> wpList){
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
	
	public void addWaypoint(POI w){
		onScreen.add(w); 
	}
	
	public void removeWaypoint(POI w){
		onScreen.remove(w); 
	}
	
	public void populateOnScreen(double cameraAngle){
		
		double halfAngle = cameraAngle/2; 
		for(POI w: wpList){
			double distance = user.getDistanceTo(w); 
			
			if(distance < CUTOFF){
				
				double bearing = user.getBearingTo(w); 
				
				if(bearing < user.getOrient().getAzimuth() + halfAngle || bearing > user.getOrient().getAzimuth() - halfAngle ){
					onScreen.add(w); 
				}
			}
			
		}
		
	}
	
	public void computeDisplacementVectors(){
		double temp1; 
		double temp2; 
		double dy; 
		double dBearing; 
		double dx; 
		double dz; 
		
		double pitch = user.getOrient().getPitch(); 
		double roll = user.getOrient().getRoll(); 
		
		//Populate array of vectors (each i vector in the list corresponds with the i POI in the onScreen list)
		for(POI w: onScreen){
			dy = user.getLocation().getElevation() - w.getLocation().getElevation(); 
			
			dBearing = user.getOrient().getAzimuth() - user.getBearingTo(w); 
			dx = Math.sin(dBearing) * user.getDistanceTo(w); 
			dz = Math.cos(dBearing) * user.getDistanceTo(w); 
			
			//Compensate for rotation of camera (NEEDS TESTED!!!)
			//Refactored equation thanks to aldream.net
			temp1 = Math.cos(pitch) * dy - Math.sin(pitch) * dx; 
			temp2 = Math.cos(roll) * dz + Math.sin(roll) * (Math.sin(pitch) * dy + Math.cos(pitch) * dz); 
			
			dx = Math.cos(roll) * (Math.sin(pitch) * dy + Math.cos(pitch) * dx) - Math.sin(roll) * dz; 
			dy = Math.sin(pitch) * temp2 + Math.cos(pitch) * temp1; 
			dz = Math.cos(pitch) * temp2 - Math.sin(pitch) * temp1; 
			Vector v = new Vector();
			v.set((float)dx,(float)dy,(float)dz);
			displacementVectors.add(v); 
		}
		
		
	}
}
