package com.designs_1393.asana;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;

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
