package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import server.ServerApp;

public class ServerAdapter {

	private ServerApp ctrl;
	private ServerSocket socket;
	private ClientListener clientListener;
	private int port;
	
	public ServerAdapter(ServerApp ctrl) {
		this.ctrl = ctrl;
	}
	
	public void listen(int port) throws IOException {
		this.port = port;
		this.socket = new ServerSocket(port);
		this.clientListener = new ClientListener(this.socket);
		clientListener.start();
		System.out.println("Listening on Inet " + this.socket.getInetAddress() + ", port " + port + "...");
	}
	
	public void send(ObjectOutputStream out, Object o) throws IOException {
		System.out.println("Sending: " + o.toString() + " to " + out.toString());
		out.writeObject(o);
	}
	
	public int getPort() {
		return this.port;
	}
	
	/**
	 * A threaded inner class that waits for incoming clients to connect to the server. 
	 * It will notify the controller when a new client connects.
	 */
	private class ClientListener extends Thread {
		private ServerSocket socket;
		
		public ClientListener(ServerSocket socket) {
			this.socket = socket;
		}
		
		public void run() {
			while ( true ) {
				try {
					Socket client = this.socket.accept();	//accept the socket.
					new DatagramListener(client).start();	//start listening for messages from the client.
					ctrl.clientEvent(client);
				} catch ( SocketException e1 ) {
					//do nothing
				} catch (IOException e) {
					System.out.println("Unable to accept client connection.");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Propegates all data received to the app.
	 * @author jake
	 *
	 */
	private class DatagramListener extends Thread {
		
		private Socket socket;
		private ObjectInputStream in;
		
		public DatagramListener(Socket s) throws IOException {
			this.socket = s;
			this.in = new ObjectInputStream(s.getInputStream());
		}
		
		public void run() {
			boolean isRunning = true;
			
			while ( this.socket.isConnected() && isRunning ) {
				try {			
					Object o = this.in.readObject();
					if ( o.toString().equals("handshake") ) {
						continue;
					}
					
					System.out.println("Adapter receiving" + o.toString());
					ctrl.datagramEvent(o);
				} catch (ClassNotFoundException e) {
					System.out.println("Server Adapter: Unable to read input from client");
					e.printStackTrace();
				} catch ( IOException e1 ) {
					System.out.println("Server Adapter: " + e1);
					isRunning = false;
				}
			}
			
			try {
				this.in.close();
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 }
