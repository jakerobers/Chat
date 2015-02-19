package models;

public interface Observer {

	/**
	 * Updates the observer with the given information.
	 * @param Object o The information to be sent.
	 */
	void update(Object o);
}
