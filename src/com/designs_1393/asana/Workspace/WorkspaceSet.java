package com.designs_1393.asana;

public class WorkspaceSet
{
	private Workspace[] data;

	/** Sets the array of Workspaces.
	 *  @param newData Array of Workspace objects
	 */
	public void setData( Workspace[] newData )
	{
		this.data = newData;
	}

	/** Returns the array of Workspaces.
	 *  @return Array of Workspace objects
	 */
	public Workspace[] getData()
	{
		return data;
	}
}
