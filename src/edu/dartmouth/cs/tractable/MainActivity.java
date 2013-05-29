package edu.dartmouth.cs.tractable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends Activity {

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;

		// GCM registration
		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);
		// Make sure the manifest was properly set - comment out this line
		GCMRegistrar.checkManifest(this);

		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			// Automatically registers application on startup.
			GCMRegistrar.register(this, Globals.SENDER_ID);
		} 

		findViewById(R.id.start_relativelayout).setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 // send to start page (ManualInputActivity)
		    	 Intent i = new Intent(mContext, TimerActivity.class);
		    	 startActivity(i);
		     }      
		});

		findViewById(R.id.stats_relativelayout).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// send to stats page (StatsTabActivity)
				Intent i = new Intent(mContext, StatsTabActivity.class);
				startActivity(i);
			}       
		});
	}


}
