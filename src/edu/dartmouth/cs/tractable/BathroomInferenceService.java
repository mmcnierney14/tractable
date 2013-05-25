package edu.dartmouth.cs.tractable;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
		
	// set up the MyRunsBinder
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
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		// Get wifi access point string from intent
		String wifi_name = intent.getStringExtra(Globals.KEY_WIFI);
		
		
		
		return START_STICKY;
	}
	
	
	private BroadcastReceiver locationReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle data = intent.getExtras();
			wifiName = data.getString("key_bio_location", "");
			String[] locationLines = wifiName.split(";");
			String locationStat = "";
			for (String line : locationLines) {
				String[] wifiComponents = line.split(",");
				locationStat = locationStat
						+ parseTime(Double.valueOf(wifiComponents[0]).longValue())+","
						+ wifiComponents[1] +","+wifiComponents[2] + "\n";
			}
			wifiName = locationStat;
		}
	};
	
	private String parseTime(long timeInSec) {

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timeInSec * 1000);
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat(DATE_FORMAT);

		return dateFormat.format(calendar.getTime());
	}


}
