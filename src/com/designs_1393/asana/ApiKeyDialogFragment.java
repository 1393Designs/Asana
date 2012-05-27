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
	private final String APP_TAG = "ApiKeyDialogFragment";
	private static SharedPreferences sharedPrefs;

	public static ApiKeyDialogFragment newInstance( SharedPreferences prefs )
	{
		ApiKeyDialogFragment frag = new ApiKeyDialogFragment();

		sharedPrefs = prefs;
		return frag;
	}

	@Override
	public View onCreateView( LayoutInflater inflater,
	                          ViewGroup      container,
	                          Bundle         savedInstanceState )
	{
		View v = inflater.inflate(R.layout.apikeydialog, container );
		TextView tv = (TextView) v.findViewById( R.id.apiKeyInstructions );

		// apply links
		Linkify.addLinks( tv, Linkify.WEB_URLS );

		return v;
	}

	@Override
	public Dialog onCreateDialog( Bundle savedInstanceState )
	{
		View layout = getView();
		final EditText apiKeyInput =
			(EditText) layout.findViewById( R.id.apiKeyEntry );

		return new AlertDialog.Builder( getActivity() )
			.setTitle( "API Key" )
			.setView( layout )
			.setCancelable( true )
			.setPositiveButton( "Okay", new DialogInterface.OnClickListener() {
					public void onClick( DialogInterface dialog, int which )
					{
						SharedPreferences.Editor editor =
							ApiKeyDialogFragment.sharedPrefs.edit();
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
