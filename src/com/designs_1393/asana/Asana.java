package com.designs_1393.asana;

import com.designs_1393.asana.workspace.*;

// General
import android.os.Bundle;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.Context;

// UI
import android.view.View;
import android.widget.EditText;

// Fragments
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockDialogFragment;

import android.util.Log;

/**
 * Main application activity.
 * Because the application uses Fragments, this will probably stay fairly
 * minimal.
 */
public class Asana extends SherlockFragmentActivity
{
	final String APP_TAG = "Asana";
	private SharedPreferences sharedPrefs;
	private SherlockDialogFragment apiDialog;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		sharedPrefs = getSharedPreferences("AsanaPrefs", Context.MODE_PRIVATE);

		if(sharedPrefs.getString( "api key", "not found" ).equals("not found"))
		{
			showApiKeyDialog();
		}

		setContentView( R.layout.workspace_list );
	}

	private void showApiKeyDialog()
	{
		// create and show the dialog
		apiDialog = ApiKeyDialogFragment.newInstance(sharedPrefs);
		apiDialog.show( getSupportFragmentManager(), "dialog" );
	}

	public void doApiPositiveClick()
	{
		View v = apiDialog.getView();
		EditText apiKeyInput =
			(EditText) v.findViewById(R.id.apiKeyInstructions);

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
}
