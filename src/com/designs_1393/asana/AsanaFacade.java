package com.designs_1393.asana;

// Workspace classes
import com.designs_1393.asana.workspace.Workspace;
import com.designs_1393.asana.workspace.WorkspaceSet;
import com.designs_1393.asana.project.Project;
import com.designs_1393.asana.project.ProjectSet;
import com.designs_1393.asana.task.Task;
import com.designs_1393.asana.task.TaskSet;

// Android classes
import android.content.Context;
import android.content.SharedPreferences;

// Database
import android.database.Cursor;

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
	private ObjectMapper mapper = new ObjectMapper();

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
	 * Gets a list of all projects stored on Asana and store them in the cache
	 * database.
	 */
	public void retreiveProjects()
	{
		dbAdapter.open();

		Cursor c = dbAdapter.getWorkspaces( true );

		c.moveToFirst();
		for( int i = 0; i < c.getCount(); i++ )
		{
			long workspaceID = c.getLong(
			                       c.getColumnIndex(
			                           DatabaseAdapter.WORKSPACES_COL_ASANA_ID
			                       )
			                   );

			retreiveProjects( workspaceID );
			c.moveToNext();
		}

		dbAdapter.close();
	}

	/**
	 * Gets a list of projects in the workspace with ID workspaceID from the
	 * Asana servers, and stores them in the cache database.
	 * @param workspaceID  Asana-assigned ID for the workspace in question.
	 */
	public void retreiveProjects( long workspaceID )
	{
		dbAdapter.open();

		String projectsJSON = ah.getProjectsInWorkspace( workspaceID );

		Log.i( APP_TAG, "Retreiving for workspace: " +workspaceID );
		Log.i( APP_TAG, "JSON: " +projectsJSON );

		try
		{
			// map the received JSON to a ProjectSet
			ProjectSet projects = mapper.readValue(
				projectsJSON,
				ProjectSet.class );

			// manually set the workspace ID.  There MUST be a better way of
			// doing this.
			for( Project p : projects.getData() )
				p.setWorkspaceID( workspaceID );

			// write the ProjectSet to the cache database
			dbAdapter.addProjects( projects );
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		finally
		{
			dbAdapter.close();
		}
	}

	/**
	 * Gets a list of tasks associated with the project with ID projectID
	 * from the Asana servers, and stores them in the cache database.
	 * @param projectID  Asana-assigned ID for the project in question.
	 */
	public void retreiveTasks( long projectID )
	{
		dbAdapter.open();

		String tasksJSON = ah.getTasks( projectID );

		Log.i( APP_TAG, "Retreiving for project: " +projectID );
		Log.i( APP_TAG, "JSON: " +tasksJSON );

		try
		{
			// map the received JSON to a TaskSet
			TaskSet tasks = mapper.readValue(
				tasksJSON,
				TaskSet.class );

			// manually set the project ID.  There MUST be a better way of
			// doing this.
			for( Task t : tasks.getData() )
				t.setProjects( new long[] {projectID} );

			dbAdapter.addTasks( tasks );
		}
		catch(Exception e)
		{ e.printStackTrace(); }
		finally
		{
			dbAdapter.close();
		}
	}
}
