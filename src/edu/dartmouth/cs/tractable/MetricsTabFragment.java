package edu.dartmouth.cs.tractable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
		String fave_bathroom = "";
		String most_used_bathroom = "";

		double total_meals = 0;
		double total_sleep = 0;
		double sleep = 0;
		double meals = 0;
		// get metrics
		if (mBathroomSessionCursor.getCount() != 0) {
			av_experience = String
					.valueOf(get_av_experience(mBathroomSessionCursor)); 
			num_sessions = String.valueOf(get_total_sessions(mBathroomSessionCursor));
			fave_bathroom = get_fave_bathroom(mBathroomSessionCursor);
			//most_used_bathroom = get_most_used_bathroom(mBathroomSessionCursor);
			// if no entries have been logged, av = none
			try {
				total_meals = readMealFile("meals.txt");
				total_sleep = readSleepFile("sleep.txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			meals = total_meals;

		} else {
			av_experience = "None Logged Yet!";
			num_sessions = "0";
			fave_bathroom = "None Logged Yet!";
			most_used_bathroom = "None Logged Yet!";
		}
		DecimalFormat df = new DecimalFormat("0.00##");
		String result_meals = df.format(meals);
		String result_sleep = df.format(total_sleep);
		
		// set the average experience value text in xml
		TextView experience_view = (TextView) view
				.findViewById(R.id.av_experience_text);
		experience_view.setText(av_experience);
		
		// set the total number of sessions value text in xml
		TextView total_sessions_view = (TextView) view
				.findViewById(R.id.num_sessions_txt);
		total_sessions_view.setText(num_sessions);
		
		// set the average number of meals value text in xml
		TextView average_meals = (TextView) view
				.findViewById(R.id.av_number_meals_text);
		average_meals.setText(result_meals);
		
		// set the average hours of sleep value text in xml
		TextView average_sleep = (TextView) view
				.findViewById(R.id.av_hours_sleep_text);
		average_sleep.setText(result_sleep);
		
		// set the favorite bathroom text in xml
//		TextView favorite_bathroom = (TextView) view
//				.findViewById(R.id.favorite_bathroom_text);
//		favorite_bathroom.setText(fave_bathroom);
//		
//		// set the most used bathroom text in xml
//		TextView frequency_bathroom = (TextView) view
//				.findViewById(R.id.most_used_bathroom_text);
//		frequency_bathroom.setText(most_used_bathroom);
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
	
	
	// function to get the most used bathroom
	public String get_fave_bathroom(Cursor cursor) {
		// move cursor to beginning of set
		cursor.moveToFirst();
		
		String result = "";

		String favorite = " ";
		int index = mBathroomSessionCursor.getColumnIndex(Globals.KEY_BUILDING);
		
		// find max used bathroom string
		do {
			int count = 1, tempCount;
			String temp = "";
			
	        for (int i = 1; i < cursor.getCount(); i++) {
	        	tempCount = 0;
	        	for (int j = 1; j < cursor.getCount(); j++) {
	        		cursor.moveToPosition(j);
	        		String current = cursor.getString(index);
	        		if (temp.equalsIgnoreCase(current)) {
	        			tempCount ++;
	        		}
	        	}
	        	cursor.moveToPosition(i);
	        	if (tempCount > count) {
	        		favorite = temp;
	        		count = tempCount;
	        	}
	        }

		} while (cursor.moveToNext());
		
		result = favorite;
		
		return result;
	}

	// function to get the number of bathroom sessions
	public int get_total_sessions(Cursor cursor) {
		return cursor.getCount();
	}
	
	// read in sleep info for use in metrics/graphs
		private double readSleepFile(String filename) throws FileNotFoundException, IOException {
			ArrayList<Double> sleep_lines = new ArrayList<Double>();
			
			InputStream is = mContext.getAssets().open(filename);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String [] parts = line.split(",");
				sleep_lines.add(Double.valueOf(parts[1]));
			}
			reader.close();
			
			double result = 0;
			for (int i = 0; i < sleep_lines.size(); i++) {
				result += sleep_lines.get(i);
			}
			result /= sleep_lines.size();
			
			return result;
		}
	
	// read in meal info for use in metrics/graphs
		private double readMealFile(String filename) throws FileNotFoundException, IOException {
			ArrayList<Double> meal_lines = new ArrayList<Double>();
			
			InputStream is = mContext.getAssets().open(filename);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String [] parts = line.split(" ");
				meal_lines.add(Double.valueOf(parts[0]));
			}
			reader.close();
			
			double result = 0;
			for (int i = 0; i < meal_lines.size(); i++) {
				result += (meal_lines.get(i) / 24); //24 users in the study
			}
			result /= meal_lines.size();
			return result;
		}
}
