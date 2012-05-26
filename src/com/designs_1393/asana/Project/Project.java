package com.designs_1393.asana.project;

// Asana classes
import com.designs_1393.asana.*;
import com.designs_1393.asana.workspace.*;

public class Project
{
	private boolean   archived;
	private String    createdAt; // TODO: this should be parsed to a real time
	//  private UserSet   followers; TODO: UserSet does not exist yet.
	private String    modifiedAt; // TODO: this should be parsed to a real time
	private String    name;
	private String    notes;
	private Workspace workspace;

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
		archived   = false;
		createdAt  = "";
		// followers = new UserSet();
		modifiedAt = "";
		name       = "";
		notes      = "";
		workspace  = "";
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

	/**
	 * Returns the set of users following this project.
	 * @return a UserSet containing the list of users following this project.
	 */
	public UserSet getFollowers()
	{
		return followers;
	}

	/**
	 * Sets the list of users following this project.
	 * Because this is a read-only property within Asana's API, this method
	 * exists only to facilitate the use of the Jackson JSON API.
	 * @param users  a UserSet containing the list of users following this
	 *               project.
	 */
	public void setFollowers( UserSet users )
	{
		followers = users;
	}
}
