package models;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.ServerApp;

public class ClientObserver implements Observer {

	private Socket socket;
	private ServerApp ctrl;
	private ObjectOutputStream out;
	
	public ClientObserver(ServerApp ctrl, Socket socket) {
		this.ctrl = ctrl;
		this.socket = socket;
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Object o) {
		Message m = (Message) o;
		this.ctrl.send(this.out, m);
	}
	
	public Socket getSocket() {
		return this.socket;
	}
}
