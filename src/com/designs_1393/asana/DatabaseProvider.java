package com.designs_1393.asana;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

public class DatabaseProvider extends ContentProvider
{
	/* Database Attribute Declarations */
	public static final String WORKSPACES_TABLE_NAME    = "workspaces";
	public static final String WORKSPACES_COL_ID        = "_id";
	public static final String WORKSPACES_COL_ASANA_ID  = "workspace_id";
	public static final String WORKSPACES_COL_NAME      = "workspace_name";

	public static final String PROJECTS_TABLE_NAME      = "projects";
	public static final String PROJECTS_COL_ID          = "_id";
	public static final String PROJECTS_COL_ASANA_ID    = "project_id";
	public static final String PROJECTS_COL_ARCHIVED    = "archived";
	public static final String PROJECTS_COL_CREATED_AT  = "created_at";
	public static final String PROJECTS_COL_FOLLOWERS   = "followers";
	public static final String PROJECTS_COL_MODIFIED_AT = "modified_at";
	public static final String PROJECTS_COL_NAME        = "project_name";
	public static final String PROJECTS_COL_NOTES       = "project_notes";
	public static final String PROJECTS_COL_WORKSPACE   = "workspace_id";

	public static final String TASKS_TABLE_NAME         = "tasks";
	public static final String TASKS_COL_ID             = "_id";
	public static final String TASKS_COL_ASANA_ID       = "task_id";
	public static final String TASKS_COL_ASSIGNEE       = "assignee";
	public static final String TASKS_COL_CREATED_AT     = "created_at";
	public static final String TASKS_COL_COMPLETED      = "completed";
	public static final String TASKS_COL_MODIFIED_AT    = "modified_at";
	public static final String TASKS_COL_NAME           = "name";
	public static final String TASKS_COL_NOTES          = "notes";
	public static final String TASKS_COL_PROJECT_IDS    = "project_ids";
	public static final String TASKS_COL_WORKSPACE      = "workspace_id";

	public static final String DATABASE_NAME           = "asana_data";
	public static final int    DATABASE_VERSION        = 1;


	/* Table CREATE Commands */
	private static final String WORKSPACES_TABLE_CREATE =
		"CREATE TABLE " +WORKSPACES_TABLE_NAME +" ("
		+WORKSPACES_COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
		+WORKSPACES_COL_ASANA_ID +" INTEGER NOT NULL, "
		+WORKSPACES_COL_NAME +" TEXT NOT NULL);";

	private static final String PROJECTS_TABLE_CREATE =
		"CREATE TABLE " +PROJECTS_TABLE_NAME +" ("
		+PROJECTS_COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
		+PROJECTS_COL_ASANA_ID    +" INTEGER NOT NULL, "
		+PROJECTS_COL_ARCHIVED    +" INTEGER NOT NULL, "
		+PROJECTS_COL_CREATED_AT  +" TEXT NOT NULL, "
		+PROJECTS_COL_FOLLOWERS   +" TEXT NOT NULL, "
		+PROJECTS_COL_MODIFIED_AT +" TEXT NOT NULL, "
		+PROJECTS_COL_NAME        +" TEXT NOT NULL, "
		+PROJECTS_COL_NOTES       +" TEXT NOT NULL, "
		+PROJECTS_COL_WORKSPACE   +" INTEGER NOT NULL);";

	private static final String TASKS_TABLE_CREATE =
		"CREATE TABLE " +TASKS_TABLE_NAME +" ("
		+TASKS_COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
		+TASKS_COL_ASANA_ID       +" INTEGER NOT NULL, "
		+TASKS_COL_ASSIGNEE       +" INTEGER NOT NULL, "
		+TASKS_COL_CREATED_AT     +" TEXT NOT NULL, "
		+TASKS_COL_COMPLETED      +" INTEGER NOT NULL, "
		+TASKS_COL_MODIFIED_AT    +" TEXT NOT NULL, "
		+TASKS_COL_NAME           +" TEXT NOT NULL, "
		+TASKS_COL_NOTES          +" TEXT NOT NULL, "
		+TASKS_COL_PROJECT_IDS    +" TEXT NOT NULL, "
		+TASKS_COL_WORKSPACE      +" INTEGER NOT NULL);";

	/* URI matcher constants */
	// The incoming URI matches the Workspaces URI pattern
	private static final int WORKSPACES = 1;


	/* Class Member Objects */
	private static final String APP_TAG = "Asana.DatabaseProvider";


	private static final UriMatcher URImatcher; // A UriMatcher instance

	// A block that instantiates and sets static objects
	static
	{
		URImatcher = new UriMatcher(UriMatcher.NO_MATCH);
	}

