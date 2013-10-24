package edu.ycp.cs481.arna.client.uicontroller;

import edu.ycp.cs481.arna.shared.model.TourMode;
import edu.ycp.cs481.arna.shared.model.User;

public class TourController {

	private TourMode tour; 
	
	public TourController(TourMode t){
		this.tour = t; 
	}
	
	public TourController() {
		tour = new TourMode(); 
	}

	public void setModel(TourMode t){
		this.tour = t; 
	}
	
	public TourMode getModel(){
		return tour; 
	}
	
	public void updateLocation(double lat, double lon, double elev){
		User u = tour.getUser();
		u.setLocation(lat, lon, elev);
	}
	
	public void updateOrientation(double azimuth, double pitch, double roll){
		User u = tour.getUser();
		u.setOrient(azimuth, pitch, roll); 
	}
}
