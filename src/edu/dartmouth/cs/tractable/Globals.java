package edu.dartmouth.cs.tractable;

import android.content.Context;
import android.telephony.TelephonyManager;

public abstract class Globals {

	// Debugging tag for the whole project
	public static final String TAG = "Tractable";

	// constant for parsing time
	public static final int SECONDS_PER_HOUR = 3600;

	// Some map display related consts 
	public static final double MINIMUM_LOCATION_ACCURACY = 15.0;
	public static final int GPS_LOCATION_CACHE_SIZE = 100000;
	public static final int DEFAULT_MAP_ZOOM_LEVEL = 17;

	// for session detail display
	public static final String MAX_BATHROOM_QUALITY = "5.0";
	public static final String MAX_EXPERIENCE_QUALITY = "10.0";

	// Table schema, column names
	public static final String KEY_ROWID = "_id";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_DATE_TIME = "date_time";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_COMMENT = "comment";
	public static final String KEY_BUILDING = "building";
	public static final String KEY_FLOOR = "floor";
	public static final String KEY_BATHROOMQUALITY = "bathroom_quality";
	public static final String KEY_EXPERIENCEQUALITY = "experience_quality";

	public static final String KEY_GPS_DATA = "gps_data";

	public static final String KEY_WIFI = "wifi";

	// Consts for GCM
	// Google API project id registered to use GCM.
	// Different student should have different ones.
	public static final String SENDER_ID = "";
	// URL of the AppEngine Server 
	// Different student should have different ones.
	public static final String SERVER_URL = "";
	// Intent used to display a message in the screen.
	public static final String DISPLAY_MESSAGE_ACTION = "DISPLAY_MESSAGE";
	//Intent's extra that contains the message to be displayed.
	public static final String EXTRA_MESSAGE = "message";
	public static String UPDATE = "Update";
	public static String DELETE = "Delete";
	public static String SEPARATOR = ":"; 

	//GAE
	public static String getSenderID(Context context) {
		TelephonyManager telMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 

		return telMgr.getDeviceId();
	}
}
