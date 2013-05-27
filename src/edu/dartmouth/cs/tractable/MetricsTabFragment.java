package edu.dartmouth.cs.tractable;

import org.achartengine.model.XYSeries;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MetricsTabFragment extends Fragment {

	// The callbacks through which we will interact with the LoaderManager.
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
	public Context mContext; // context pointed to parent activity

	public Cursor mBathroomSessionCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		// initialize the mActivityEntryCursor
		mBathroomSessionCursor = mContext.getContentResolver().query(
				BathroomProvider.CONTENT_URI, null, null, null, null);
		mBathroomSessionCursor.moveToFirst();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.metrics, container, false);

		String av_experience;
		String num_sessions;
		// get metrics
		if (mBathroomSessionCursor.getCount() != 0) {
			av_experience = String
					.valueOf(get_av_experience(mBathroomSessionCursor)); 
			num_sessions = String.valueOf(get_total_sessions(mBathroomSessionCursor));
			// if no entries have been logged, av = none

		} else {
			av_experience = "None Logged Yet!";
			num_sessions = "0";
		}
		// set the average experience value text in xml
		TextView experience_view = (TextView) view
				.findViewById(R.id.av_experience_text);
		experience_view.setText(av_experience);
		TextView total_sessions_view = (TextView) view
				.findViewById(R.id.num_sessions_txt);
		total_sessions_view.setText(num_sessions);

		return view;

	}

	// function to get the average experience of all saved bathroom sessions
	public double get_av_experience(Cursor cursor) {

		double av_experience = 0;

		// move cursor to beginning of set
		cursor.moveToFirst();

		// add up all user experience values
		do {
			av_experience += cursor.getInt(mBathroomSessionCursor
					.getColumnIndex(Globals.KEY_EXPERIENCEQUALITY));
		} while (cursor.moveToNext());

		// divide by the total number of bathroom sets
		int num_entries = cursor.getCount();
		av_experience = av_experience / num_entries;

		return av_experience;
	}

	// function to get the number of bathroom sessions
	public int get_total_sessions(Cursor cursor) {
		return cursor.getCount();
	}
}
