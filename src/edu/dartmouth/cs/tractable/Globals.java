package edu.dartmouth.cs.tractable;

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
	
	public static final String KEY_PRIVACY = "privacy";
	public static final String KEY_GPS_DATA = "gps_data";

}
