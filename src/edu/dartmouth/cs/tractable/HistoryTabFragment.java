package edu.dartmouth.cs.tractable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.dartmouth.cs.tractable.gae.HistoryUploader;

public class HistoryTabFragment extends ListFragment implements 
LoaderManager.LoaderCallbacks<Cursor> {

	// The loader's unique id. Loader ids are specific to the Activity or
	// Fragment in which they reside.
	private static final int LOADER_ID = 0;

	// The callbacks through which we will interact with the LoaderManager.
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

	public Context mContext; // context pointed to parent activity
	public BathroomSessionsCursorAdapter mAdapter; // customized adapter for displaying

	public Cursor mBathroomSessionCursor;

	public int mRowIdIndex;
	public int mTimeIndex;
	public int mCommentIndex;
	public int mBuildingIndex;
	public int mFloorIndex;
	public int mBathroomQualityIndex;
	public int mExperienceQualityIndex;
	public int mLatitudeIndex;
	public int mLongitudeIndex;
	public int mDurationIndex;

	// Different format to display the information
	public static final String DATE_FORMAT = "H:mm:ss MMM d yyyy";
	public static final String MINUTES_FORMAT = "%d minutes";
	public static final String SECONDS_FORMAT = "%d seconds";
	
	private Button mButtonSync;

	private HistoryUploader mHistoryUploader;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		//initialize the mActivityEntryCursor
		mBathroomSessionCursor = mContext.getContentResolver().query(BathroomProvider.CONTENT_URI,
				null, null, null, null);
		mBathroomSessionCursor.moveToFirst();

		// Read the column indices of the database table
		mRowIdIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_ROWID);
		mTimeIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_DATE_TIME);
		mCommentIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_COMMENT);
		mBuildingIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_BUILDING);
		mFloorIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_FLOOR);
		mBathroomQualityIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_BATHROOMQUALITY);
		mExperienceQualityIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_EXPERIENCEQUALITY);
		mLatitudeIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_LATITUDE);
		mLongitudeIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_LONGITUDE);
		mDurationIndex = mBathroomSessionCursor.getColumnIndex(Globals.KEY_DURATION);

		mCallbacks = this;

		// Initialize the Loader with id "0" and callbacks "mCallbacks".
		LoaderManager lm = getLoaderManager();
		lm.initLoader(LOADER_ID, null, mCallbacks);


		// Set the mAdapter to show the list.
		mAdapter = new BathroomSessionsCursorAdapter(mContext, mBathroomSessionCursor);
		setListAdapter(mAdapter);
		
		// Initialize uploader
		String serverUrl = Globals.SERVER_URL + "/post_data";
		mHistoryUploader = new HistoryUploader(mContext, serverUrl);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.history, container, false);
		
		mButtonSync = (Button) view.findViewById(R.id.btnSync);
		
		mButtonSync.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onSyncClicked(v);
			}
		});
		return view;
	}

	public void onSyncClicked(View view) {

		new AsyncTask<Void, Void, String>() {

			@Override
			// Get history and upload it to the server. 
			protected String doInBackground(Void... arg0) {
				// Query to access the database.
				//Cursor c = mContext.getContentResolver().query(BathroomProvider.CONTENT_URI, null, null, null, null);

				String uploadState = "";
				// Upload the history of all entries using upload(). 

				try {
					mHistoryUploader.upload(mBathroomSessionCursor);
					Log.i(Globals.TAG, "uploaded");
				} catch (IOException e1) {
					uploadState = "Sync failed: " + e1.getCause();
					Log.e(Globals.TAG, "data posting error " + e1);
				}


				return uploadState;
			}

			@Override
			protected void onPostExecute(String errString) {
				String resultString;
				if(errString.equals("")) {
					resultString =  "All entries uploaded.";
				} else {
					resultString = errString;
				}

				// Making a "toast" informing the user that the upload succeeded.
				Toast.makeText(mContext,
						resultString,
						Toast.LENGTH_SHORT).show();
			}

		}.execute();

	}


	// Handler for the Click event on the entries in history.
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l,v,position,id);

		// The intent to launch the activity after click.
		Intent intent = new Intent(mContext, DisplaySessionActivity.class); 

		// The extra information needed pass through to next activity.
		Bundle extras = new Bundle();

		// go to whatever row we click on
		mBathroomSessionCursor.moveToPosition(position);

		// Write row id into extras.
		extras.putLong(Globals.KEY_ROWID, id);

		// write date/time into extras
		String s = parseTime(mBathroomSessionCursor.getLong(mTimeIndex));
		extras.putString(Globals.KEY_DATE_TIME, s);

		// write comment into extras
		s = mBathroomSessionCursor.getString(mCommentIndex);
		extras.putString(Globals.KEY_COMMENT, s);

		s = mBathroomSessionCursor.getString(mBuildingIndex);
		extras.putString(Globals.KEY_BUILDING, s);

		int i = mBathroomSessionCursor.getInt(mFloorIndex);
		extras.putInt(Globals.KEY_FLOOR, i);

		i = mBathroomSessionCursor.getInt(mBathroomQualityIndex);
		extras.putDouble(Globals.KEY_BATHROOMQUALITY, i);

		i = mBathroomSessionCursor.getInt(mExperienceQualityIndex);
		extras.putInt(Globals.KEY_EXPERIENCEQUALITY, i);
		
		i = mBathroomSessionCursor.getInt(mDurationIndex);
		extras.putInt(Globals.KEY_DURATION, i);
		
		double d = mBathroomSessionCursor.getDouble(mLatitudeIndex);
		extras.putDouble(Globals.KEY_LATITUDE, d);
		
		d = mBathroomSessionCursor.getDouble(mLongitudeIndex);
		extras.putDouble(Globals.KEY_LONGITUDE, d);
		


		// put the extras in and start the activity
		intent.putExtras(extras);
		startActivity(intent);
	}

	// Create a new CursorLoader with the following query parameters.
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		final String[] projection = new String[] {
				Globals.KEY_ROWID,
				Globals.KEY_DATE_TIME,
				Globals.KEY_DURATION,
				Globals.KEY_BUILDING,
				Globals.KEY_FLOOR,
				Globals.KEY_BATHROOMQUALITY,
				Globals.KEY_EXPERIENCEQUALITY,
				Globals.KEY_LATITUDE,
				Globals.KEY_LONGITUDE,
				Globals.KEY_COMMENT,

		};

		return new CursorLoader(mContext, BathroomProvider.CONTENT_URI,
				projection, null, null, null);
	}

	// When the load finished, swap the cursor for the cursor adapter.
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
	}

	// For whatever reason, the Loader's data is now unavailable.
	// Remove any references to the old data by replacing it with
	// a null Cursor.
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	//ActivityEntriesCursorAdapter is specialized adapter for a list view, 
	//when the data comes from database. 

	private class BathroomSessionsCursorAdapter extends CursorAdapter {

		private LayoutInflater mInflater;

		public BathroomSessionsCursorAdapter(Context context, Cursor c) {
			super(context, c, FLAG_REGISTER_CONTENT_OBSERVER);
			mInflater = LayoutInflater.from(context);
		}

		
		@Override
		public void bindView(View view, Context context, Cursor cursor) {

			// create two text views to go in each listView line
			TextView topLine = (TextView) view.findViewById(android.R.id.text1);
			TextView botLine = (TextView) view.findViewById(android.R.id.text2);

			// get their strings
			String botText = "Experience: " + cursor.getInt(mExperienceQualityIndex);
			botText += "	Bathroom: " + cursor.getInt(mBathroomQualityIndex);

			String topText = parseTime(cursor.getLong(mTimeIndex));


			// set the text
			topLine.setText(topText);
			botLine.setText(botText);


		}


		// When the view will be created for first time,
		// we need to tell the adapters how each item will look
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return mInflater.inflate(android.R.layout.two_line_list_item, null);
		}
	}


	// From 1970 epoch time in seconds to something like "10/24/2012"
	private String parseTime(long timeInMillis) {

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timeInMillis);
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.format(calendar.getTime());
	}




}
