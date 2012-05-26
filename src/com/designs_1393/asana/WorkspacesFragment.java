package com.designs_1393.asana;

// General
import android.os.Bundle;
import android.content.Context;
import android.view.View;

// Shared preferences
import android.content.SharedPreferences;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockListFragment;

// Widgets
import android.widget.ListView;

// Database
import android.database.Cursor;

// Logging
import android.util.Log;

// Jackson JSON
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;

public class WorkspacesFragment extends SherlockListFragment
{
	boolean dualPane;

	private String APP_TAG = "Asana.WorkspacesFragment";

	private Context ctx;
	private Cursor workspaceCursor;

	private SharedPreferences sharedPrefs;

	@Override
	public void onActivityCreated(Bundle savedState)
	{
		super.onActivityCreated(savedState);

		ctx = getActivity().getApplicationContext();

		DatabaseAdapter dbAdapter = new DatabaseAdapter( ctx );
		dbAdapter.open();
		workspaceCursor = dbAdapter.getWorkspaces( true );
		setListAdapter( new WorkspaceAdapter( ctx, workspaceCursor ) );

		sharedPrefs = getActivity().getSharedPreferences("AsanaPrefs", Context.MODE_PRIVATE);

		/* Sample API call - gets workspaces. */
		AsanaHelper ah = new AsanaHelper( sharedPrefs.getString("api key", "No API key.") );
		ah.usePrettyPrint( true );

		String workspacesJSON = ah.getWorkspaces();

		Log.i(APP_TAG, "Starting deserialization");
		ObjectMapper mapper = new ObjectMapper();

		try{
			WorkspaceSet workspaces = mapper.readValue( workspacesJSON, WorkspaceSet.class );

			Workspace[] wArray = workspaces.getData();

			for( int i = 0; i < wArray.length; i++ )
			{
				Log.i(APP_TAG, "\tWorkspace ID = " +wArray[i].getID());
				Log.i(APP_TAG, "\tWorkspace name = " +wArray[i].getName());
			}

			boolean successfulSet = dbAdapter.setWorkspaces( workspaces );
			dbAdapter.close();

			if( successfulSet )
				Log.i( APP_TAG, "DB set successful!" );
			else
				Log.i( APP_TAG, "DB set failed. :(" );
		} catch( Exception e) { e.printStackTrace(); }
		Log.i(APP_TAG, "Finished deserialization");

		Log.i( APP_TAG, "Fragment loaded!" );
	}

	@Override
	public void onListItemClick( ListView l, View v, int pos, long id )
	{
		Log.i( APP_TAG, "Item at position " +pos +" pressed!" );
	}
}
