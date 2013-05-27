package edu.dartmouth.cs.tractable;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class ManualInputActivity extends Activity {

	// Exercise entry
	public BathroomSessionHelper mEntry;
	
	public LocationManager mLocationManager;
	private double latitude;
	private double longitude;
	
	public Intent mBathroomServiceIntent;
	public BathroomInferenceService mBathroomInferenceService;
	public String mBathroomName;
	
	public TextView bathroomNameView;

	
	public static final int LIST_ITEM_ID_DATE = 0;
	public static final int LIST_ITEM_ID_TIME = 1;
	public static final int LIST_ITEM_ID_DURATION = 2;
	public Uri manualInputURI;
	
	// Service connection for bathroom service
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBathroomInferenceService = ((BathroomInferenceService.TractableBinder) service).getService();
			
			mBathroomName = mBathroomInferenceService.bathroomName;
			
			if (mBathroomName != null) bathroomNameView.setText(mBathroomName);
			
			Log.d(Globals.TAG, "Closest wifi name: " + mBathroomName);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			
		}
	};

	// skeleton
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Setting the UI layout
		setContentView(R.layout.manualinput);

		// Initialize the ExerciseEntryHelper()
		mEntry = new BathroomSessionHelper();
		
		
		// Location manager for getting wifi location
		mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		// Bathroom inference service to get closest bathroom name
		mBathroomServiceIntent = new Intent(this, BathroomInferenceService.class);
		startService(mBathroomServiceIntent);
		bindService(mBathroomServiceIntent, connection, Context.BIND_AUTO_CREATE);
		
		// Get the bathroom text view
		bathroomNameView = (TextView) findViewById(R.id.bathroom_name);
		
		
		// set up adapter for AutoComplete building list
//		AutoCompleteTextView mAutoComplete = (AutoCompleteTextView) findViewById(R.id.actBuilding);
//	    String [] buildings = getResources().getStringArray(R.array.building_array);
//	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, buildings);
//	    mAutoComplete.setAdapter(adapter);
		
		
		
	}

	// "Save" button is clicked
	public void onSaveClicked(View v) {
		
		setBuilding();
		setBathroomQuality();
		setExperienceQuality();
		setComment();
		setLocation();

		// Insert the exercise entry into database
		mEntry.insertToDB(this);

		Toast.makeText(getApplicationContext(), "Entry Saved", Toast.LENGTH_SHORT).show();
		// Close the activity
		finish();
	}

	//skeleton
	// "Cancel" button is clicked
	public void onCancelClicked(View v) {
		// Pop up a toast, discard the input and close the activity directly
		// Making a "toast" informing the user changes are canceled.
		Toast.makeText(getApplicationContext(),
				"Canceled",
				Toast.LENGTH_SHORT).show();
		// Close the activity
		finish();
	}


	// Setters for mEntry fields
	public void setLocation() {
		
		String provider = mLocationManager.NETWORK_PROVIDER;
		Location l = mLocationManager.getLastKnownLocation(provider);
		latitude = l.getLatitude();
		longitude = l.getLongitude();
		LatLng latlng = new LatLng(latitude, longitude);
		mEntry.setLocation(latlng);
		
	}
	
	public void setBuilding() {
		mEntry.setBuilding(mBathroomName);
	}
	
	public void setBathroomQuality() {
		RatingBar rat = (RatingBar) findViewById(R.id.ratingBathroom);
		double stars = rat.getRating();
		mEntry.setBathroomQuality(stars);
	}
	
	public void setExperienceQuality() {
		SeekBar seek = (SeekBar) findViewById(R.id.seekExperience);
		int progress = seek.getProgress();
		mEntry.setExperienceQuality(progress);
	}

	public void setComment() {
		EditText et = (EditText) findViewById(R.id.editComment);
		String comment = et.getText().toString();
		mEntry.setComment(comment);
	}
}
