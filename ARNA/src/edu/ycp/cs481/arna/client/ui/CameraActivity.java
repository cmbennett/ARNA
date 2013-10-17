package edu.ycp.cs481.arna.client.ui;

import java.io.IOException;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.hardware.Camera;
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
      
       buttonStartCameraPreview.setOnClickListener(new Button.OnClickListener(){

   @Override
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
  // TODO Auto-generated method stub
  
 }

 @Override
 public void surfaceCreated(SurfaceHolder holder) {
  // TODO Auto-generated method stub
  
 }

 @Override
 public void surfaceDestroyed(SurfaceHolder holder) {
  // TODO Auto-generated method stub
  
 }
}
//
//public class CameraActivity extends Activity {
//	
//	private static String logtag = "camera";
//	private static int TAKE_PICTURE = 1;
//	private Uri imageUri;
//	
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_camera);
//		
//		Button cameraButton = (Button)findViewById(R.id.button_capture);
//		cameraButton.setOnClickListener(cameraListener);
//				
//	}
//
//	
//	private OnClickListener cameraListener = new OnClickListener() {
//		public void onClick(View v){
//			takePhoto(v);
//		}
//	};
//	
//	private void takePhoto(View v)
//	{
//	/*	Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//		File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picture.jpg");
//		imageUri = Uri.fromFile(photo);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		startActivityForResult(intent,TAKE_PICTURE);
//	
//	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
//	{
//		super.onActivityResult(requestCode, resultCode, intent);
//		if (resultCode == Activity.RESULT_OK){
//			Uri selectedImage = imageUri;
//			getContentResolver().notifyChange(selectedImage, null);
//			
//			ImageView imageView = (ImageView)findViewById(R.id.image_camera);
//			ContentResolver cr = getContentResolver();
//			Bitmap bitmap;
//			
//			try {
//				bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
//				imageView.setImageBitmap(bitmap);
//				Toast.makeText(CameraActivity.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
//				
//				
//			} catch(Exception e){
//				Log.e(logtag, e.toString());
//			}
//			
//		}
//		
//	}
//	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.camera, menu);
//		return true;
//	}
//
//}/*
//
