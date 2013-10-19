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
import android.util.Log; 
import android.view.SurfaceHolder; 
import android.view.SurfaceView; 



public class CameraActivity extends Activity {
	SurfaceView cameraPreview; 
	SurfaceHolder previewHolder; 
	Camera camera; 
	boolean inPreview; 

	final static String TAG = "test"; 
	SensorManager sensorManager;
	Sensor accelerometer; 
	

	float azimuth; 
	float pitchAngle; 
	float rollAngle; 

	int accelerometerSensor; 
	float xAxis; 
	float yAxis; 
	float zAxis; 

	LocationManager locationManager; 
	double latitude; 
	double longitude; 
	double altitude;
	int magnetometerSensor;
	
	TourMode tour; 
	TourController cont; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener); 

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
		
		magnetometerSensor = Sensor.TYPE_MAGNETIC_FIELD; 
		accelerometerSensor = Sensor.TYPE_ACCELEROMETER; 
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(magnetometerSensor), SensorManager.SENSOR_DELAY_NORMAL); 
		sensorManager.registerListener(sensorEventListener,  sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_NORMAL); 
		
		
		inPreview = false; 

		cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
		previewHolder = cameraPreview.getHolder(); 
		previewHolder.addCallback(surfaceCallback); 
	
		
		//cont = new TourController(new TourMode()); 
	}

	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location){
			latitude = location.getLatitude(); 
			longitude = location.getLongitude(); 
			altitude = location.getAltitude(); 
			
			//cont.updateLocation(latitude, longitude, altitude); 
			Log.d(TAG, "Latitude: " + String.valueOf(latitude));
			Log.d(TAG, "Longitude: " + String.valueOf(longitude));
			Log.d(TAG, "Altitude: " + String.valueOf(altitude));
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
			if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
				gravity = sensorEvent.values; 
			}
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				geomagnetic = sensorEvent.values; 
			}
			if(gravity != null && geomagnetic != null){
				float R[] = new float[9]; 
				float I[] = new float[9]; 
				
				if(SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)){
					float orientation[] = new float[3]; 
					SensorManager.getOrientation(R, orientation); 
					
					double azimuth = Math.toDegrees((double)orientation[0]);
					double pitch = Math.toDegrees((double)orientation[1]);
					double roll = Math.toDegrees((double)orientation[2]); 
					
					Log.d(TAG, "Azimuth: " + String.valueOf(azimuth));
					Log.d(TAG, "Pitch: " + String.valueOf(pitch));
					Log.d(TAG, "Roll: " + String.valueOf(roll));
					
					//cont.updateOrientation(azimuth, pitch, roll); 
				}
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy){
			//Not used
		}
	}; 

	@Override
	public void onResume(){
		super.onResume(); 


		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener);
		//sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(magnetometerSensor), SensorManager.SENSOR_DELAY_NORMAL);
		//sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_NORMAL);
		camera = Camera.open(); 

	}

	@Override
	public void onPause() {
		if (inPreview) {
			camera.stopPreview();
		}

		//locationManager.removeUpdates(locationListener);
		//sensorManager.unregisterListener(sensorEventListener);
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
		public void surfaceCreated(SurfaceHolder holder){
			try{
				camera.setPreviewDisplay(previewHolder); 
			}
			catch(Throwable t){
				Log.e("Camera", "Exception in setPreviewDisplay()", t); 
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			Camera.Parameters parameters = camera.getParameters(); 
			Camera.Size size = getBestPreviewSize(width, height, parameters); 

			if(size != null){
				parameters.setPreviewSize(size.width, size.height); 
				camera.setParameters(parameters); 

				camera.startPreview(); 
				inPreview = true; 
			}	
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// TODO Auto-generated method stub
			
		}
	};
}