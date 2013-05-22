package edu.dartmouth.cs.tractable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = this;
		
		findViewById(R.id.start_relativelayout).setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 // send to start page (ManualInputActivity)
		    	 Intent i = new Intent(mContext, ManualInputActivity.class);
		    	 startActivity(i);
		     }      
		});
		
		findViewById(R.id.stats_relativelayout).setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 // send to stats page (StatsTabActivity)
		    	 Intent i = new Intent(mContext, StatsTabActivity.class);
		    	 startActivity(i);
		     }       
		});
	}


}
