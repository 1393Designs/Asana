package com.designs_1393.asana.workspace;

// Asana stuff (mainly R here)
import com.designs_1393.asana.*;

// Android stuff
import android.widget.SimpleCursorTreeAdapter;
import android.content.Context;
import android.database.Cursor;

import android.widget.TextView;

import android.util.Log;

public class ExpandableWorkspaceAdapter extends SimpleCursorTreeAdapter
{
	private DatabaseAdapter db = null;
	private final String APP_TAG = "Asana.ExpandableWorkspaceAdapter";

	private Context ctx;

	/**
	 * Constructor.
	 * Facade around a constructor from SimpleCursorTreeAdapter
	 *
	 * @param context  The context where the ExpandableListView associated with
	 *                 this ExpandableWorkspaceAdapter is running
	 * @param c        The database cursor
	 */
	public ExpandableWorkspaceAdapter( Context context, Cursor c )
	{
		super( context, c,
			R.layout.expandable_workspace_group,
			new String[] {DatabaseAdapter.WORKSPACES_COL_NAME},
			new int[] {R.id.workspace_name},
			R.layout.expandable_workspace_child,
			new String[] {DatabaseAdapter.PROJECTS_COL_NAME},
			new int[] {R.id.projects} );

		ctx = context;
		db = new DatabaseAdapter(ctx);
		db.open();
	}

	/**
	 * Gets the cursor for the children at the given group.
	 *
	 * @param groupCursor  The cursor pointing to the group whose children
	 *                     cursor should be returned
	 * @return The cursor for the children of a particular group, or null.
	 */
	public Cursor getChildrenCursor( Cursor groupCursor )
	{
		long id = groupCursor.getLong(
			groupCursor.getColumnIndex(
				DatabaseAdapter.WORKSPACES_COL_ASANA_ID
			)
		);

		Log.i( APP_TAG, "in getChildrenCursor" );

		return db.getProjects( id, true );
	}
}
