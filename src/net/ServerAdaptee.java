package net;

import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * The concrete implementation of this interface must create a "new ServerAdapter(this)". The concrete implementation must
 * be passed in as a parameter so that the event methods can be called. The following methods below are the "events" that 
 * the adapter will call.
 * @author jake
 *
 */
public interface ServerAdaptee {

	void clientEvent(Socket s);
	void datagramEvent(Object o);
	void send(ObjectOutputStream out, Object o);

}
