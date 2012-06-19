package com.designs_1393.asana.project;

// Asana classes
import com.designs_1393.asana.*;
import com.designs_1393.asana.workspace.*;

public class Project
{
	private long      ID;
	private boolean   archived;
	private String    createdAt; // TODO: this should be parsed to a real time
	//  private UserSet   followers; TODO: UserSet does not exist yet.
	private String    modifiedAt; // TODO: this should be parsed to a real time
	private String    name;
	private String    notes;
	private long      workspaceID;

	/**
	 * Default constructor.
	 * Creates a new Project object with the following properties: <br>
	 * <blockquote>
	 *   archived   = false<br>
	 *   createdAt  = ""<br>
	 *   followers  = [empty]<br>
	 *   modifiedAt = ""<br>
	 *   name       = ""<br>
	 *   notes      = ""<br>
	 *   workspace  = [empty]<br>
	 * </blockquote>
	 */
	public Project()
	{
		archived    = false;
		createdAt   = "";
		// followers = new UserSet();
		modifiedAt  = "";
		name        = "";
		notes       = "";
		workspaceID = 0;
	}

	/**
	 * Returns the project's unique identifier from Asana.
	 * @return unique identieifer for this worpsace, represented as a long int.
	 */
	public long getID()
	{
		return ID;
	}

	/**
	 * Sets the project's unique identifier, as provided by Asana.
	 * This exists mainly to facilitate the use of the Jackson JSON API.
	 * @param projectID  Unique long int identifier for the proejct, as
	 *                   provided by Asana.
	 */
	public void setID( long projectID )
	{
		ID = projectID;
	}

	/**
	 * Returns the project's archived status.
	 * @return true if the project is archived on Asana, false otherwise.
	 */
	public boolean isArchived()
	{
		return archived;
	}

	/**
	 * Sets the project's archived state.
	 * @param isArchived  boolean describing the new archived state of the
	 *                    project.
	 */
	public void setArchived( boolean isArchived )
	{
		archived = isArchived;
	}

	/**
	 * Returns the time at which the project was created on Asana's servers.
	 * @return a String representation of a time, e.g. 2012-02-22T02:06:58.147Z.
	 */
	public String getCreatedAt()
	{
		return createdAt;
	}

	/**
	 * Sets the time at which the project was created on Asana's servers.
	 * Because this is a read-only property within Asana's API, this method
	 * exists only to facilitate the use of the Jackson JSON API.
	 * @param time  a String representation of a time, e.g.
	 *              2012-02-22T02:06:58.147Z.
	 */
	public void setCreatedAt( String time )
	{
		createdAt = time;
	}

//	/**
//	 * Returns the set of users following this project.
//	 * @return a UserSet containing the list of users following this project.
//	 */
//	public UserSet getFollowers()
//	{
//		return followers;
//	}
//
//	/**
//	 * Sets the list of users following this project.
//	 * Because this is a read-only property within Asana's API, this method
//	 * exists only to facilitate the use of the Jackson JSON API.
//	 * @param users  a UserSet containing the list of users following this
//	 *               project.
//	 */
//	public void setFollowers( UserSet users )
//	{
//		followers = users;
//	}

	/**
	 * Returns the time at which this project was last modified on Asana's
	 * servers.
	 * @return a String representation of a time, e.g. 2012-02-22T02:06:58.147Z.
	 */
	public String getModifiedAt()
	{
		return modifiedAt;
	}

	/**
	 * Sets the time at which this project was last modified on Asana's servers.
	 * Because this is a read-only property within Asana's API, this method
	 * exists only to facilitate the use of the Jackson JSON API.
	 * @param time  a String representation of a time, e.g.
	 *              2012-02-22T02:06:58.147Z.
	 */
	public void setModifiedAt( String time )
	{
		modifiedAt = time;
	}

	/**
	 * Returns the name of the project.
	 * @return a String containing the name of the project.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of this project.
	 * @param text  a String containing the name of the project.
	 */
	public void setName( String text )
	{
		name = text;
	}

	/**
	 * Returns the notes stored with the project.
	 * @return a String containing the notes stored with the project.
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * Sets the notes stored with the project.
	 * @param text  a String containing the notes stored with the project.
	 */
	public void setNotes( String text )
	{
		notes = text;
	}

	/**
	 * Returns the workspace ID this project is associated with.
	 * @return the workspace ID this project is associated with
	 */
	public long getWorkspaceID()
	{
		return workspaceID;
	}

	/**
	 * Sets the workspace ID this project is associated with.
	 * Note that once created, projects cannot be moved to a different
	 * workspace.
	 * @param wsID the ID of the workspace this project is associated with
	 */
	public void setWorkspaceID( long wsID )
	{
		workspaceID = wsID;
	}
}
