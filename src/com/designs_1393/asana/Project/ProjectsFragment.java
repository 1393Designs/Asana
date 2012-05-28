package com.designs_1393.asana.workspace;

import android.util.Log;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// Asana classes
import com.designs_1393.asana.*;

// ActionBarSherlock
import com.actionbarsherlock.app.SherlockListFragment;

public class ProjectsFragment extends SherlockListFragment
{
	private final String APP_TAG = "Asana.ProjectsFragment";

	/**
	 * Creates a new instance of ProjectsFragment, initialized to show the
	 * projects from workspace with ID 'workspaceID'.
	 */
	public static ProjectsFragment newInstance( long workspaceID )
	{
		Log.i( "Asana.ProjectsFragment", "GOT WORKSPACE ID: " +workspaceID );
		ProjectsFragment f = new ProjectsFragment();

		// supply workspace ID as an argument.
		Bundle args = new Bundle();
		args.putLong( "workspaceID", workspaceID );
		f.setArguments( args );

		return f;
	}

	@Override
	public void onActivityCreated(Bundle savedState)
	{
		Log.i(APP_TAG, "in Activity Created!");
	}


	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState )
	{
		View layout = inflater.inflate( R.layout.project_list, container );
		return layout;
	}

}
