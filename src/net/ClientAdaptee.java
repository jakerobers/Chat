package net;

import java.io.IOException;

/**
 * The concrete implementation of this interface must create a "new ClientAdapter(this)". The concrete implementation must
 * be passed in as a parameter so that the event methods can be called. TThe following methods below are the "events" that 
 * the adapter will call (with exception to connect and disconnect). Connect and disconnect are convenient methods that can 
 * be used from the views so that the adapter is not referenced directly.
 * @author jake
 *
 */
public interface ClientAdaptee {
	
	/**
	 * This method should call the connect method in ClientAdapter.
	 * @param url The url to connect to.
	 * @param port The port to connect to.
	 * @throws IOException 
	 */
	void connect(String url, int port) throws IOException;
	
	/**
	 * This method should call the disconnect method in ClientAdapter.
	 * @throws IOException 
	 */
	void disconnect() throws IOException;
	
	/**
	 * Sends an object to a server
	 * @param o The object to be sent.
	 * @throws IOException 
	 */
	void send(Object o) throws IOException;
	
	/**
	 * The event called when a new datagram is received.
	 * @param o
	 */
	void datagramEvent(Object o);
	
	/**
	 * An event called when there is an error connecting to the server.
	 */
	void connectionErrorEvent();
	
}
