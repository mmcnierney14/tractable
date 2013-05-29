package edu.dartmouth.cs.tractable;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class TimerActivity extends Activity {

	private Context mContext;
	private int duration;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer);
		duration = 0;
		mContext = this;
		
		// array of fun facts to display
		String[] funfacts = new String[10];
		funfacts[0] = "According to the US News and World Report, during the first week of daylight saving time, there is a 5% increase in heart-attack incidents (due to people not getting enough sleep). ";
		funfacts[1] = "If you sneeze too hard, you could fracture a rib.";
		funfacts[2] = "Almonds are a member of the peach family.";
		funfacts[3] = "About 75% of human waste is made up of water. The rest consists of undigested plant fibers and germs.";
		funfacts[4] = "Women blink nearly twice as much as men.   ";
		funfacts[5] = "The air released from a sneeze can exceed 100 mph.";
		funfacts[6] = "Food stays in your stomach for 2 to 4 hours.";
		funfacts[7] = "The human skull is 80% water.";
		funfacts[8] = "It might only take you a few minutes to finish a meal but it takes your body around 12 hours before it has completely digested the food.";
		funfacts[9] = "The eye of a human can distinguish between 500 shades of gray.";
		
		Random generator = new Random();
		
		TextView funfact_view = (TextView) findViewById(R.id.funfact);
		int i = generator.nextInt(10);
		funfact_view.setText(funfacts[i]);
		
		//Declare the timer
		final Timer t = new Timer();
		//Set the schedule function and rate
		t.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		        //Called each time when 1000 milliseconds (1 second) (the period parameter)
		    	//We must use this function in order to change the text view text
		    	runOnUiThread(new Runnable() {

		    	    @Override
		    	    public void run() {
		    	        TextView tv = (TextView) findViewById(R.id.timer);
		    	        tv.setText(String.valueOf(duration));
		    	        duration += 1;
		    	    }
		    	     
		    	});
		    }
		         
		},
		//Set how long before to start calling the TimerTask (in milliseconds)
		0,
		//Set the amount of time between each execution (in milliseconds)
		1000);
		
		
		findViewById(R.id.to_manual_input).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// send to stats page (StatsTabActivity)
				t.cancel();
				Log.e("tractable", String.valueOf(duration));
				
				Intent i = new Intent(mContext, ManualInputActivity.class);
				i.putExtra(Globals.KEY_DURATION, duration);
				startActivity(i);
			}       
		});
	}


}
