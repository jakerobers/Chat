package client.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import models.Message;
import client.ClientApp;

public class Gui extends JFrame {

	private static final long serialVersionUID = -3322209284338235579L;

	private ClientApp ctrl;
	
	private JPanel northPanel;
	private JPanel contentPane;
	private JPanel southPanel;
	private JPanel chatPanel;

	private JScrollPane scrollPane;

	private JButton btnConnect;
	private JButton btnDisconnect;
	private JButton btnSend;

	private JTextField txtUri;
	private JTextField txtPort;
	private JTextField txtUsername;
	private JTextField txtMessage;
	
	private JTextArea chat;

	private JLabel lblUri;
	private JLabel lblPort;
	private JLabel lblNewLabel;

	//These are set after a user connects
	private JLabel uriLabel;
	private JLabel portLabel;
	private JLabel usernameLabel;

	/**
	 * Launch the application for testing.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Gui(ClientApp ctrl) {
		this.ctrl = ctrl;
		configureFrame();
		constructNorthPanel();
		constructChatBox();
		constructSouthPanel();
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		configureFrame();
		constructNorthPanel();
		constructChatBox();
		constructSouthPanel();
	}

	private void constructNorthPanel() {
		northPanel = new JPanel();
		northPanel.setBounds(5, 5, 659, 54);
		contentPane.add(northPanel);
		northPanel.setLayout(null);
		
		uriLabel = new JLabel();
		uriLabel.setBounds(21, 20, 134, 28);
		uriLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		portLabel = new JLabel();
		portLabel.setBounds(167, 20, 134, 28);
		portLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		usernameLabel = new JLabel();
		usernameLabel.setBounds(313, 20, 134, 28);
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblUri = new JLabel("URI");
		lblUri.setHorizontalAlignment(SwingConstants.CENTER);
		lblUri.setBounds(59, 6, 61, 16);
		northPanel.add(lblUri);
		
		lblPort = new JLabel("Port");
		lblPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblPort.setBounds(205, 6, 61, 16);
		northPanel.add(lblPort);
		
		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(347, 6, 76, 16);
		northPanel.add(lblNewLabel);
		
		txtUri = new JTextField("");
		txtUri.setBounds(21, 20, 134, 28);
		northPanel.add(txtUri);
		txtUri.setColumns(10);
		
		txtPort = new JTextField();
		txtPort.setBounds(167, 20, 134, 28);
		northPanel.add(txtPort);
		txtPort.setColumns(10);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(313, 20, 134, 28);
		northPanel.add(txtUsername);
		txtUsername.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(e -> {
			connectAction();
		});
		
		btnConnect.setBounds(510, 21, 143, 29);
		northPanel.add(btnConnect);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.addActionListener(e -> {
			disconnectAction();
		});
		btnDisconnect.setBounds(510, 21, 143, 29);
		
	}

	private void constructChatBox() {
		chatPanel = new JPanel();
		chatPanel.setBounds(5, 71, 659, 356);
		contentPane.add(chatPanel);
		chatPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		chat = new JTextArea();
		chat.setEditable(false);
		scrollPane = new JScrollPane(chat);
		chatPanel.add(scrollPane);
	}

	private void constructSouthPanel() {
		southPanel = new JPanel();
		southPanel.setBounds(5, 428, 659, 54);
		contentPane.add(southPanel);
		southPanel.setLayout(null);
		
		txtMessage = new JTextField();
		txtMessage.setBounds(0, 6, 564, 28);
		txtMessage.addActionListener(e -> {
			sendAction();
		});
		southPanel.add(txtMessage);
		txtMessage.setColumns(10);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(e -> {
			sendAction();
		});
		
		btnSend.setBounds(565, 7, 94, 29);
		southPanel.add(btnSend);
	}

	private void configureFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setBounds(100, 100, 674, 494);
	}
	
	public void update(Message m) {
		chat.append(m.toString() + "\n");
	}
	
	public void errorMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	/**
	 * After the user enters in their information, the information should be changed to labels instead.
	 */
	private void connectReplacements() {
		uriLabel.setText(txtUri.getText());
		portLabel.setText(txtPort.getText());
		usernameLabel.setText(txtUsername.getText());
		
		northPanel.remove(txtUri);
		northPanel.remove(txtPort);
		northPanel.remove(txtUsername);
		
		northPanel.add(uriLabel);
		northPanel.add(portLabel);
		northPanel.add(usernameLabel);
		
		northPanel.remove(btnConnect);
		northPanel.add(btnDisconnect);
		
		northPanel.revalidate();
		northPanel.repaint();
		
	}
	
	/**
	 * After the user clicks disconnet, the following elements should change.
	 */
	private void disconnectReplacements() {
		northPanel.remove(uriLabel);
		northPanel.remove(portLabel);
		northPanel.remove(usernameLabel);
		
		northPanel.add(txtUri);
		northPanel.add(txtPort);
		northPanel.add(txtUsername);
		
		northPanel.remove(btnDisconnect);
		northPanel.add(btnConnect);
		
		northPanel.revalidate();
		northPanel.repaint();
	}
	
	private void sendAction() {
		if ( ctrl != null && txtMessage.getText().length() != 0 ) {
			try {
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(System.currentTimeMillis());
				String time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
				ctrl.send(new Message(txtUsername.getText(), txtMessage.getText(), time));
				txtMessage.setText("");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Error Sending Message");
			}
		}
	}
	
	private void connectAction() {
		if ( !ctrl.isConnected() ) {
			if ( txtUri.getText().length() == 0 || txtPort.getText().length() == 0 || txtUsername.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "All fields need to be filled in.");
				return;
			}
			//Everything looks good.
			
			if ( ctrl != null ) {
				try {
					ctrl.connect(txtUri.getText(), Integer.parseInt(txtPort.getText()));
					connectReplacements();
					chat.append("Connected to " + txtUri.getText() + ":" + txtPort.getText() + "\n");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Invalid port");
					e1.printStackTrace();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Server connection refused.");
					e1.printStackTrace();
				}
			}
		}
	}
	
	private void disconnectAction() {
		if ( ctrl.isConnected() ) {
			try {
				ctrl.disconnect();
				disconnectReplacements();
				chat.append("Disconnected from server.\n");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Unable to disconnect from the server.");
			}
		}
	}
}
