package edu.ycp.cs481.arna.client.ui;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.TourMode;
import edu.ycp.cs481.shared.persistence.DatabaseHelper;
import edu.ycp.cs481.shared.persistence.addingTourModeWaypoints;
import android.location.LocationManager;
import android.location.LocationListener; 
import android.location.Location; 
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Camera; 
import android.hardware.Sensor; 
import android.hardware.SensorManager;
import android.hardware.SensorEventListener; 
import android.hardware.SensorEvent;  
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder; 
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class TourModeView extends Activity {
	SurfaceView cameraPreview; 
	SurfaceHolder previewHolder; 
	Camera camera; 
	boolean inPreview; 
	SensorManager sensorManager;
	Sensor accelerometer; 

	int accelerometerSensor; 

	float[] buffer = new float[10];

	LocationManager locationManager; 
	int magnetometerSensor;

	addingTourModeWaypoints waypoints;
	TourController cont; 

	float roll;
	float pitch;
	float azimuth;

	boolean started;
	double latitude;
	double longitude;
	double altitude;
	TextView LocationID;
	TextView Description;
	boolean touched;

	boolean firstTime = false;
	private ArrayList<ImageView> static_list;
	private List<ImageView> dynamic_list;

	private ArrayList<TextView> Location_IDS;
	private List<TextView> Location_list;

	int buffer_counter;
	boolean readyForAverage;

	double GEOIDHEIGHT = 34;
	double viewAngle;
	double viewVertAngle;

	Point size = new Point();

	// private DatabaseHelper db;

	private static final float ALPHA = 0.25f;

	@SuppressLint("NewApi")
	public android.hardware.Camera.CameraInfo info =  new android.hardware.Camera.CameraInfo();

	@SuppressLint("NewApi")
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

		cont = new TourController(new TourMode()); 

		if(firstTime == false) {
			waypoints = new addingTourModeWaypoints(cont.getModel());
			firstTime = true;
		}

		// Night mode.
		Calendar c = Calendar.getInstance();
		SimpleDateFormat hour = new SimpleDateFormat("HH"); // 24 hour 		
		int hours = Integer.parseInt(hour.format(c.getTime()));


		if(hours < 5 || hours > 17) {
			LocationID.setTextColor(Color.WHITE);
		} else {			
			LocationID.setTextColor(Color.BLACK);
		}

		Display display = getWindowManager().getDefaultDisplay();		
		display.getSize(size);

		Description= (TextView) findViewById(R.id.textView1);
		touched = true;
		buffer_counter = 0;
		readyForAverage = false;

		static_list = new ArrayList<ImageView>();
		static_list.add((ImageView) findViewById(R.id.ImageView01));
		static_list.add((ImageView) findViewById(R.id.ImageView02));
		static_list.add((ImageView) findViewById(R.id.ImageView03));
		static_list.add((ImageView) findViewById(R.id.ImageView04));
		static_list.add((ImageView) findViewById(R.id.ImageView05));
		static_list.add((ImageView) findViewById(R.id.ImageView06));
		static_list.add((ImageView) findViewById(R.id.ImageView07));
		static_list.add((ImageView) findViewById(R.id.ImageView08));
		static_list.add((ImageView) findViewById(R.id.ImageView09));
		static_list.add((ImageView) findViewById(R.id.ImageView10));

		dynamic_list = new ArrayList<ImageView>();


		Location_IDS = new ArrayList<TextView>();
		Location_IDS.add((TextView) findViewById(R.id.LocationID01));
		Location_IDS.add((TextView) findViewById(R.id.LocationID02));
		Location_IDS.add((TextView) findViewById(R.id.LocationID03));
		Location_IDS.add((TextView) findViewById(R.id.LocationID04));
		Location_IDS.add((TextView) findViewById(R.id.LocationID05));
		Location_IDS.add((TextView) findViewById(R.id.LocationID06));
		Location_IDS.add((TextView) findViewById(R.id.LocationID07));
		Location_IDS.add((TextView) findViewById(R.id.LocationID08));
		Location_IDS.add((TextView) findViewById(R.id.LocationID09));
		Location_IDS.add((TextView) findViewById(R.id.LocationID10));

		Location_list = new ArrayList<TextView>();
	}

	/*
	public POIList getPOIList(String tag) {
		try {
			db.openDatabase();
		} catch (java.sql.SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Cursor cursor = db.getCursorfromDatabase(tag);
		POIList poi_list = new POIList();    
		poi_list.getListFromCursor(cursor);
		db.close();
		return poi_list;
	}
	 */

	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location){	

			latitude = location.getLatitude(); 
			longitude = location.getLongitude(); 
			altitude = location.getAltitude() + GEOIDHEIGHT; 

			cont.updateLocation(latitude, longitude, altitude); 
			cont.getModel().populateOnScreen(viewAngle);

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

	public void Sensor() {

	}

	final SensorEventListener sensorEventListener = new SensorEventListener(){
		float[] gravity; 
		float[] geomagnetic; 
		float var;
		float inclination;

		@SuppressLint("NewApi")
		public void onSensorChanged(SensorEvent sensorEvent) {
			Context context;
			int SCREEN_ORIENTATION_SENSOR_LANDSCAPE = 6;
			final int rotation = getResources().getConfiguration().orientation;
			/*switch (rotation) {
			case Surface.ROTATION_0:
				// return "Portrait";
				setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
			case Surface.ROTATION_90:
				setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
				// return "Landscape";
			case Surface.ROTATION_180:
				setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
				// return "Reverse Portrait";	
				int  degrees = 180;
				int result = (info.orientation - degrees + 360) % 360;
				camera.setDisplayOrientation(result);

			default:
				// return "Reverse Landscape";
				setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
			}		
			 */



			float R[] = new float[9]; 
			float I[] = new float[9]; 
			float outR[] = new float[9]; 
			float orientation[] = new float[3];
			List<POI> list = cont.getModel().getOnScreen();
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

				cont.getModel().populateOnScreen(viewAngle);
				cont.getModel().computePOIVector(viewAngle, viewVertAngle, size.x, size.y);

				renderMarkers(dynamic_list, list);

				for(ImageView image : static_list) 
				{
					for (TextView ids : Location_IDS)
					{
						image.setVisibility(View.INVISIBLE);
						ids.setText("");
					}
				}

				// If there are points to be drawn on the screen...
				if(!list.isEmpty() ) { 

					for(ImageView image : dynamic_list)
					{
						image.setVisibility(View.VISIBLE);
					}

					renderMarkers(dynamic_list, list);

					/*LocationID.setX(x);
					LocationID.setY(cont.getModel().getOnScreen().get(0).getVector().getY() - 50);

					image.setY(cont.getModel().getOnScreen().get(0).getVector().getY());

					LocationID.setText(cont.getModel().getOnScreen().get(0).getName()); // if not empty
					Description.setMovementMethod(new ScrollingMovementMethod());
					 */
				}
			} else if(list.isEmpty()) { // if there is NO elements 
				for(ImageView image : dynamic_list)
				{
					for (TextView ids : Location_list)						
					{
						image.setVisibility(View.INVISIBLE);
						ids.setText(""); // If not empty...
						Description.setText("");
						Description.setVisibility(View.INVISIBLE);
					}
				}
			}





		}

		/*if(roll > 145)
			{
				Intent intent = new Intent(TourModeView.this, CompassModeView.class);  
				startActivity(intent);
			}*/

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
	public void onResume() {
		Sensor gsensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		Sensor asensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor msensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener);
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
		//camera.setDisplayOrientation(-180); // Set the camera upside down.
		camera=null;
		inPreview=false;

		super.onPause(); 
	}

	private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
		Camera.Size result = null; 

		for(Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if(size.width<=width && size.height <= height) {
				if(result == null) {
					result = size; 
				} else {
					int resultArea = result.width * result.height; 
					int newArea = size.width * size.height; 

					if(newArea > resultArea) {
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
				viewVertAngle = camera.getParameters().getVerticalViewAngle();

			}	
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub

		}
	};

	@SuppressLint("NewApi")
	private void renderMarkers(List<ImageView> markers, List<POI> points) {
		// Ensure there is one marker for each point to be drawn.
		if(markers.size() < points.size() && points.size() != 0) {
			do {

				markers.add(static_list.get(markers.size()));				

			} while(markers.size() < points.size());
		} else if(markers.size() > points.size()) {
			do {
				markers.remove(markers.size()-1);
			} while(markers.size() > points.size());
		}
		int count = 0;




		// For each point to be drawn to the screen...
		for( POI poi : points) {	

			// Get the horizontal displacement of the current point.
			float x = poi.getVector().getX();

			poi.addBufferValue(x);			
			// Update the image horizontal position.
			markers.get(count).setX(poi.getRollingAverage());

			// Set images vertical position.
			markers.get(count).setY(poi.getVector().getY());

			Description.setText(poi.getDescription());

			for(ImageView image : dynamic_list) {

				for (TextView ids : Location_list)
				{
					ids.setText(poi.getName());
					ids.setX(poi.getRollingAverage());
					ids.setY(poi.getVector().getY() - 50);
					
					
					image.setOnClickListener(new View.OnClickListener(){ 
						public void onClick(View v) {

							touched = !touched; 
							if (!touched) {

								Description.setVisibility(View.VISIBLE);
								Description.setMovementMethod(new ScrollingMovementMethod());						
							} else {
								if(touched)
									Description.setVisibility(View.INVISIBLE);
							}
						}

					});
				}
			}
			count++;
		}

	}
}
