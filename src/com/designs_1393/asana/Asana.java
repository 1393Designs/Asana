package com.designs_1393.asana;

import com.designs_1393.asana.workspace.*;

// General
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import android.view.View;

import android.support.v4.app.DialogFragment;

// Shared preferences
import android.content.SharedPreferences;

// Database
import android.database.Cursor;

// Widgets
import android.widget.ListView;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockExpandableListActivity;

import android.util.Log;

/**
 * Main application activity.
 */
public class Asana extends SherlockExpandableListActivity
{
	final String APP_TAG = "Asana";
	private SharedPreferences sharedPrefs;
	private DialogFragment apiKeyDialog;

	private Context ctx;
	private Cursor workspaceCursor;

	private DatabaseAdapter dbAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		sharedPrefs = getSharedPreferences("AsanaPrefs", Context.MODE_PRIVATE);

		if(sharedPrefs.getString( "api key", "not found" ).equals("not found"))
		{
			// create and show the dialog
			//apiKeyDialog = ApiKeyDialogFragment.newInstance(sharedPrefs);
			//apiKeyDialog.show( getFragmentManager(), "dialog" );
		}

		// store application context
		ctx = getApplicationContext();

		// get shared preferences containing API key
		sharedPrefs = getSharedPreferences(
			"AsanaPrefs",
			Context.MODE_PRIVATE);

		// get and store workspaces from Asana
		AsanaFacade aFacade = new AsanaFacade( sharedPrefs, ctx );
		aFacade.retreiveWorkspaces();
		aFacade.retreiveProjects();

		// set layout content from the cache database
		dbAdapter = new DatabaseAdapter( ctx );
		dbAdapter.open();

		workspaceCursor = dbAdapter.getWorkspaces( true );
		setListAdapter( new ExpandableWorkspaceAdapter( ctx, workspaceCursor ) );
		dbAdapter.close();
		setContentView( R.layout.workspace_list );
	}

	public void onListItemClick( ListView l, View v, int pos, long id )
	{
		Cursor tempCursor = workspaceCursor;
		tempCursor.moveToPosition( pos );

		String workspaceName =
			tempCursor.getString(
				tempCursor.getColumnIndexOrThrow(
					DatabaseAdapter.WORKSPACES_COL_NAME
				)
			);

		Log.i( APP_TAG, workspaceName +" pressed!" );
	}
}
