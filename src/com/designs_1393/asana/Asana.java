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

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockActivity;

// View
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

// Widgets
import android.widget.TextView;
import android.widget.EditText;

// Links
import android.text.util.Linkify;

// Dialog
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.app.ProgressDialog;

// AsyncTask
import android.os.AsyncTask;

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

	private AsyncTask loadTask;


	private void showAPIkeyDialog()
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

						loadTask.execute( new Void[] {} );
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
		final Context ctxCopy = this;

		// get shared preferences containing API key
		sharedPrefs = getSharedPreferences("AsanaPrefs", Context.MODE_PRIVATE);

		// get and store workspaces from Asana
		final AsanaFacade aFacade = new AsanaFacade( sharedPrefs, ctx );


		loadTask = new AsyncTask<Void, String, Void>()
		{
			ProgressDialog loadDialog = new ProgressDialog( ctxCopy );
			protected void onPreExecute()
			{
				loadDialog.setTitle( "Loading" );
				loadDialog.setMessage( "Loading Workspaces" );
				loadDialog.setIndeterminate(true);
				loadDialog.setCancelable(true);

				loadDialog.show();
			}

			protected Void doInBackground( Void... args )
			{
				aFacade.retreiveWorkspaces();
				publishProgress( "Loading Projects" );
				aFacade.retreiveProjects();

				return null;
			}

			protected void onProgressUpdate( String... args )
			{
				loadDialog.setMessage( args[0] );
			}

			protected void onPostExecute( Void result )
			{
				((SherlockActivity)ctxCopy).onContentChanged();
				Log.i( APP_TAG, "onPostExecute" );
				loadDialog.dismiss();
			}
		};

		if(sharedPrefs.getString( "api key", "not found" ).equals("not found"))
		{
			showAPIkeyDialog();
		}
		else
			loadTask.execute( new Void[] {} );

		// set layout content from the cache database
		dbAdapter = new DatabaseAdapter( ctx );
		dbAdapter.open();

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

		dbAdapter.close();

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
