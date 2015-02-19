package models;

public interface Subject {

	/**
	 * Attempts to add a subject.
	 * @return true if add is successful, false if it is not.
	 */
	boolean add(Observer o);
	
	/**
	 * Removes an object from a subject.
	 * @param o The object to be removed.
	 * @return Returns true if successfully removed.
	 */
	boolean remove(Observer o);
	
	/**
	 * Notifies all observers by calling the observer's update method.
	 */
	void notifyObservers(Object o);
}
