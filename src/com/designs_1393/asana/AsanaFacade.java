package com.designs_1393.asana;

// Workspace classes
import com.designs_1393.asana.workspace.Workspace;
import com.designs_1393.asana.workspace.WorkspaceSet;
import com.designs_1393.asana.project.ProjectSet;

// Android classes
import android.content.Context;
import android.content.SharedPreferences;

// Jackson JSON
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;

import android.util.Log;

/**
 * Facade pattern around AsanaAPI to automate the process of storing things in
 * the cache database.
 */
public class AsanaFacade
{
	private SharedPreferences preferences;
	private AsanaAPI ah;
	private Context context;
	private DatabaseAdapter dbAdapter;

	private final String APP_TAG = "Asana.AsanaFacade";

	/**
	 * Creates a new AsanaFacade.
	 * @param prefs  SharedPreferences object containing the user's API key.
	 * @param ctx    Context for the calling application.
	 */
	public AsanaFacade( SharedPreferences prefs, Context ctx)
	{
		preferences = prefs;
		context = ctx;

		ah = new AsanaAPI( preferences.getString(
			"api key",
			"No API key.") );
		ah.usePrettyPrint( true ); // DBG
		dbAdapter = new DatabaseAdapter( context );
	}

	/**
	 * Gets a list of workspaces from the Asana servers and stores them in the
	 * cache database.
	 */
	public void retreiveWorkspaces()
	{
		dbAdapter.open();

		String workspacesJSON = ah.getWorkspaces();

		ObjectMapper mapper = new ObjectMapper();
		try
		{
			// map the received JSON to a WorkspaceSet
			WorkspaceSet workspaces = mapper.readValue(
				workspacesJSON,
				WorkspaceSet.class );

			// write the WorkspaceSet to the cache database
			dbAdapter.setWorkspaces( workspaces );
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		finally
		{
			dbAdapter.close();
		}
	}

	/**
	 * Gets a list of all projects stored on Asana
	 */
	public void retreiveProjects()
	{
		dbAdapter.open();

		String projectsJSON = ah.getAllProjects();
		Log.i( APP_TAG, projectsJSON );

		ObjectMapper mapper = new ObjectMapper();
		try
		{
			// map the received JSON to a ProjectSet
			ProjectSet projects = mapper.readValue(
				projectsJSON,
				ProjectSet.class );

			// write the ProjectSet to the cache database
			dbAdapter.setProjects( projects );
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		finally
		{
			dbAdapter.close();
		}
	}

	/**
	 * Gets a list of projects in the workspace with ID workspaceID from the
	 * Asana servers, and stores them in the cache database.
	 * @param workspaceID  Asana-assigned ID for the workspace in question.
	 */
	public void retreiveProjects( long workspaceID )
	{
		dbAdapter.open();
		dbAdapter.close();
	}
}
