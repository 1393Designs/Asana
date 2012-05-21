package com.designs_1393.asana;

import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

// Dialogs
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.text.util.Linkify;
//import android.view.View.OnClickListener;

// Widgets
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

// Shared preferences
import android.content.SharedPreferences;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockActivity;

// Jackson JSON
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;

// Logging
import android.util.Log;

public class Asana extends SherlockActivity
{
	final String TAG = "Asana";
	private SharedPreferences sharedPrefs;
	private Context ctx;

	private void getUserApiKey()
	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.apikeydialog,
			(ViewGroup) getCurrentFocus());
		dialogBuilder.setView( dialoglayout );

		final EditText apiKeyInput = (EditText)
			dialoglayout.findViewById(R.id.apiKeyEntry);
		final TextView apiInstructions = (TextView)
			dialoglayout.findViewById(R.id.apiKeyInstructions);

		Linkify.addLinks( apiInstructions, Linkify.WEB_URLS );

		dialogBuilder.setCancelable( true );
		dialogBuilder.setPositiveButton( "Okay", new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int which )
			{
				if( sharedPrefs == null )
					Log.i(TAG, "Shared prefs is null");
				SharedPreferences.Editor editor = sharedPrefs.edit();
				if( editor == null )
					Log.i(TAG, "Editor is null");
				editor.putString("api key", apiKeyInput.getText().toString());
				editor.commit();
			}
		});

		dialogBuilder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int which ){}
		});

		dialogBuilder.create().show();
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		ctx = getApplicationContext();

		sharedPrefs = this.getSharedPreferences("AsanaPrefs", Context.MODE_PRIVATE);

		if( !sharedPrefs.contains("api key") )
		{
			getUserApiKey();
		}
		else
		{
			try{
				Log.i(TAG, "Found API key: " +sharedPrefs.getString("api key",
					"No \"api key\" preference exists."));
			} catch( Exception e ){ Log.e(TAG, e.toString()); }
		}

		AsanaHelper ah = new AsanaHelper( sharedPrefs.getString("api key", "No API key.") );
		ah.usePrettyPrint( true );

		String workspacesJSON = ah.getWorkspaces();

		Log.i(TAG, "Starting deserialization");
		ObjectMapper mapper = new ObjectMapper();

		try{
			WorkspaceSet workspaces = mapper.readValue( workspacesJSON, WorkspaceSet.class );

			Workspace[] wArray = workspaces.getData();

			for( int i = 0; i < wArray.length; i++ )
			{
				Log.i(TAG, "\tWorkspace ID = " +wArray[i].getID());
				Log.i(TAG, "\tWorkspace name = " +wArray[i].getName());
			}

			DatabaseAdapter dbAdapter = new DatabaseAdapter( getApplicationContext() );
			dbAdapter.open();
			boolean successfulSet = dbAdapter.setWorkspaces( workspaces );
			dbAdapter.close();

			if( successfulSet )
				Log.i( TAG, "DB set successful!" );
			else
				Log.i( TAG, "DB set failed. :(" );
		} catch( Exception e) { e.printStackTrace(); }
		Log.i(TAG, "Finished deserialization");



		/*Log.i(TAG, "All projects in any workspace:");
		Log.i(TAG, "\t" +ah.getAllProjects());

		Log.i(TAG, "All projects in workspace \"1393 Designs\":");
		Log.i(TAG, "\t" +ah.getProjectsInWorkspace( 185645369321L ));

		Log.i(TAG, "Create a task with name \"Android test\" to workspace \"1393 Designs\"");
		Log.i(TAG, "\t" +ah.createTask( "Android test",
			185645369321L ));*/
    }
}
