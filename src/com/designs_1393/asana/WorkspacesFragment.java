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

		AsanaFacade aFacade = new AsanaFacade( sharedPrefs, ctx );
		aFacade.retreiveWorkspaces();
	}

	@Override
	public void onListItemClick( ListView l, View v, int pos, long id )
	{
		Log.i( APP_TAG, "Item at position " +pos +" pressed!" );
	}
}
