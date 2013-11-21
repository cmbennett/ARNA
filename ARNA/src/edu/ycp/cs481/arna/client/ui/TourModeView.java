package edu.ycp.cs481.arna.client.ui;


import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.POI;
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
import android.util.Log;
import android.view.SurfaceHolder; 
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;


public class TourModeView extends Activity {
	SurfaceView cameraPreview; 
	SurfaceHolder previewHolder; 
	Camera camera; 
	boolean inPreview; 

	final static String TAG = "test"; 
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
	double latitude;
	double longitude;
	double altitude;
	TextView LocationID;


	double viewAngle;

	private static final float ALPHA = 0.25f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_mode);

		// Initialize sensor objects.
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener); 

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 

		magnetometerSensor = Sensor.TYPE_MAGNETIC_FIELD; 
		accelerometerSensor = Sensor.TYPE_ACCELEROMETER; 
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(magnetometerSensor), SensorManager.SENSOR_DELAY_UI); 
		sensorManager.registerListener(sensorEventListener,  sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_UI); 

		inPreview = false; 
		started = false;

		cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
		previewHolder = cameraPreview.getHolder(); 
		previewHolder.addCallback(surfaceCallback); 

		tour = new TourMode(); 
		cont = new TourController(tour); 

		LocationID = (TextView) findViewById(R.id.LocationID);
		LocationID.setTextColor(0xFF000000); //black

		POI kinsley = new POI(39.949120, -76.735165,32.0);
		kinsley.setName("Kinsley Enginnering Center");
		POI northSide = new POI(39.949792, -76.734041,70.0);
		northSide.setName("North side Commons");
		tour.addWaypoint(kinsley);
		tour.addWaypoint(northSide);
	}
	
	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location){	
			
			 latitude = location.getLatitude(); 
			 longitude = location.getLongitude(); 
			 altitude = location.getAltitude(); 

			cont.updateLocation(latitude, longitude, altitude); 
			tour.populateOnScreen(viewAngle);

		}
		@Override
		public void onProviderDisabled(String arg0){
			//TODO Auto-generated method sub
		}
		@Override
		public void onProviderEnabled(String arg0){
			//TODO Auto-generated method sub
		}
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2){
			//TODO Auto-generated method sub
		}
	}; 

	final SensorEventListener sensorEventListener = new SensorEventListener(){
		float[] gravity; 
		float[] geomagnetic; 
		float var;
		float inclination;
		public void onSensorChanged(SensorEvent sensorEvent){
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
			if(gravity != null && geomagnetic != null){
				SensorManager.getRotationMatrix(R, I, gravity, geomagnetic);
			
				inclination = (float) Math.acos(outR[8]);
				
				// If the device is upright (or nearly so), use unadjusted values.
				if (inclination < 25.0f || inclination > 155.0f ) {
					SensorManager.getOrientation(R, orientation); 
				} else { // Otherwise, remap the coordinates for portrait mode.
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
				
			
				tour.populateOnScreen(viewAngle);

				if (!tour.getOnScreen().isEmpty() )
				{
					LocationID.setText(tour.getOnScreen().get(0).getName()); // if not empty
					
				}
				else if (tour.getOnScreen().isEmpty() )
				{
					LocationID.setText("NOTHING IN SIGHT"); // if empty
					
				}
		
			}

			/*if(roll > 145)
			{
				Intent intent = new Intent(TourModeView.this, CompassModeView.class);  
				startActivity(intent);
			}*/
		}	



		protected float[] lowPass( float[] input, float[] output) {
			if(output == null) return input;

			for(int  i = 0; i < input.length; i++) {
				output[i] = output[i] + ALPHA * (input[i] - output[i]);
			}
			return output;
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy){
			//
		}
	}; 

	@Override
	public void onResume(){
		
		Sensor gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		Sensor asensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);


		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2.0f, locationListener);
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(magnetometerSensor), SensorManager.SENSOR_DELAY_UI);
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_UI);
		camera = Camera.open(); 
		super.onResume(); 
		

	}

	@Override
	public void onPause() {
		if (inPreview) {
			camera.stopPreview();
		}

		locationManager.removeUpdates(locationListener);
		sensorManager.unregisterListener(sensorEventListener);
		camera.release();
		camera=null;
		inPreview=false;

		super.onPause(); 
	}

	private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
		Camera.Size result = null; 

		for(Camera.Size size : parameters.getSupportedPreviewSizes()){
			if(size.width<=width && size.height <= height){
				if(result == null){
					result = size; 
				}
				else{
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

	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera.setPreviewDisplay(previewHolder); 
			}
			catch(Throwable t) {
				Log.e("Camera", "Exception in setPreviewDisplay()", t); 
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Camera.Parameters parameters = camera.getParameters(); 
			Camera.Size size = getBestPreviewSize(width, height, parameters); 

			if(size != null) {
				parameters.setPreviewSize(size.width, size.height); 
				camera.setParameters(parameters); 

				camera.startPreview(); 
				inPreview = true; 
				viewAngle = camera.getParameters().getHorizontalViewAngle();
				
			}	
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub

		}
	};
}