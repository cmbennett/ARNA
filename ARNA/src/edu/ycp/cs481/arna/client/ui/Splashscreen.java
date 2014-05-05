package edu.ycp.cs481.arna.client.ui;

import java.util.ArrayList;

import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.TourMode;
import edu.ycp.cs481.shared.persistence.POIDataSource;
import edu.ycp.cs481.shared.persistence.addingWaypoints;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;

public class Splashscreen extends Activity {
	LocationManager locationManager;
	TourMode tour; 
	TourController cont; 
	int count;
	boolean found;
	ImageView image;

	static int width;
	static int height;
	
	addingWaypoints waypoints;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);	


		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); 
		
		POISingleton.getInstance();
		POISingleton.setDataSource(this);		
		
		
		
		tour = new TourMode(POISingleton.getDataSource()); 
		
		POISingleton.setTourMode(tour);	
	
		
		cont = new TourController(tour); 
		POISingleton.setTourCont(cont);	
		count = 0;
		found = false; 
		
		if (POISingleton.getPOIS(POISingleton.getDataSource()).size() == 0)
		{
			
			waypoints = new addingWaypoints(cont.getModel());
			ArrayList<POI> pois = waypoints.getPOI();	
			for (int i = 0; i < pois.size(); i++)
			{
				 // this adds the points to the database
				POISingleton.getDataSource().addPOI(new POI( pois.get(i).getName(),pois.get(i).getDescription(), pois.get(i).getLocation().getLatitude(),
						 pois.get(i).getLocation().getLongitude(), pois.get(i).getLocation().getElevation()));
			tour.addWaypoint(pois.get(i));
		
			}
		}
		

	}

	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location){
			double latitude = location.getLatitude(); 
			double longitude = location.getLongitude(); 
			double altitude = location.getAltitude();

			count++;

			if(latitude > 0 && count == 5 && found ==  false)
			{
				cont.updateLocation(latitude, longitude, altitude); 
				Intent intent = new Intent(Splashscreen.this, MainMenu.class);  
				startActivity(intent);
				count = 0;
				found = true;
			}
			if(count > 5)
			{
				count = 0;
			}
		}

		public void onProviderDisabled(String arg0){
			//TODO Auto-generated method sub
		}

		public void onProviderEnabled(String arg0){
			//TODO Auto-generated method sub
		}
		public void onStatusChanged(String arg0, int arg1, Bundle arg2){
			//TODO Auto-generated method sub
		}
	}; 

}