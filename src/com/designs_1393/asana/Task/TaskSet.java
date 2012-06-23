package com.designs_1393.asana.task;

public class TaskSet
{
	private Task[] data;

	/**
	 * Returns the array of Tasks.
	 * @return array of Task objects.
	 */
	public Task[] getData()
	{
		return data;
	}

	/**
	 * Sets the array of Tasks.
	 * @param newData  array of Task objects.
	 */
	public void setData( Task[] newData )
	{
		data = newData;
	}
}
