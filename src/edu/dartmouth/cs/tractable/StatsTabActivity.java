package edu.dartmouth.cs.tractable;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;


public class StatsTabActivity extends Activity {

private static final String TAB_INDEX_KEY = "tab_index";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Action Bar
		ActionBar actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// create tabs for start, history, and settings
		String graphsStr = getString(R.string.stats_tab_graphs_title);
		String historyStr = getString(R.string.stats_tab_history_title);
		String metricsStr = getString(R.string.stats_tab_metrics_title);

		ActionBar.Tab mGraphsTab = actionbar.newTab().setText(graphsStr);		
		ActionBar.Tab mHistoryTab = actionbar.newTab().setText(historyStr);
		ActionBar.Tab mMetricsTab = actionbar.newTab().setText(metricsStr);		
		
		// bind the fragments to their tabs and add tabs to action bar
		mGraphsTab.setTabListener(new TabListener<GraphsTabFragment>(this, graphsStr, GraphsTabFragment.class));
		mHistoryTab.setTabListener(new TabListener<HistoryTabFragment>(this, historyStr, HistoryTabFragment.class));
		mMetricsTab.setTabListener(new TabListener<MetricsTabFragment>(this, metricsStr, MetricsTabFragment.class));
		actionbar.addTab(mGraphsTab);
		actionbar.addTab(mHistoryTab);
		actionbar.addTab(mMetricsTab);

		
		// remember the tab we were on (mainly for orientation changes)
		if (savedInstanceState != null) {
			actionbar.setSelectedNavigationItem(savedInstanceState.getInt(TAB_INDEX_KEY));
		}		
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		// save the current tab (for orientation changes)
		outState.putInt(TAB_INDEX_KEY, this.getActionBar().getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats_tab, menu);
		return true;
	}
	

public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
	private Fragment mFragment;
	private final Activity mActivity;
	private final String mTag;
	private final Class<T> mClass;
	
	public TabListener(Activity activity, String tag, Class<T> clz) {
		mActivity = activity;
		mTag = tag;
		mClass = clz;
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		// if the fragment doesn't exist, create it
		if (mFragment == null) {
			mFragment = Fragment.instantiate(mActivity, mClass.getName());
		}
		
		// replace whatever is the in the content with the fragment
		ft.replace(android.R.id.content, mFragment, mTag);
	}
	
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(mFragment);
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

}

}


