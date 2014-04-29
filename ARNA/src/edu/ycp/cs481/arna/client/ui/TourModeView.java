package edu.ycp.cs481.arna.client.ui;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.POI;
import edu.ycp.cs481.arna.shared.model.TourMode;
import edu.ycp.cs481.shared.persistence.POIDataSource;
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
	private ArrayList<ImageView> static_img_list;
	private List<ImageView> dynamic_list;

	private ArrayList<TextView> static_loc_list;
	private List<TextView> dynamic_loc_list;

	int buffer_counter;
	boolean readyForAverage;

	double GEOIDHEIGHT = 34; 
	double viewAngle;
	double viewVertAngle;

	Point size = new Point();

	
	

	private static POIDataSource datasource;

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


		
		
		POISingleton.getInstance();		
		datasource = POISingleton.getDataSource();
		TourMode tourMode = new TourMode(datasource);
		cont = new TourController(tourMode); 
	
	if (POISingleton.getPOIS(datasource).size() == 0)
	{
		
		waypoints = new addingTourModeWaypoints(cont.getModel());
		ArrayList<POI> pois = waypoints.getPOI();	
		for (int i = 0; i < pois.size(); i++)
		{
			 // this adds the points to the database
			datasource.addPOI(new POI( pois.get(i).getName(),pois.get(i).getDescription(), pois.get(i).getLocation().getLatitude(),
					 pois.get(i).getLocation().getLongitude(), pois.get(i).getLocation().getElevation()));
		tourMode.addWaypoint(pois.get(i));
	
		}
	}
		

		
		

		// Night mode.
		Calendar c = Calendar.getInstance();
		SimpleDateFormat hour = new SimpleDateFormat("HH"); // 24 hour 		
		int hours = Integer.parseInt(hour.format(c.getTime()));


		Display display = getWindowManager().getDefaultDisplay();		
		display.getSize(size);

		Description= (TextView) findViewById(R.id.textView1);
		touched = true;
		buffer_counter = 0;
		readyForAverage = false;

		static_img_list = new ArrayList<ImageView>();
		static_img_list.add((ImageView) findViewById(R.id.ImageView01));
		static_img_list.add((ImageView) findViewById(R.id.ImageView02));
		static_img_list.add((ImageView) findViewById(R.id.ImageView03));
		static_img_list.add((ImageView) findViewById(R.id.ImageView04));
		static_img_list.add((ImageView) findViewById(R.id.ImageView05));
		static_img_list.add((ImageView) findViewById(R.id.ImageView06));
		static_img_list.add((ImageView) findViewById(R.id.ImageView07));
		static_img_list.add((ImageView) findViewById(R.id.ImageView08));
		static_img_list.add((ImageView) findViewById(R.id.ImageView09));
		static_img_list.add((ImageView) findViewById(R.id.ImageView10));

		dynamic_list = new ArrayList<ImageView>();


		static_loc_list = new ArrayList<TextView>();
		static_loc_list.add((TextView) findViewById(R.id.LocationID01));
		static_loc_list.add((TextView) findViewById(R.id.LocationID02));
		static_loc_list.add((TextView) findViewById(R.id.LocationID03));
		static_loc_list.add((TextView) findViewById(R.id.LocationID04));
		static_loc_list.add((TextView) findViewById(R.id.LocationID05));
		static_loc_list.add((TextView) findViewById(R.id.LocationID06));
		static_loc_list.add((TextView) findViewById(R.id.LocationID07));
		static_loc_list.add((TextView) findViewById(R.id.LocationID08));
		static_loc_list.add((TextView) findViewById(R.id.LocationID09));
		static_loc_list.add((TextView) findViewById(R.id.LocationID10));

		dynamic_loc_list = new ArrayList<TextView>();

		for (TextView ids : static_loc_list){
			if(hours < 5 || hours > 17) {
				ids.setTextColor(Color.WHITE);
			} else {			
				ids.setTextColor(Color.BLACK);
			}
		}
	}

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
			float R[] = new float[9]; 
			float I[] = new float[9]; 
			float outR[] = new float[9]; 
			float orientation[] = new float[3];
			List<POI> onScreenList = cont.getModel().getOnScreen();

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

				// If the dynamic lists are not the same sizes, wipe them.
				if(dynamic_list.size() != dynamic_loc_list.size()) {
					dynamic_list.removeAll(dynamic_list);
					dynamic_loc_list.removeAll(dynamic_loc_list);
				}

				// Ensure there is an ImageView and TextView object for each POI onscreen.
				if(dynamic_list.size() < onScreenList.size() && dynamic_loc_list.size() < onScreenList.size()) {
					do {
						dynamic_list.add(static_img_list.get(dynamic_list.size())); // Pull another ImageView object.
						dynamic_loc_list.add(static_loc_list.get(dynamic_loc_list.size())); // Pull another TextView object.
					} while(dynamic_list.size() < onScreenList.size() && dynamic_loc_list.size() < onScreenList.size());

				} else if(dynamic_list.size() > onScreenList.size() && dynamic_loc_list.size() > onScreenList.size()) {
					do {
						dynamic_list.remove(dynamic_list.size()-1); // Remove an ImageView object.
						dynamic_loc_list.remove(dynamic_loc_list.size()-1); // Remove a TextView object.
					} while(dynamic_list.size() > onScreenList.size() && dynamic_loc_list.size() > onScreenList.size());
				}
				
				for(ImageView image : static_img_list) {
					image.setVisibility(View.INVISIBLE);
					
				}

				for (TextView ids : static_loc_list) {
					ids.setText(""); // If not empty...
					ids.setVisibility(View.INVISIBLE); 
				}

				// If there are points to be drawn on the screen...
				if(!onScreenList.isEmpty() ) {	
					renderMarkers(onScreenList);		
				}
				else
				{
					Description.setText("");
					Description.setVisibility(View.INVISIBLE);
				}
			}
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
	private void renderMarkers( List<POI> POIList) {
		int count = 0;

		// For each PoI to be drawn onscreen...
		for(POI poi : POIList) {	

			// Get the horizontal displacement of the current point.
			float x = poi.getVector().getX();

			poi.addBufferValue(x);

			// Update the image horizontal position.
			dynamic_list.get(count).setX(poi.getRollingAverage());

			// Set images vertical position.
			dynamic_list.get(count).setY(poi.getVector().getY());

			// Initialize ImageView object for marker display.
			ImageView image = static_img_list.get(count);
			final String des = poi.getDescription();
			image.setVisibility(View.VISIBLE);
			image.setOnClickListener(new View.OnClickListener(){ 
				public void onClick(View v) {

					touched = !touched;
					if(!touched) {
						Description.setVisibility(View.VISIBLE);
						Description.setMovementMethod(new ScrollingMovementMethod());
						Description.setText(des);
					} else {
						if(touched)
						{
							Description.setVisibility(View.INVISIBLE);
						}
					}
				}
			});

			// Attach name to POI.
			TextView id = static_loc_list.get(count);
			id.setVisibility(View.VISIBLE);
			id.setText(poi.getName());
			id.setX(poi.getRollingAverage());
			id.setY(poi.getVector().getY() - 50);

			count++;
		}
	}
}