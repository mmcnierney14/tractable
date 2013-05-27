package edu.dartmouth.cs.tractable;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

public class WaitingActivity extends Activity {
	
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);
		
		mContext = this;
		
		findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
		     @Override
		     public void onClick(View v) {
		    	 // send to stats page (StatsTabActivity)
		    	 Intent i = new Intent(mContext, ManualInputActivity.class);
		    	 startActivity(i);
		    	 finish();
		     }       
		});
	}

}
