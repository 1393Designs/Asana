package com.designs_1393.asana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdapter
{
	/* Database Attribute Declarations */
	public static final String WORKSPACES_TABLE_NAME   = "workspaces";
	public static final String WORKSPACES_KEY_ASANA_ID = "workspace_id";
	public static final String WORKSPACES_KEY_NAME     = "workspace_name";
	public static final String WORKSPACES_KEY_ID       = "_id";

	public static final String DATABASE_NAME           = "asana_data";
	public static final int    DATABASE_VERSION        = 1;


	/* Table CREATE Commands */
	private static final String WORKSPACES_TABLE_CREATE =
		"CREATE TABLE " +WORKSPACES_TABLE_NAME +" ("
		+WORKSPACES_KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
		+WORKSPACES_KEY_ASANA_ID +" INTEGER NOT NULL, "
		+WORKSPACES_KEY_NAME +" TEXT NOT NULL);";


	/* Class Member Objects */
	private static final String TAG = "Asana: DatabaseAdapter";

	private DatabaseHelper DBhelper;
	private SQLiteDatabase DB;

	private final Context context;

	/** Class constructor.
	 *  Retains calling application's context, so that it can be used in
	 *  additional functions.
	 *  @param callingApplicationsContext  Calling application's context
	 */
	public DatabaseAdapter( Context callingApplicationsContext )
	{
		this.context = callingApplicationsContext;
	}

	/* Inner class providing a databse upgrade process. */
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		/** Class constructor.
		 *  Retains calling application's context, so that it can be used in
		 *  additional functions.
		 *  @param context  Calling application's context
		 */
		DatabaseHelper( Context context )
		{
			super( context, DATABASE_NAME, null, DATABASE_VERSION );
		}

		@Override
		public void onCreate( SQLiteDatabase db )
		{
			db.execSQL( WORKSPACES_TABLE_CREATE );
		}

		@Override
		public void onUpgrade( SQLiteDatabase db,
			                   int oldVersion,
			                   int newVersion )
		{
			// nothing required here yet
		}
	}

	/**
	 * Opens the database helper for writing and returns the database adapter.
	 * @return  Database adapter associated with the database.
	 */
	public DatabaseAdapter open() throws SQLException
	{
		DBhelper = new DatabaseHelper( this.context );
		DB = DBhelper.getWritableDatabase();
		return this;
	}

	/** Closes the database helper
	 */
	public void close()
	{
		DBhelper.close();
	}

	/** Sets the "workspaces" table to the data in the WorkspaceSet.
	 *  This should be used primarily to inject the WorkspaceSet parsed from
	 *  {@link AsanaHelper.getWorkspaces()}.  Note that this method deletes the
	 *  entire contents of the "workspaces" table first, and then replaces them
	 *  with the data in "workspaces".
	 *  @param workspaces  WorkspaceSet parsed from Asana's list of workspaces.
	 *  @return            true if the operation was successful, flase otherwise.
	 */
	public boolean setWorkspaces( WorkspaceSet workspaces )
	{
		// TODO: Handle rollback if "insert" fails?  Maybe this is possible with some a conflict clause?
		// delete contents
		DB.delete( WORKSPACES_TABLE_NAME, null, null );

		ContentValues values;

		// Get array of workspaces from WorkspaceSet
		Workspace[] workspaceArray = workspaces.getData();

		long insertResult = 0;

		for( int i = 0; i < workspaceArray.length; i++ )
		{
			values = new ContentValues();

			values.put( WORKSPACES_KEY_ASANA_ID, workspaceArray[i].getID()   );
			values.put( WORKSPACES_KEY_NAME,     workspaceArray[i].getName() );

			insertResult = DB.insert( WORKSPACES_TABLE_NAME, null, values );
			if( insertResult == -1 )
				return false;
		}

		return true;
	}
}
