package com.designs_1393.asana;

// General
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.Context;

import android.util.Log;

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

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockDialogFragment;

public class ApiKeyDialogFragment extends SherlockDialogFragment
{
	private final String APP_TAG = "Asana.ApiKeyDialogFragment";
	private static SharedPreferences sharedPrefs;

	public static ApiKeyDialogFragment newInstance( SharedPreferences prefs )
	{
		ApiKeyDialogFragment frag = new ApiKeyDialogFragment();

		sharedPrefs = prefs;
		return frag;
	}

	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState )
	{
		Log.i( APP_TAG, "in onCreateDialog()" );

		View v = getActivity()
		         .getLayoutInflater()
		         .inflate(R.layout.apikeydialog, null);
		TextView instructions =
			(TextView) v.findViewById( R.id.apiKeyInstructions );

		final EditText apiKeyInput =
			(EditText) v.findViewById( R.id.apiKeyEntry );

		// apply links to instructions
		Linkify.addLinks( instructions, Linkify.WEB_URLS );

		return new AlertDialog.Builder( getActivity() )
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
			}).create();
	}
}
