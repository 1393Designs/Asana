package com.designs_1393.asana.task;

// Asana stuff
import com.designs_1393.asana.*;

// General
import android.content.Context;

// View
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

// Database
import android.database.Cursor;

// Widgets
import android.widget.TextView;

// Base Class
import android.widget.SimpleCursorAdapter;

public class TaskAdapter extends SimpleCursorAdapter
{
	public TaskAdapter( Context context, Cursor c )
	{
		super( context, R.layout.task_row, c,
			new String[] { DatabaseAdapter.TASKS_COL_NAME },
			new int[] {R.id.task_name} );
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup viewGroup)
	{
		return LayoutInflater.from(context).inflate(R.layout.task_row, viewGroup, false);
	}

	@Override
	public void bindView(View v, Context context, Cursor c)
	{
		TextView title = (TextView) v.findViewById(R.id.task_name);
		String name = c.getString(c.getColumnIndex(DatabaseAdapter.TASKS_COL_NAME));

		if( name.endsWith(":") )
		{
			v.findViewById(R.id.task_done).setVisibility(View.GONE);
		}

		title.setText(name);
	}
}
