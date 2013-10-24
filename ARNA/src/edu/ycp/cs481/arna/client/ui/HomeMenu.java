package edu.ycp.cs481.arna.client.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_menu);
		Button tourButton = (Button)findViewById(R.id.Button_Tour);
		tourButton.setOnClickListener(tourModeListener);
		
		Button compassButton = (Button)findViewById(R.id.Button_Compass);
		compassButton.setOnClickListener(compassModeListener);
	}
		
		private OnClickListener compassModeListener = new OnClickListener() {
			   
			@Override
			public void onClick(View arg0) {
				   Intent TourMode = new Intent(getApplicationContext(), edu.ycp.cs481.arna.client.ui.TourModeView.class);
				   startActivity(TourMode);
			}
		};
		        
		private OnClickListener tourModeListener = new OnClickListener() {
			   
			@Override
			public void onClick(View arg0) {
				   Intent CompassMode = new Intent(getApplicationContext(), edu.ycp.cs481.arna.client.ui.CompassModeView.class);
				   startActivity(CompassMode);
			}
		};
		
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_menu, menu);
		return true;
	}

}
