package com.designs_1393.asana;

import android.os.Bundle;
import android.content.Context;
import android.view.View;

// Dialogs
import android.app.Dialog;
import android.text.util.Linkify;
import android.view.View.OnClickListener;

// Widgets
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

// Shared preferences
import android.content.SharedPreferences;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockActivity;

// Logging
import android.util.Log;

public class Asana extends SherlockActivity
{
	final String TAG = "Asana";
	private SharedPreferences sharedPrefs;
	private Context ctx;

	private void getUserApiKey()
	{
		final Dialog apiKeyDialog = new Dialog( Asana.this );
		apiKeyDialog.setContentView(R.layout.apikeydialog);
		apiKeyDialog.setTitle("Asana API Key");

		final TextView instructionsText = (TextView)
			apiKeyDialog.findViewById(R.id.apiKeyInstructions);
		final EditText apiKeyInput = (EditText)
			apiKeyDialog.findViewById(R.id.apiKeyEntry);

		Button okayButton   = (Button)
			apiKeyDialog.findViewById(R.id.okayButton);
		Button cancelButton = (Button)
			apiKeyDialog.findViewById(R.id.cancelButton);

		okayButton.setOnClickListener( new OnClickListener() {
			public void onClick( View v )
			{
				SharedPreferences.Editor editor = sharedPrefs.edit();
				editor.putString("api key", apiKeyInput.getText().toString());
				editor.commit();
				apiKeyDialog.dismiss();
			}
		});

		cancelButton.setOnClickListener( new OnClickListener() {
			public void onClick( View v )
			{
				apiKeyDialog.dismiss();
			}
		});

		Linkify.addLinks( instructionsText, Linkify.WEB_URLS );
		apiKeyDialog.show();
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

		Log.i(TAG, "All workspaces:");
		Log.i(TAG, "\t" +ah.getWorkspaces());

		Log.i(TAG, "All projects in any workspace:");
		Log.i(TAG, "\t" +ah.getAllProjects());

		Log.i(TAG, "All projects in workspace \"1393 Designs\":");
		Log.i(TAG, "\t" +ah.getProjectsInWorkspace( 185645369321L ));

		Log.i(TAG, "Create a task with name \"Android test\" to workspace \"1393 Designs\"");
		Log.i(TAG, "\t" +ah.createTask( "Android test",
			185645369321L ));
    }
}
