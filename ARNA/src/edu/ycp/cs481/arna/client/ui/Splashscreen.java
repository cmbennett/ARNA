package edu.ycp.cs481.arna.client.ui;

import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.TourMode;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);


		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); 

		tour = new TourMode(); 
		cont = new TourController(tour); 
		count = 0;
		found = false;

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