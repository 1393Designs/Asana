package com.designs_1393.asana;

import com.designs_1393.asana.workspace.*;

// General
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;

// Fragments
import android.support.v4.app.DialogFragment;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockFragmentActivity;

/**
 * Main application activity.
 * Because the application uses Fragments, this will probably stay fairly
 * minimal.
 */
public class Asana extends SherlockFragmentActivity
{
	final String APP_TAG = "Asana";
	private SharedPreferences sharedPrefs;
	private DialogFragment apiKeyDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		sharedPrefs = getSharedPreferences("AsanaPrefs", Context.MODE_PRIVATE);

		if(sharedPrefs.getString( "api key", "not found" ).equals("not found"))
		{
			// create and show the dialog
			apiKeyDialog = ApiKeyDialogFragment.newInstance(sharedPrefs);
			apiKeyDialog.show( getSupportFragmentManager(), "dialog" );
		}

		setContentView( R.layout.workspace_list );
	}
}
