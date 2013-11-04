package edu.ycp.cs481.arna.client.ui;


import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.TourMode;
import android.location.LocationManager;
import android.location.LocationListener; 
import android.location.Location; 
import android.os.Bundle;
import android.app.Activity; 
import android.content.Context;
import android.content.Intent; 
import android.hardware.Sensor; 
import android.hardware.SensorManager;
import android.hardware.SensorEventListener; 
import android.hardware.SensorEvent;  
import android.util.Log; 
import android.view.Display;

import com.google.android.maps.MapView;
import com.google.android.maps.MapController;


public class CompassModeView extends MapActivity  {

	final static String TAG = "test"; 
	SensorManager sensorManager;
	Sensor accelerometer; 
	private MapView mapView;
	private MapController mapController;
	int accelerometerSensor; 
	
	LocationManager locationManager; 
	int magnetometerSensor;
	
	TourMode tour; 
	TourController cont; 
	
    float headingAngle;
    float pitchAngle;
    float rollAngle;
    
    float roll;
    float pitch;
    float azimuth;
    boolean started;
    int count;
    private static final float ALPHA = 0.25f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass_mode_view);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener); 
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
		
		magnetometerSensor = Sensor.TYPE_MAGNETIC_FIELD; 
		accelerometerSensor = Sensor.TYPE_ACCELEROMETER; 
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(magnetometerSensor), SensorManager.SENSOR_DELAY_FASTEST); 
		sensorManager.registerListener(sensorEventListener,  sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_FASTEST); 

		count = 0;
	
		tour = new TourMode(); 
		cont = new TourController(tour); 
	 
		 mapView = (MapView) findViewById(R.id.map);
		  
		  // enable Street view by default
		  mapView.setStreetView(true);
		  
		  // enable to show Satellite view
		  // mapView.setSatellite(true);
		  
		  // enable to show Traffic on map
		  // mapView.setTraffic(true);
		  
		  mapView.setBuiltInZoomControls(true);
		  
		  mapController = mapView.getController();
		  mapController.setZoom(16); 
	    
		
	}

	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location){
			double latitude = location.getLatitude(); 
			double longitude = location.getLongitude(); 
			double altitude = location.getAltitude(); 
			
			cont.updateLocation(latitude, longitude, altitude); 

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

	final SensorEventListener sensorEventListener = new SensorEventListener(){
		float[] gravity; 
		float[] geomagnetic; 
		public void onSensorChanged(SensorEvent sensorEvent){
			double azimuth = sensorEvent.values[0];
		
			float R[] = new float[9]; 
			float I[] = new float[9]; 
			float orientation[] = new float[3];
	
			
			if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
				gravity = lowPass(sensorEvent.values.clone(), gravity);
			}
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				geomagnetic = lowPass(sensorEvent.values.clone(), geomagnetic);
			}
			if(gravity != null && geomagnetic != null){
			 
				 SensorManager.getRotationMatrix( R, I, gravity, geomagnetic);
				 SensorManager.getOrientation(R, orientation); 
				int orientations =  getResources().getConfiguration().orientation;
				// 1 is portrait
				// 2 is landscape
				 				  
					 azimuth = (float) Math.toDegrees(orientation[0]);
					if ( azimuth < 0.0f)
					{
						azimuth += 360.0f; 
					}
					 pitch = (float) Math.toDegrees(orientation[1]); // -180 to 180
					if ( pitch < 180.0f)
					{
						pitch -= 180.0f; 
					}
					
					 roll  = (float) Math.toDegrees(orientation[2]); // -90 to 90
					if (roll > -90.f)
					{
						roll +=  90.0f;
					}
					if (roll > 90.0f)
					{
						roll -= 90.f;
					} 
                       cont.updateOrientation(azimuth, pitch, roll); 
                       count++;
				
			}	
			
			if(roll < 120 && count > 10)
             {
          	   Intent intent = new Intent(CompassModeView.this, TourModeView.class);  
          	   startActivity(intent);
				count=0;         	
             }
			if (count > 11)
			{
				count = 0;
			}
		}
		

		protected float[] lowPass( float[] input, float[] output ) {
		    if ( output == null ) return input;

		    for ( int i=0; i<input.length; i++ ) {
		        output[i] = output[i] + ALPHA * (input[i] - output[i]);
		    }
		    return output;
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy){
			//Not used
		}
	}; 

	@Override
	public void onResume(){
		super.onResume(); 


		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener);
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(magnetometerSensor), SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_NORMAL);
		
	}

	@Override
	public void onPause() {
		locationManager.removeUpdates(locationListener);
		sensorManager.unregisterListener(sensorEventListener);		
		super.onPause(); 
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}