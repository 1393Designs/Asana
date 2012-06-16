package com.designs_1393.asana.project;

public class ProjectSet
{
	private Project[] data;

	/**
	 * Returns the array of Projects.
	 * @return array of Project objects.
	 */
	public Project[] getData()
	{
		return data;
	}

	/** Sets the array of Projects.
	 *  @param newData  array of Project objects.
	 */
	public void setData( Project[] newData )
	{
		data = newData;
	}
}
