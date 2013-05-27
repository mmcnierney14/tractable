package edu.dartmouth.cs.tractable.gae;

import org.json.JSONException;
import org.json.JSONObject;

public class HistoryEntry {
	public long id;
	public long dateTime;
	public int duration;
	public String building;
	public int floor;
	public double bathroomQuality;
	public int experienceQuality;
	public double latitude;
	public double longitude;
	public String comment;
	
	public JSONObject fromJSONObject(JSONObject obj) {		
		try {
			id = obj.getInt("id");
			dateTime = obj.getLong("dateTime" );
			duration = obj.getInt("duration");
			building = obj.getString("building");
			floor = obj.getInt("floor");
			bathroomQuality = obj.getDouble("bathroomQuality");
			experienceQuality = obj.getInt("experienceQuality");
			latitude = obj.getDouble("latitude");
			longitude = obj.getDouble("longitude");
			comment = obj.getString("comment");
		} catch (JSONException e) {
			return null;
		}
		return obj;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		
		try {
			obj.put("id", id);
			obj.put("dateTime",dateTime );
			obj.put("duration", duration);
			obj.put("building", building);
			obj.put("floor", floor);
			obj.put("bathroomQuality", bathroomQuality);
			obj.put("experienceQuality", experienceQuality);
			obj.put("latitude", latitude);
			obj.put("longitude", longitude);
			obj.put("comment", comment);
		} catch (JSONException e) {
			return null;
		}

		return obj;
	}
}
