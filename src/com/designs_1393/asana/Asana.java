package com.designs_1393.asana;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;

/**
 * Main application activity.
 * Because the application uses Fragments, this will probably stay fairly
 * minimal.
 */
public class Asana extends SherlockFragmentActivity
{
	final String APP_TAG = "Asana";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView( R.layout.workspace_list );
	}

}
