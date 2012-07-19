package com.designs_1393.asana;

import com.designs_1393.asana.workspace.*;
import com.designs_1393.asana.task.*;

// General
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import android.support.v4.app.DialogFragment;

// Shared preferences
import android.content.SharedPreferences;

// Database
import android.database.Cursor;

// Widgets
import android.widget.ListView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.SpinnerAdapter;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockActivity;

// View
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

// Links
import android.text.util.Linkify;

// Dialog
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

// ActionBar Navigation
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;

import android.util.Log;

/**
 * Main application activity.
 */
public class Asana extends SherlockActivity
{
	final String APP_TAG = "Asana";
	private SharedPreferences sharedPrefs;
	private DialogFragment apiKeyDialog;

	private Context ctx;
	private Cursor workspaceCursor;

	private ExpandableWorkspaceAdapter workspaceAdapter;
	private ExpandableListView workspaceList;

	private DatabaseAdapter dbAdapter;


	private void showDialog()
	{
		View v = getLayoutInflater()
		         .inflate(R.layout.apikeydialog, null);
		TextView instructions =
			(TextView) v.findViewById( R.id.apiKeyInstructions );

		final EditText apiKeyInput =
			(EditText) v.findViewById( R.id.apiKeyEntry );

		// apply links to instructions
		Linkify.addLinks( instructions, Linkify.WEB_URLS );

		new AlertDialog.Builder( this )
			.setView( v )
			.setTitle( "API Key" )
			.setCancelable( true )
			.setPositiveButton( "Okay", new DialogInterface.OnClickListener() {
					public void onClick( DialogInterface dialog, int which )
					{
						SharedPreferences.Editor editor = sharedPrefs.edit();
						if( editor == null )
						{
							Log.i(APP_TAG, "Editor is null");
						}
						editor.putString(
							"api key",
							apiKeyInput.getText().toString());
						editor.commit();
					}
				})
			.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
				public void onClick( DialogInterface dialog, int which ){}
			}).create().show();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// store application context
		ctx = getApplicationContext();

		// get shared preferences containing API key
		sharedPrefs = getSharedPreferences("AsanaPrefs", Context.MODE_PRIVATE);

		final AsanaFacade aFacade = new AsanaFacade( sharedPrefs, ctx );

		if(sharedPrefs.getString( "api key", "not found" ).equals("not found"))
		{
			Log.i( APP_TAG, "----- SHOWING API DIALOG -----" );
			showDialog();
		}
		else
		{
			aFacade.retreiveWorkspaces();
			aFacade.retreiveProjects();
		}


		// get and store workspaces from Asana


		// set layout content from the cache database
		dbAdapter = new DatabaseAdapter( ctx );

		workspaceCursor = dbAdapter.getWorkspaces( true );

		setContentView( R.layout.workspace_list );

		final ExpandableListView elv = (ExpandableListView)findViewById(R.id.workspace_list);
		workspaceList = elv;
		workspaceAdapter = new ExpandableWorkspaceAdapter(ctx, workspaceCursor); // setAdapter

		elv.setAdapter( workspaceAdapter );

		elv.setOnChildClickListener( new OnChildClickListener() {
			public boolean onChildClick( ExpandableListView parent,
										 View v,
										 int groupPosition,
										 int childPosition,
										 long id )
			{
				Cursor childrenCursor = ((ExpandableWorkspaceAdapter)elv.getExpandableListAdapter())
											.getChildrenCursor( workspaceCursor );
				childrenCursor.moveToPosition( childPosition );

				long projectID = childrenCursor.getLong(
									 childrenCursor.getColumnIndex(
										 DatabaseAdapter.PROJECTS_COL_ASANA_ID
									 )
								 );

				String projectName = childrenCursor.getString(
										 childrenCursor.getColumnIndex(
											 DatabaseAdapter.PROJECTS_COL_NAME
										 )
									 );

				childrenCursor.close();

				Log.i( APP_TAG, "Project with ID: " +projectID +" clicked!" );
				Log.i( APP_TAG, "Project with name: " +projectName +" clicked!" );

				Intent newIntent = new Intent( ctx, TaskActivity.class );
				newIntent.putExtra( "projectID", projectID );
				newIntent.putExtra( "projectName", projectName );
				startActivity( newIntent );

				// we've handled the click, so return true
				return true;
			}
		});

		SpinnerAdapter spinAdapter = new WorkspaceAdapter(ctx, workspaceCursor);
		ActionBar ab = getSupportActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		ab.setListNavigationCallbacks(spinAdapter, new OnNavigationListener(){
			@Override
			public boolean onNavigationItemSelected( int position, long itemID )
			{
				Log.i( APP_TAG, "Click item at position: " +position );
				return true;
			}
		});
	}

	/**
	 * Updates the screen state (current list and other views) when the
	 * content changes.
	 * Shamelessly modified from Android's ListActivity
	 *
	 * @see Activity#onContentChanged()
	 */
	@Override
	public void onContentChanged() {
		super.onContentChanged();
		View emptyView = findViewById(R.id.no_workspaces);
		workspaceList = (ExpandableListView)findViewById(R.id.workspace_list);
		if (workspaceList == null) {
			throw new RuntimeException(
				"Your content must have a ListView whose id attribute is " +
				"'android.R.id.list'");
		}
		if (emptyView != null) {
			workspaceList.setEmptyView(emptyView);
		}
		workspaceList.setAdapter(workspaceAdapter);
	}
}
