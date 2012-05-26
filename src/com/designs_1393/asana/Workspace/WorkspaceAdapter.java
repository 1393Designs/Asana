package com.designs_1393.asana;

import android.widget.SimpleCursorAdapter;
import android.content.Context;
import android.database.Cursor;

/** Facade pattern around a SimpleCursorAdapter for the Workspaces list.
 *  Expect this to change as the layout gets more complex.
 */
public class WorkspaceAdapter extends SimpleCursorAdapter
{
	public WorkspaceAdapter( Context context, Cursor c )
	{
		super( context, R.layout.workspace_row, c,
			new String[] {"workspace_name"},
			new int[] {R.id.workspace_list_name} );
	}
}
