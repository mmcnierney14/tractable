package edu.dartmouth.cs.tractable.gae;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.database.Cursor;

import com.google.android.gcm.GCMRegistrar;

import edu.dartmouth.cs.tractable.Globals;

public class HistoryUploader {

	private Context mContext;
	private String mServerUrl;

	public HistoryUploader(Context context, String serverUrl) {
		mContext = context;
		mServerUrl = serverUrl;
	}

	public boolean upload(Cursor cursor) throws IOException {
		if (!cursor.moveToFirst()) {
			return false;
		}

		// convert entrys to string
		ArrayList<HistoryEntry> entryList = new ArrayList<HistoryEntry>();
		do {
			HistoryEntry entry  = getEntryFromCursor(cursor);
			if(entry != null) {
				entryList.add(entry);
			}
		} while (cursor.moveToNext());

		String entryListString = convertToString(entryList);

		// upload
		final String regId = GCMRegistrar.getRegistrationId(mContext);

		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		params.put("data", entryListString);
		ServerUtilities.post(mServerUrl, params);

		return true;
	}

	private String convertToString(ArrayList<HistoryEntry> entryList) {
		
		JSONArray jsonArray = new JSONArray();
		for(HistoryEntry entry:entryList) {
			jsonArray.put(entry.toJSONObject());
		}
		String entryListStr = jsonArray.toString();

//		HistoryEntry entry0 = new HistoryEntry();
//		try {
//			JSONArray testArray = new JSONArray(entryListStr);
//			JSONObject object = testArray.getJSONObject(0);
//
//			entry0.fromJSONObject(object);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return entryListStr;
	}

	private  HistoryEntry getEntryFromCursor(Cursor cursor) {
		int mRowIdIndex = cursor.getColumnIndex(Globals.KEY_ROWID);
		int mTimeIndex = cursor.getColumnIndex(Globals.KEY_DATE_TIME);
		int mCommentIndex = cursor.getColumnIndex(Globals.KEY_COMMENT);
		int mBuildingIndex = cursor.getColumnIndex(Globals.KEY_BUILDING);
		int mFloorIndex = cursor.getColumnIndex(Globals.KEY_FLOOR);
		int mBathroomQualityIndex = cursor.getColumnIndex(Globals.KEY_BATHROOMQUALITY);
		int mExperienceQualityIndex = cursor.getColumnIndex(Globals.KEY_EXPERIENCEQUALITY);
		int mLatitudeIndex = cursor.getColumnIndex(Globals.KEY_LATITUDE);
		int mLongitudeIndex = cursor.getColumnIndex(Globals.KEY_LONGITUDE);
		int mDurationIndex = cursor.getColumnIndex(Globals.KEY_DURATION);

		HistoryEntry entry = new HistoryEntry();

		if (mRowIdIndex != -1)
			entry.id = cursor.getInt(mRowIdIndex);
		if (mTimeIndex != -1)
			entry.dateTime = cursor.getLong(mTimeIndex);
		if (mDurationIndex != -1)
			entry.duration =  cursor.getInt(mDurationIndex);
		if (mBuildingIndex != -1)
			entry.building = cursor.getString(mBuildingIndex);
		if (mFloorIndex != -1)
			entry.floor = cursor.getInt(mFloorIndex);
		if (mBathroomQualityIndex != -1)
			entry.bathroomQuality = cursor.getDouble(mBathroomQualityIndex);
		if (mExperienceQualityIndex != -1)
			entry.experienceQuality = cursor.getInt(mExperienceQualityIndex);
		if (mLatitudeIndex != -1)
			entry.latitude = cursor.getDouble(mLatitudeIndex);
		if (mLongitudeIndex != -1)
			entry.longitude = cursor.getDouble(mLongitudeIndex);
		if (mCommentIndex != -1)
			entry.comment = cursor.getString(mCommentIndex);

		
		return entry;
	}

}
