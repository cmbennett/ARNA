package edu.ycp.cs481.arna.client.ui;


import edu.ycp.cs481.arna.client.uicontroller.TourController;
import edu.ycp.cs481.arna.shared.model.TourMode;
import android.location.LocationManager;
import android.location.LocationListener; 
import android.location.Location; 
import android.os.Bundle;
import android.app.Activity; 
import android.content.Intent;
import android.hardware.Camera; 
import android.hardware.Sensor; 
import android.hardware.SensorManager;
import android.hardware.SensorEventListener; 
import android.hardware.SensorEvent;  
import android.util.Log; 
import android.view.SurfaceHolder; 
import android.view.SurfaceView; 
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
	
    float headingAngle;
    float pitchAngle;
    float rollAngle;
    boolean started;
    
    TextView headingValue;
    TextView pitchValue;
    TextView rollValue;
    private static final float ALPHA = 0.25f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tour_mode);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); 
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 2, locationListener); 

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); 
		
		magnetometerSensor = Sensor.TYPE_MAGNETIC_FIELD; 
		accelerometerSensor = Sensor.TYPE_ACCELEROMETER; 
		sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(magnetometerSensor), SensorManager.SENSOR_DELAY_FASTEST); 
		sensorManager.registerListener(sensorEventListener,  sensorManager.getDefaultSensor(accelerometerSensor), SensorManager.SENSOR_DELAY_FASTEST); 
		
		
		inPreview = false; 
		started = false;

		cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
		previewHolder = cameraPreview.getHolder(); 
		previewHolder.addCallback(surfaceCallback); 
	
		tour = new TourMode(); 
		cont = new TourController(tour); 
		
		 headingValue = (TextView) findViewById(R.id.headingValue);
	        pitchValue = (TextView) findViewById(R.id.pitchValue);
	        rollValue = (TextView) findViewById(R.id.rollValue);
	
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
			double azmith = sensorEvent.values[0];
		
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

					float azimuth = (float) Math.toDegrees(orientation[0]);
					if (azimuth < 0.0f)
					{
						azimuth += 360.f;
					}
					float pitch = (float) Math.toDegrees(orientation[1]);
					/*if (pitch > 24.5 || pitch < 25.5)
					{
						pitch = 0;
					}
					else
					{
						pitch  = pitch;
					}*/
					float roll  = (float) Math.toDegrees(orientation[2]); 
					/*if (roll < 0.0f)
					{
						roll += 90.f;
					}*/
					
					
					 headingValue.setText(String.valueOf(azimuth));
                      pitchValue.setText(String.valueOf(pitch));
                       rollValue.setText(String.valueOf(roll)); 
                       
                   /*  if(roll > 131 || roll < 150 && !started)
                       {
                    	   Intent intent = new Intent(edu.ycp.cs481.arna.client.ui.TourModeView.this, edu.ycp.cs481.arna.client.ui.CompassModeView.class);  
                    	   startActivity(intent);
                    	   started = true;
                       }
                  */
					
					
				cont.updateOrientation(azimuth, pitch, roll); 
				
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
		camera = Camera.open(); 

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