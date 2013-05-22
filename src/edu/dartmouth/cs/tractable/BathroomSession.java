package edu.dartmouth.cs.tractable;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;


// Create the ExerciseEntry class. This class is our model and contains the data we will save in the database and
// show in the user interface.


// Definition of a exercise entry
// Member variables are accessed through setters and getters
// And it's actually not directly exposed for use, see ExerciseEntryHelper.java

public class BathroomSession {
	
	private Long id;
	
	private long remoteId;
	private Date dateTime;
	private int duration;
	private String comment;
	private String building;
	private int floor;
	private double bathroomQuality;
	private int experienceQuality;
	private LatLng latlng;
	
	//building, floor, quality of bathroom (1-10), quality of experience (1-10), will infer location, time, date, duration
	
	public BathroomSession(){
		this.remoteId = -1L;
		this.dateTime = new Date(System.currentTimeMillis());
		this.duration = 0;
		this.comment = "";
		this.building = "";
		this.floor = 0;
		this.bathroomQuality = 0;
		this.experienceQuality = 0;
		this.latlng = new LatLng(0, 0);
	}

	public String getBuilding() {
		return building;
	}
	
	public void setBuilding(String new_building) {
		this.building = new_building;
	}
	
	public int getFloor() {
		return floor;
	}
	
	public void setFloor(int new_floor) {
		this.floor = new_floor;
	}
	
	public double getBathroomQuality() {
		return bathroomQuality;
	}
	
	public void setBathroomQuality(double new_bathroomquality){
		this.bathroomQuality = new_bathroomquality;
	}
	
	public int getExperienceQuality() {
		return experienceQuality;
	}
	
	public void setExperienceQuality(int new_expquality) {
		this.experienceQuality = new_expquality;
	}
	
	public LatLng getLocation() {
		return latlng;
	}
	
	public void setLocation(LatLng latlng) {
		this.latlng = latlng;
	}
	
	public long getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(long remoteId) {
		this.remoteId = remoteId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
}

