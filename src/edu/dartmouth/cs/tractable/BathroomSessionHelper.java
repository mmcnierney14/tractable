package edu.dartmouth.cs.tractable;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;



//The ExerciseEntryHelper class creates a data access object for the application to manage the data for us. 
//It has two operations: deleteEntryInDB() and insertToDB(). 
//Also to interact with the ExerciseEntry class, it defines some standard set functions to set detail values 
//to ExerciseEntry, and also defines some get functions to get values from the ExerciseEntry to the database. 
public class BathroomSessionHelper {
	
	// The ExerciseEntry 
	private BathroomSession mData;

	
	// Formatter
	public static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

	public BathroomSessionHelper() {
		mData = new BathroomSession();
	}

	// ************** Database operations ************** //
	
	// Write the current exercise entry into database.
	
	public long insertToDB(Context context) {
		//create a ContentValues object to store exercise entry.
		

		// put all the data saved in BathroomSession into the ContentValues object.
		ContentValues values = new ContentValues();
		values.put(Globals.KEY_DATE_TIME, mData.getDateTime().getTime());
		values.put(Globals.KEY_DURATION, mData.getDuration());
		if (mData.getBuilding() == null) {
			mData.setBuilding("Unknown building");
		}
		values.put(Globals.KEY_BUILDING, mData.getBuilding());
		values.put(Globals.KEY_FLOOR, mData.getFloor());
		values.put(Globals.KEY_BATHROOMQUALITY, mData.getBathroomQuality());
		values.put(Globals.KEY_EXPERIENCEQUALITY, mData.getExperienceQuality());
		values.put(Globals.KEY_LATITUDE, mData.getLocation().latitude);
		values.put(Globals.KEY_LONGITUDE, mData.getLocation().longitude);
		values.put(Globals.KEY_COMMENT, mData.getComment());

//		// get the content resolver, insert the ContentValues into HistoryProvider.
  		Uri uri = context.getContentResolver().insert(BathroomProvider.CONTENT_URI, values);
		
		//set current ExerciseEntry's id.
		mData.setId(Long.valueOf(uri.getLastPathSegment()));
		
		
		//return the current ExerciseEntry's id.
		//temp returns to get rid of errors temporarily
		return Long.valueOf(uri.getLastPathSegment());
	}

	// Delete a entry specified by the argument id.
	// Static method, more general.
	public static void deleteEntryInDB(Context context, long id) {
		//set the URI based on the id of the row we want to delete
		Uri uri = Uri.parse(BathroomProvider.CONTENT_URI + "/" + id);
		//call the delete function
		context.getContentResolver().delete(uri, null, null);
	}

	// Overloading class function to delete current entry.
	public void deleteEntryInDB(Context context) {
		deleteEntryInDB(context, getID());
	}	
	
	// *******************************************************//
	// Standard setters and getters. May have some conversions.

	
	public Date getDateTime() {
		return mData.getDateTime();
	}
	
	
	
	public String getComment() {
		return mData.getComment();
	}

	public void setDate(int year, int month, int day) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(mData.getDateTime());

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);

		cal = new GregorianCalendar(year, month, day, hour, minute, second);

		mData.setDateTime(cal.getTime());
	}

	public void setTime(int hour, int minute, int second) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(mData.getDateTime());

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		cal = new GregorianCalendar(year, month, day, hour, minute, second);

		mData.setDateTime(cal.getTime());
	}

	public void setDateTime(long timeInMS) {
		mData.setDateTime(new Date(timeInMS));
	}

	public void setDuration(int durationInSeconds) {
		mData.setDuration(durationInSeconds);
	}

	public void setComment(String comment) {
		mData.setComment(comment);
	}

	public void setID(long id) {
		mData.setId(Long.valueOf(id));
	}
	
	public void setBuilding(String name) {
		mData.setBuilding(name);
	}
	
	public void setFloor(int floor) {
		mData.setFloor(floor);
	}
	
	public void setExperienceQuality(int experience) {
		mData.setExperienceQuality(experience);
	}
	
	public void setBathroomQuality(double quality) {
		mData.setBathroomQuality(quality);
	}

	public long getID() {
		return mData.getId().longValue();
	}
	
	public int getDuration() {
		return mData.getDuration();
	}
	
	public double getBathroomQuality() {
		return mData.getBathroomQuality();
	}
	
	public int getExperienceQuality() {
		return mData.getExperienceQuality();
	}
	
	public int getFloor() {
		return mData.getFloor();
	}
	
	public String getBuilding() {
		return mData.getBuilding();
	}
	
	public LatLng getLocation() {
		return mData.getLocation();
	}
	
	public void setLocation(LatLng latlng) {
		mData.setLocation(latlng);
	}
	

}
