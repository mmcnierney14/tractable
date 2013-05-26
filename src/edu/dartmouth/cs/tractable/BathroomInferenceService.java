package edu.dartmouth.cs.tractable;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class BathroomInferenceService extends Service {
	
	// Inferred bathroom name
	String bathroomName;
	
	String wifiName;
	
	public static final String DATE_FORMAT = "H:mm:ss MMM d yyyy";

	// Set up binder for the BathroomInferenceService using IBinder
	private final IBinder binder = new TractableBinder();	
		
	// set up the TractableBinder
    public class TractableBinder extends Binder {
        BathroomInferenceService getService() {
            // Return this instance of TrackingService so clients can call public methods
            return BathroomInferenceService.this;
        }
    }
    
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	@Override
	public void onCreate() {
		registerReceiver(locationReceiver, new IntentFilter("bio_location"));
	}
	
	private BroadcastReceiver locationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle data = intent.getExtras();
			wifiName = data.getString("key_bio_location", "");
			wifiName = wifiName.split(";")[0].split(",")[1];
		}
	};


}
