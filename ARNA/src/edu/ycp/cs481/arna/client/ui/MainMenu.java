package edu.ycp.cs481.arna.client.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

	
	Button Tour;
	Button Compass;
	Button Settings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		Tour = (Button) findViewById(R.id.button1);
		Compass = (Button) findViewById(R.id.button2);
		Settings = (Button) findViewById(R.id.button3);
		
		  Tour.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) {
	        		Intent intent = new Intent(MainMenu.this, TourModeView.class);  
					startActivity(intent);
	          }
	      });
		  
		  Compass.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) {
	        	  Intent intent = new Intent(MainMenu.this, CompassModeView.class);  
					startActivity(intent);
	          }
	      });
		  
		  Settings.setOnClickListener(new View.OnClickListener() {
	          public void onClick(View v) {
	        	  Intent intent = new Intent(MainMenu.this, Settings.class);  
					startActivity(intent);
	          }
	      });
		  
	}


}
