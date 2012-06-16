package com.designs_1393.asana.project;

// Asana stuff (mainly R here)
import com.designs_1393.asana.*;

// Android stuff
import android.widget.SimpleCursorAdapter;
import android.content.Context;
import android.database.Cursor;

/** Facade pattern around a simpleCursorAdapter for the Projects list.
 *  Expect this to change as the layout gets more complex.
 */
public class ProjectAdapter extends SimpleCursorAdapter
{
	public ProjectAdapter( Context context, Cursor c )
	{
		super( context, R.layout.project_row, c,
			new String[] {"project_name"},
			new int[] {R.id.project_list_name} );
	}
}
