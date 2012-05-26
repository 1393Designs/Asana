package com.designs_1393.asana;

import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import android.database.Cursor;

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
import android.widget.ListView;

// Shared preferences
import android.content.SharedPreferences;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListFragment;

// Jackson JSON
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;

// Logging
import android.util.Log;

public class Asana extends SherlockFragmentActivity
{
	final String TAG = "Asana";
	private SharedPreferences sharedPrefs;
	private Context ctx;

	private Cursor workspaceCursor;

	/*private void getUserApiKey()
	{
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder( getApplicationContext() );
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
	}*/

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView( R.layout.workspace_list );
	}

}
