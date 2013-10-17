package edu.ycp.cs481.arna.client.ui;

import java.io.IOException;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class CameraActivity extends Activity implements SurfaceHolder.Callback{

 Camera camera;
 SurfaceView surfaceView;
 SurfaceHolder surfaceHolder;
 boolean previewing = false;;
 

 
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_camera);
      
       Button buttonStartCameraPreview = (Button)findViewById(R.id.startcamerapreview);
       Button buttonStopCameraPreview = (Button)findViewById(R.id.stopcamerapreview);
      
       getWindow().setFormat(PixelFormat.UNKNOWN);
       surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
       surfaceHolder = surfaceView.getHolder();
       surfaceHolder.addCallback(this);
       surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
       this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
      
       buttonStartCameraPreview.setOnClickListener(new Button.OnClickListener(){

    	   public void onClick(View v) {
    		    // TODO Auto-generated method stub
    		    if(!previewing){
    		     camera = Camera.open();
    		     if (camera != null){
    		      try {
    		       camera.setPreviewDisplay(surfaceHolder);
    		       camera.startPreview();
    		       previewing = true;
    		      } catch (IOException e) {
    		       // TODO Auto-generated catch block
    		       e.printStackTrace();
    		      }
    		     }
    		    }
    		   }});
      
     // this stops the view
       buttonStopCameraPreview.setOnClickListener(new Button.OnClickListener(){

   @Override
   public void onClick(View v) {
    // TODO Auto-generated method stub
    if(camera != null && previewing){
     camera.stopPreview();
     camera.release();
     camera = null;
     
     previewing = false;
    }
   }});
      
   }

  

 @Override
 public void surfaceChanged(SurfaceHolder holder, int format, int width,
   int height) {
	/* // TODO Auto-generated method stub
     Parameters params = camera.getParameters(); 
     camera.setParameters(params);
     this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
     camera.startPreview();
*/
 }

 @Override
 public void surfaceCreated(SurfaceHolder holder) {
  // TODO Auto-generated method stub
	/*  camera = Camera.open();
      try{
          camera.setPreviewDisplay(surfaceHolder);
      }
      catch(Exception e){
          e.printStackTrace();
      }*/
 }

 @Override
 public void surfaceDestroyed(SurfaceHolder holder) {
  // TODO Auto-generated method stub
	 // camera.stopPreview();
    //  camera.release();
 }
}
