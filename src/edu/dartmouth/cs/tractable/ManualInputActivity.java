package edu.dartmouth.cs.tractable;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class ManualInputActivity extends Activity {

	// Exercise entry
	public BathroomSessionHelper mEntry;
	
	public LocationManager mLocationManager;
	private double latitude;
	private double longitude;

	
	public static final int LIST_ITEM_ID_DATE = 0;
	public static final int LIST_ITEM_ID_TIME = 1;
	public static final int LIST_ITEM_ID_DURATION = 2;
	public Uri manualInputURI;

	// skeleton
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Setting the UI layout
		setContentView(R.layout.manualinput);

		// Initialize the ExerciseEntryHelper()
		mEntry = new BathroomSessionHelper();
		
		
		// Location manager for getting wifi location
		String svc = this.LOCATION_SERVICE;
		mLocationManager = (LocationManager) getSystemService(svc);
		
		// set up adapter for AutoComplete building list
		AutoCompleteTextView mAutoComplete = (AutoCompleteTextView) findViewById(R.id.actBuilding);
	    String [] buildings = getResources().getStringArray(R.array.building_array);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, buildings);
	    mAutoComplete.setAdapter(adapter);
		
	}

	// "Save" button is clicked
	public void onSaveClicked(View v) {
		
		setBuilding();
		setFloor();
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
		AutoCompleteTextView act = (AutoCompleteTextView) findViewById(R.id.actBuilding);
		String building = act.getText().toString();
		mEntry.setBuilding(building);
	}
	
	public void setFloor() {
		Spinner spin = (Spinner) findViewById(R.id.spinnerFloor);
		int position = spin.getSelectedItemPosition();
		mEntry.setFloor(position);
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
