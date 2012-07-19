package com.designs_1393.asana.task;

import com.designs_1393.asana.*;

// General
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;

// Database
import android.database.Cursor;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockListActivity;

import android.util.Log;

public class TaskActivity extends SherlockListActivity
{
	final String APP_TAG = "Asana.TaskActivity";

	private SharedPreferences sharedPrefs;
	private Context ctx;
	private Cursor taskCursor;

	private DatabaseAdapter dbAdapter;

	private AsanaFacade aFacade;

	private long projectID = 0;
	private String windowTitle = "Tasks";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();

		if( extras != null )
		{
			projectID = extras.getLong( "projectID" );
			windowTitle = extras.getString( "projectName" );
		}

		getSupportActionBar().setTitle( windowTitle );

		// store application context
		ctx = getApplicationContext();

		sharedPrefs = getSharedPreferences("AsanaPrefs", Context.MODE_PRIVATE);

		aFacade = new AsanaFacade( sharedPrefs, ctx );

		dbAdapter = new DatabaseAdapter( ctx );

		aFacade.retreiveTasks( projectID );

		taskCursor = dbAdapter.getTasks( projectID );
		if( taskCursor.getCount() == 0 )
			Log.i( APP_TAG, "Cursor is empty" );

		setContentView( R.layout.task_list );

		startManagingCursor( taskCursor );

		setListAdapter( new TaskAdapter( ctx, taskCursor ) );
	}
}
