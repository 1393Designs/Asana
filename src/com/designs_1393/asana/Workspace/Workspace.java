package com.designs_1393.asana.workspace;

// Asana classes
import com.designs_1393.asana.*;

public class Workspace
{
	private long ID;
	private String name;

	/** Default constructor.
	 *  Creates a new Workspace object with 'ID'=0 and empty 'name' field
	 */
	public Workspace()
	{
		ID = 0;
		name = "";
	}

	/** Explicit constructor.
	 *  Creates a new Workspace object with 'ID' and 'name' fields as provided.
	 *  @param workspaceID    Unique long int identifier for the workspace, as
	 *                        provided by Asana.
	 *  @param workspaceName  String describing the workspace.
	 */
	public Workspace( long workspaceID, String workspaceName )
	{
		setID( workspaceID );
		setName( workspaceName );
	}

	/** Returns the workspace's unique identifier from Asana.
	 *  @return Unique identifier for this workspace, represented as a long
	 *          int.
	 */
	public long getID()
	{
		return ID;
	}

	/** Sets the workspace's unique identifier, as provided by Asana.
	 *  This exists mainly to facilitate the use of the Jackson JSON API.
	 *  @param workspaceID    Unique long int identifier for the workspace, as
	 *                        provided by Asana.
	 */
	public void setID( long workspaceID )
	{
		this.ID = workspaceID;
	}

	/** Returns the workspace's descriptive name.
	 *  @return String containing the workspace's name.
	 */
	public String getName()
	{
		return name;
	}

	/** Sets the workspace's descriptive name.
	 *  @param workspaceName  String describing the workspace.
	 */
	public void setName( String workspaceName )
	{
		this.name = workspaceName;
	}
}
