package edu.dartmouth.cs.tractable;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.ActionBar.LayoutParams;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GraphsTabFragment extends Fragment {
	// charting variables
	private GraphicalView mChart;
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYSeries mCurrentSeries;
    private XYSeriesRenderer mCurrentRenderer;
    
	
	// The callbacks through which we will interact with the LoaderManager.
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
	public Context mContext; // context pointed to parent activity

	public Cursor mBathroomSessionCursor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = getActivity();
		//initialize the mActivityEntryCursor
		mBathroomSessionCursor = mContext.getContentResolver().query(BathroomProvider.CONTENT_URI,
				null, null, null, null);
		mBathroomSessionCursor.moveToFirst();
		
		
		
	
		// empty, need to figure out what goes in here
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.graphs, container, false);
	
		
		String av_experience;
		// get the average experience value
		if (mBathroomSessionCursor.getCount() != 0) {
			//av_experience = String.valueOf(get_av_experience(mBathroomSessionCursor)); // if no entries have been logged, av = none

			mCurrentSeries = experience_data(mBathroomSessionCursor);
		}
		else {
			av_experience = "None Logged Yet!";
			mCurrentSeries = new XYSeries("NOTHING LOGGED YET");
			mCurrentSeries.add(1, 0);
		}
		

		
		mDataset.addSeries(mCurrentSeries);
		mCurrentRenderer = new XYSeriesRenderer();
		mCurrentRenderer.setLineWidth(0);
		mCurrentRenderer.setColor(Color.WHITE);
		mCurrentRenderer.setFillPoints(true);
		mCurrentRenderer.setPointStyle(PointStyle.CIRCLE);
		mRenderer.addSeriesRenderer(mCurrentRenderer);
		
		mRenderer.setMargins(new int[] {100, 75, 10, 50});
		mRenderer.setYAxisMin(0, 0);
		mRenderer.setYAxisMax(12, 0);
		mRenderer.setXTitle("Time");
		mRenderer.setYTitle("Experience");
		mRenderer.setAxisTitleTextSize(40);
		mRenderer.setChartTitleTextSize(12);
		mRenderer.setLabelsTextSize(10);
		mRenderer.setLegendTextSize(12);
		mRenderer.setPointSize(5f);
		mRenderer.setXLabels(12);
		mRenderer.setYLabels(12);
		mRenderer.setXLabelsAlign(Align.RIGHT);
		mRenderer.setYLabelsAlign(Align.RIGHT);
		mRenderer.setPanLimits(new double[] { -10, 20, -10, 40 });
		mRenderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.parseColor("#333333"));

        mChart = ChartFactory.getCubeLineChartView(getActivity(), mDataset, mRenderer, 0.3f);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.chart);
        layout.addView(mChart);

		return view;
	}
	
	// Create a new CursorLoader with the following query parameters.
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		final String[] projection = new String[] {
				Globals.KEY_ROWID,
				Globals.KEY_DATE_TIME,
				Globals.KEY_LATITUDE,
				Globals.KEY_LONGITUDE,
				Globals.KEY_BUILDING,
				Globals.KEY_FLOOR,
				Globals.KEY_BATHROOMQUALITY,
				Globals.KEY_EXPERIENCEQUALITY,
				Globals.KEY_COMMENT,

		};

		return new CursorLoader(mContext, BathroomProvider.CONTENT_URI,
				projection, null, null, null);
	}
	
	// function to plot the points of the overall experience chart
	public XYSeries experience_data(Cursor cursor) {
		XYSeries result = new XYSeries("");
		
		int i = 1;
		cursor.moveToFirst();
		do {
			result.add(i, cursor.getInt(mBathroomSessionCursor.getColumnIndex(Globals.KEY_EXPERIENCEQUALITY)));
		}
		while (cursor.moveToNext());
		
		return result;
		
	}
	
	
	
	
	// METHODS USED FOR GRAPHING
	
	
}
