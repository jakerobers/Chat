package client;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import models.Message;
import net.ClientAdaptee;
import net.ClientAdapter;
import client.gui.Gui;

public class ClientApp implements ClientAdaptee {
	
	/**
	 * The history of the conversation
	 */
	private volatile List<Message> messageHistory;
	
	/**
	 * Messages that have been sent, but we have not received confirmation of delivery.
	 */
	private volatile List<Message> messagesOutstanding;
	
	/**
	 * The adapter used to talk to the server.
	 */
	private ClientAdapter adapter;
	
	/**
	 * The gui instance.
	 */
	private GuiLoader guiLoader;
	
	
	/**
	 * The client app instance.
	 */
	private ClientApp() {
		this.adapter = new ClientAdapter(this);
		this.messagesOutstanding = new LinkedList<>();
		this.messageHistory = new LinkedList<>();
		
		this.guiLoader = new GuiLoader(this);
		this.guiLoader.start();
	}
	
	public void connect(String url, int port) throws IOException {
		this.adapter.connect(url, port);
	}
	
	public void disconnect() throws IOException {
		this.adapter.disconnect();
	}
	
	/**
	 * An event trigger that will prompt the gui to update with the new information.
	 */
	public void notifyView(Object o) {
		Message m = (Message) o;
		synchronized ( m ) {
			this.guiLoader.getGui().update(m);
		}
	}
	
	/**
	 * Sends a message.
	 * @throws IOException 
	 */
	@Override
	public void send(Object o) throws IOException {
		Message m = (Message) o;
		this.adapter.send(m.toJson());
	}
	
	/**
	 * @return true -> socket is connected
	 * 		   false -> socket is disconnected.
	 */
	public boolean isConnected() {
		return this.adapter.isConnected();
	}
	
	
	/**
	 * This is called by the ClientAdapter. It triggers an action when a connection is received.
	 */
	@Override
	public void datagramEvent(Object o) {
		Message m = new Message().fromJson(o.toString());
		
		//false -> we received confirmation that our message successfully sent.
		//true -> we received a new message from someone else.
		if ( !this.messagesOutstanding.remove(m) ) {
			this.messageHistory.add(m);
			notifyView(m);
		}
	}
	
	/**
	 * This is an error that occurs when the adapter cannot send the handshake to the remote server. 
	 */
	@Override
	public void connectionErrorEvent() {
		this.guiLoader.getGui().errorMessage("Unable to connect to the server.");
	}
	
	
	private class GuiLoader extends Thread {
		private ClientApp ctrl;
		private Gui gui;
		
		
		public GuiLoader(ClientApp ctrl) {
			this.ctrl = ctrl;
		}
		
		public void run() {
			this.gui = new Gui(ctrl);
			this.gui.revalidate();
			this.gui.repaint();
		}
		
		public Gui getGui() {
			return this.gui;
		}
	}
	
	/**
	 * Main method
	 * @param args ignored.
	 */
	public static void main(String[] args) {
		new ClientApp();
	}
}
