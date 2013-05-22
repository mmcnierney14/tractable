package edu.dartmouth.cs.tractable;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.cs.dartmouth.tractable.R;


public class DisplaySessionActivity extends Activity {
	
	private LocationManager mLocationManager;
	
	private Context mContext;
	
	// Map elements:
	public GoogleMap mMap;
	public LatLng location;
	public double latitude;
	public double longitude;
	public Marker marker;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_session);
		
		mContext = this;
		
		latitude = getIntent().getExtras().getDouble(Globals.KEY_LATITUDE);
		longitude = getIntent().getExtras().getDouble(Globals.KEY_LONGITUDE);
		location = new LatLng(latitude, longitude);
		
		// get the google map
		FragmentManager fm = getFragmentManager();
		MapFragment myMapFragment = (MapFragment) fm.findFragmentById(R.id.map);
		mMap = myMapFragment.getMap();
		mMap.setMyLocationEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        // zoom in to the bathroom location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,
                Globals.DEFAULT_MAP_ZOOM_LEVEL));
        
        // put the marker at the location
        mMap.addMarker(new MarkerOptions().position(location));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_session, menu);
		return true;
	}

}
