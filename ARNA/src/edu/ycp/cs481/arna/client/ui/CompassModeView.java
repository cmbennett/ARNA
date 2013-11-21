package edu.ycp.cs481.arna.client.ui;


import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.TourMode;
import android.location.LocationManager;
import android.location.LocationListener; 
import android.location.Location; 
import android.os.Bundle;
import android.app.Activity;
import android.hardware.Camera; 
import android.hardware.Sensor; 
import android.hardware.SensorManager;
import android.hardware.SensorEventListener; 
import android.hardware.SensorEvent;


public class CompassModeView extends Activity {

	SensorManager sensorManager;
	Sensor accelerometer; 

	int accelerometerSensor; 

	LocationManager locationManager; 
	int magnetometerSensor;

	TourMode tour; 
	TourController cont; 
	float roll;
	float pitch;
	float azimuth;
	boolean started;

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

		tour = new TourMode(); 
		cont = new TourController(tour); 
	}

	LocationListener locationListener = new LocationListener() {
		
		// Event handler for change in location.
		public void onLocationChanged(Location location) {
			double latitude = location.getLatitude(); 
			double longitude = location.getLongitude(); 
			double altitude = location.getAltitude(); 

			cont.updateLocation(latitude, longitude, altitude); 
		}

		public void onProviderDisabled(String arg0) {
			//TODO Auto-generated method sub
		}

		public void onProviderEnabled(String arg0) {
			//TODO Auto-generated method sub
		}
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			//TODO Auto-generated method sub
		}
	}; 

	final SensorEventListener sensorEventListener = new SensorEventListener() {
		float[] gravity; 
		float[] geomagnetic; 
		float var;
		float inclination;
		
		// Event handler for sensor event.
		public void onSensorChanged(SensorEvent sensorEvent) {
			//int orientations =  getResources().getConfiguration().orientation;
			float R[] = new float[9]; 
			float I[] = new float[9]; 
			float outR[] = new float[9]; 
			float orientation[] = new float[3];

			// Acquire and filter magnetic field data from device.
			if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
				gravity = lowPass(sensorEvent.values.clone(), gravity);
			}
			
			// Acquire and filter accelerometer data from device.
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				geomagnetic = lowPass(sensorEvent.values.clone(), geomagnetic);
			}
			
			// As long as acquired data is valid, acquire the transformation matrix.
			if(gravity != null && geomagnetic != null) {
				SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
			
				inclination = (float) Math.acos(outR[8]);
				
				// If the device is upright (or nearly so), use unadjusted values.
				if (inclination < 25.0f || inclination > 155.0f ) {
					SensorManager.getOrientation(R, orientation); 
				}
				else { // Otherwise, remap the coordinates for portrait mode.
					SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_X, SensorManager.AXIS_Z, outR);
					SensorManager.getOrientation(outR, orientation); 
				}	
				
				// Covert from radians to degrees.
				double x180pi = 180.0 / Math.PI;
				
				azimuth = (float)(orientation[0] * x180pi);
				
				// Adjust starting angle due to device specs.
				azimuth -= 90.0f;
				
				// Flip angle over the horizontal plane. This is done because android devices measure angles counterclockwise instead of clockwise.
				if (azimuth < 180) { // West -> East
					var = 2*(180-azimuth);
					azimuth += var;
				} else if (azimuth > 180) { //East -> West
					var = 2*(azimuth - 180);
					azimuth -= var;
				}
				
				// Enforce wrap-around.
				if ( azimuth < 0.0f) { // Lower bound
					azimuth += 360.0f; 
				} else if ( azimuth > 360.0f) { // Upper bound
					azimuth -= 360.0f; 
				}

				pitch = (float)(orientation[1] * x180pi);
				
				roll = (float)(orientation[2] * x180pi);			

				// Update the model object.
				cont.updateOrientation(azimuth, pitch, roll); 
			}
			
			/*if(roll > 145)
			{
				Intent intent = new Intent(TourModeView.this, CompassModeView.class);  
				startActivity(intent);
			}*/
		}	

		protected float[] lowPass( float[] input, float[] output ) {
			if (output == null) return input;

			for(int i = 0; i < input.length; i++) {
				output[i] = output[i] + ALPHA * (input[i] - output[i]);
			}
			
			return output;
		}
		
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			//
		}
	}; 

	@Override
	public void onResume(){
		super.onResume(); 
		Sensor gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		Sensor asensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener);
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(magnetometerSensor), SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onPause() {
		locationManager.removeUpdates(locationListener);
		sensorManager.unregisterListener(sensorEventListener);
	
		super.onPause(); 
	}

	private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
		Camera.Size result = null; 

		for(Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if(size.width <= width && size.height <= height) {
				if(result == null) {
					result = size; 
				} else {
					int resultArea = result.width * result.height; 
					int newArea = size.width * size.height; 

					if(newArea > resultArea){
						result = size; 
					}
				}
			}
		}

		return(result); 
	}
}