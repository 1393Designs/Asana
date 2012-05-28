package com.designs_1393.asana.workspace;

// Asana classes
import com.designs_1393.asana.*;
import com.designs_1393.asana.project.*;

// General
import android.os.Bundle;
import android.content.Context;
import android.view.View;

// Fragments
import android.support.v4.app.FragmentTransaction;

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
	private DatabaseAdapter dbAdapter;

	@Override
	public void onActivityCreated(Bundle savedState)
	{
		super.onActivityCreated(savedState);

		// store application context
		ctx = getActivity().getApplicationContext();

		// get shared preferences containing API key
		sharedPrefs = getActivity().getSharedPreferences(
			"AsanaPrefs",
			Context.MODE_PRIVATE);

		// get and store workspaces from Asana
		AsanaFacade aFacade = new AsanaFacade( sharedPrefs, ctx );
		aFacade.retreiveWorkspaces();

		// set layout content from the cache database
		dbAdapter = new DatabaseAdapter( ctx );
		dbAdapter.open();

		workspaceCursor = dbAdapter.getWorkspaces( true );
		setListAdapter( new WorkspaceAdapter( ctx, workspaceCursor ) );
		dbAdapter.close();
	}

	@Override
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

		long workspaceID =
			tempCursor.getLong(
				tempCursor.getColumnIndexOrThrow(
					DatabaseAdapter.WORKSPACES_COL_NAME
				)
			);

		Log.i( APP_TAG, workspaceName +" pressed!" );

		ProjectsFragment pf = ProjectsFragment.newInstance( workspaceID );
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove( this );
		ft.add( pf, "projects" );
		ft.commit();
	}
}
