package edu.ycp.cs481.arna.client.controller;

import edu.ycp.cs481.arna.client.ui.CameraActivity;
import edu.ycp.cs481.arna.shared.model.Location;
import edu.ycp.cs481.arna.shared.model.Orientation;
import edu.ycp.cs481.arna.shared.model.TourMode;
import edu.ycp.cs481.arna.shared.model.User;

public class TourController {
	CameraActivity activity; 
	TourMode tour; 
	
	public TourController(){
		
	}
	
	public void setModel(TourMode t){
		this.tour = t; 
	}
	
	public void setActivity(CameraActivity a){
		this.activity = a; 
	}
	
	public void locationHandler(double lat, double lon, double elev){
		User u = tour.getUser();
		Location l = u.getLocation();
		
		l.setLatitude(lat); 
		l.setLongitude(lon); 
		l.setElevation(elev); 
		
	}
	
	public void orientationHandler(float az, float pitch, float roll){
		User u = tour.getUser();
		Orientation o = u.getOrient(); 
		
		o.setAzimuth(az); 
		o.setPitch(pitch); 
		o.setRoll(roll); 
	}
}
