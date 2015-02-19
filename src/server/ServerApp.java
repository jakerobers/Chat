package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import net.ServerAdaptee;
import net.ServerAdapter;
import models.ClientObserver;
import models.ClientSubject;
import models.Message;

/**
 * Starts up a server on a specific port. To change this port, go to the main method.
 * @author jake
 *
 */
public class ServerApp implements ServerAdaptee {

	ServerAdapter adapter;
	private int port;
	private ClientSubject clients;
	
	/**
	 * Starts a server app instance.
	 * @param port The port it will be listening on.
	 */
	private ServerApp(int port) {
		this.port = port;
		this.adapter = new ServerAdapter(this);
		this.clients = new ClientSubject();
	}
	
	private void start() {
		try {
			this.adapter.listen(this.port);
		} catch (IOException e) {
			System.out.println("Error listening on port " + this.port);
			e.printStackTrace();
		}
	}
	
	/**
	 * This is called by the ServerAdapter. It triggers an action when a connection is received.
	 * @param o A reference to the client Socket.
	 */
	@Override
	public void clientEvent(Socket s) {
		this.clients.add(new ClientObserver(this, s));
	}
	
	/**
	 * This is called by the ServerAdapter. It triggers an action when a connection is received.
	 * @param o A reference to the message received.
	 */
	@Override
	public void datagramEvent(Object o) {
		this.clients.notifyObservers(new Message().fromJson(o.toString()));
	}
	
	@Override
	public void send(ObjectOutputStream out, Object o) {
		Message message = (Message) o;
		try {
			this.adapter.send(out, message.toJson());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		int port = 3337;
		new ServerApp(port).start();
	}
}
