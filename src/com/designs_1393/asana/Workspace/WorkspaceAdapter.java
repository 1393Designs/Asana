package com.designs_1393.asana.workspace;

// Asana stuff (mainly R here)
import com.designs_1393.asana.*;

// Android stuff
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
		super( context, android.R.layout.simple_spinner_item, c,
			new String[] {DatabaseAdapter.WORKSPACES_COL_NAME},
			new int[] {android.R.id.text1} );
	}
}
