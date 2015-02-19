package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.ClientApp;

public class ClientAdapter {
	
	private ClientApp ctrl;
	private Socket socket;
	private ObjectOutputStream out;
	private String url;
	private int port;
	
	public ClientAdapter(ClientApp ctrl) {
		this.ctrl = ctrl;
	}
	
	public void connect(String url, int port) throws IOException {
		this.url = url;
		this.port = port;
		this.socket = new Socket(url, port);
		this.out = new ObjectOutputStream(this.socket.getOutputStream());
		
		this.handshake();
		new DatagramListener(socket).start();		
		
	}
	
	public void disconnect() throws IOException {
		this.socket.close();
		this.socket = null;
	}

	public void send(Object o) throws IOException {
		this.out.writeObject(o);
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public boolean isConnected() {
		if ( this.socket == null ) {
			return false;
		}
		return this.socket.isConnected();
	}
	
	/**
	 * Sends a message to the server signaling that it wants to connect.
	 */
	private void handshake() {
		try {
			this.send("handshake");
		} catch (IOException e) {
			ctrl.connectionErrorEvent();
		}
	}
	
	/**
	 * A threaded private class that will wait for new messages. When a new message arrives, it will
	 * notify the controller.
	 * @author jake
	 *
	 */
	private class DatagramListener extends Thread {
		
		Socket s;
		
		public DatagramListener(Socket s) {
			this.s = s;
		}
		
		public void run() {
			ObjectInputStream in = null;
			boolean isRunning = true;
			
			try {
				in = new ObjectInputStream(this.s.getInputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			while( socket.isConnected() && isRunning ) {
				try {
					ctrl.datagramEvent(in.readObject());
				} catch (ClassNotFoundException e) {
					System.out.println("Unable to read the message received.");
					e.printStackTrace();
				} catch ( IOException e1) {
					isRunning = false;
				}
			}
		}
	}
}