	private DatabaseHelper DBhelper;
	private SQLiteDatabase DB;


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
			db.execSQL( PROJECTS_TABLE_CREATE );
			db.execSQL( TASKS_TABLE_CREATE );
		}

		@Override
		public void onUpgrade( SQLiteDatabase db,
			                   int oldVersion,
			                   int newVersion )
		{
			// nothing required here yet
		}
	}

	public boolean onCreate()
	{
		DBhelper = new DatabaseHelper( getContext() );

		return true;
	}

	/**
	 * This method is called when a client calls
	 * {@link android.content.ContentResolver#query(Uri, String[], String, String[], String)}.
	 * Queries the database and returns a cursor containing the results.
	 *
	 * @return A cursor containing the results of the query. The cursor exists but is empty if
	 * the query returns no results or an exception occurs.
	 * @throws IllegalArgumentException if the incoming URI pattern is invalid.
	 */
	@Override
	public Cursor query( Uri uri,
	                     String[] projection,
	                     String selection,
	                     String[] selectionArgs,
	                     String sortOrder )
	{
		// Opens the database object in "read" mode, since no writes need to be
		// done.
		SQLiteDatabase DB = DBhelper.getReadableDatabase();
		Cursor c = null;

		switch( URImatcher.match(uri) )
		{
			case WORKSPACES:
				c = DB.query( WORKSPACES_TABLE_NAME,
					new String[] {WORKSPACES_COL_ID,
						WORKSPACES_COL_ASANA_ID,
						WORKSPACES_COL_NAME},
					null, null, null, null, WORKSPACES_COL_NAME );
				break;
			default:
				break;
		}

		c.setNotificationUri( getContext().getContentResolver(), uri );
		return c;
	}


	/**
	 * This is called when a client calls
	 * {@link android.content.ContentResolver#insert(Uri, ContentValues)}.
	 * Inserts a new row into the database. This method sets up default values for any
	 * columns that are not included in the incoming map.
	 * If rows were inserted, then listeners are notified of the change.
	 * @return The row ID of the inserted row.
	 * @throws SQLException if the insertion fails.
	 */
	@Override
	public Uri insert( Uri uri, ContentValues initialValues )
	{
		ContentValues values;

		// If the incoming values map is not null, uses it for the new values.
		if( initialValues == null )
		{
			values = new ContentValues();
		}
		else
		{
			// Otherwise, create a new value map
			values = new ContentValues( initialValues );
		}

		// Opens the database object in "write" mode.
		SQLiteDatabase DB = DBhelper.getWritableDatabase();

		String insertTable = "";
		switch( URImatcher.match(uri) )
		{
			case WORKSPACES:
				insertTable = WORKSPACES_TABLE_NAME;
				break;
			default:
				break;
		}

		long rowID = DB.insert( insertTable, null, values );


		// If the insert succeeded, the row ID exists.
		if (rowID > 0) {
			// Creates a URI with the note ID pattern and the new row ID appended to it.
			//Uri noteUri = ContentUris.withAppendedId(NotePad.Notes.CONTENT_ID_URI_BASE, rowId);

			// Notifies observers registered against this provider that the data changed.
			//getContext().getContentResolver().notifyChange(noteUri, null);
			//return noteUri;
		}

		// If the insert didn't succeed, then the rowID is <= 0. Throws an exception.
		throw new SQLException("Failed to insert row into " + uri);
	}

	/**
	 * This is called when a client calls
	 * {@link android.content.ContentResolver#delete(Uri, String, String[])}.
	 * Deletes records from the database. If the incoming URI matches the note ID URI pattern,
	 * this method deletes the one record specified by the ID in the URI. Otherwise, it deletes a
	 * a set of records. The record or records must also match the input selection criteria
	 * specified by where and whereArgs.
	 *
	 * If rows were deleted, then listeners are notified of the change.
	 * @return If a "where" clause is used, the number of rows affected is returned, otherwise
	 * 0 is returned. To delete all rows and get a row count, use "1" as the where clause.
	 * @throws IllegalArgumentException if the incoming URI pattern is invalid.
	 */
	@Override
	public int delete( Uri uri, String where, String[] whereArgs )
	{
		SQLiteDatabase DB = DBhelper.getWritableDatabase();
		String finalWhere;

		return 0;
	}

	/**
	 * This is called when a client calls
	 * {@link android.content.ContentResolver#update(Uri,ContentValues,String,String[])}
	 * Updates records in the database. The column names specified by the keys in the values map
	 * are updated with new data specified by the values in the map. If the incoming URI matches the
	 * note ID URI pattern, then the method updates the one record specified by the ID in the URI;
	 * otherwise, it updates a set of records. The record or records must match the input
	 * selection criteria specified by where and whereArgs.
	 * If rows were updated, then listeners are notified of the change.
	 *
	 * @param uri The URI pattern to match and update.
	 * @param values A map of column names (keys) and new values (values).
	 * @param where An SQL "WHERE" clause that selects records based on their column values. If this
	 * is null, then all records that match the URI pattern are selected.
	 * @param whereArgs An array of selection criteria. If the "where" param contains value
	 * placeholders ("?"), then each placeholder is replaced by the corresponding element in the
	 * array.
	 * @return The number of rows updated.
	 * @throws IllegalArgumentException if the incoming URI pattern is invalid.
	 */
	@Override
	public int update( Uri uri,
	                   ContentValues values,
	                   String where,
	                   String[] whereArgs )
	{
		return 0;
	}

	/**
	 * This is called when a client calls {@link android.content.ContentResolver#getType(Uri)}.
	 * Returns the MIME data type of the URI given as a parameter.
	 *
	 * @param uri The URI whose MIME type is desired.
	 * @return The MIME type of the URI.
	 * @throws IllegalArgumentException if the incoming URI pattern is invalid.
	 */
	@Override
	public String getType( Uri uri )
	{
		return "vnd.android.cursor.asana"; // TODO: Change this
	}
}
