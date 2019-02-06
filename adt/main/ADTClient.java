package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import messages.ADTBaseMessage;
import messages.ADTEndSessionMessage;
import messages.ADTEstablishMessage;
import messages.ADTUnlockAllMessage;
import rundown.gui.RundownFrame;
import rundown.model.RundownTable;
import rundown.model.RundownTableModel;
import server.ADTServer;
import structures.ATOAssets;
import structures.LockedCells;
import swing.GUI;
import utilities.Configuration;
import utilities.DebugUtility;
import utilities.Output;

/**
 * An ADTClient is a thread started when a Rundown is opened.
 * 
 * The ADT Client thread will process all messages from the server to keep the
 * Rundowns in sync across multiple users.
 */
public class ADTClient extends Thread {

	/** The session/ID for this client */
	private int sessionID = -1;

	/** The socket connected to the server */
	private Socket socket = null;

	/** printWriter for sending messages */
	private ObjectOutputStream output = null;

	/** reader for reading messages */
	private ObjectInputStream input = null;

	/** stores connection state */
	private boolean established = false;

	private boolean endSession;

	/**
	 * @return ADTClient ID
	 */
	public int getSessionID() {
		return this.sessionID;
	}

	/**
	 * Setup and start the session
	 * 
	 * Will wait for a successful connection before leaving this function, so that
	 * any other part of the application doesn't accidentally use a client w/o
	 * connection
	 */
	public void establishSession() {

		// start the thread (same as run())
		DebugUtility.debug(ADTClient.class, "Establishing session with ADT server...");
		this.start();

		// wait until the connection is established before proceeding
		while (!this.established) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/** TODO - verify the correct session w/the right server */
	}

	/**
	 * While the socket and printWriter are unavailable, try to establish a
	 * connection
	 */
	public void establishConnection() {
		while (this.socket == null || this.output == null) {
			try {
				DebugUtility.debug(ADTClient.class, "Trying to establish connection...");
				tryConnect();
			} catch (BindException e) {
				DebugUtility.error(ADTClient.class, "Bind Exception: " + e.getMessage());
			} catch (ConnectException e) {
				DebugUtility.error(ADTClient.class, "Connection refused.");
				DebugUtility.error(ADTClient.class, "Port " + Configuration.portNum + " may have no response.");

				tryStartServer();
			} catch (UnknownHostException e) {
				DebugUtility.error(ADTClient.class, "Uknown host: " + e.getMessage());
			} catch (IOException e) {
				DebugUtility.error(ADTClient.class, "IO Error: " + e.getMessage());
			}
		}
	}

	/**
	 * Try to connect to the server.
	 * 
	 * @throws UnknownHostException - if host isn't known (localhost should never
	 *                              cause this)
	 * @throws IOException          - if there are any IO errors with the
	 *                              BufferedReader
	 * @throws ConnectException     - if there are any issues connecting to the
	 *                              socket
	 */
	private void tryConnect() throws UnknownHostException, IOException, ConnectException {
		if (Configuration.serverAddr.equals("")) {
			Configuration.serverAddr = JOptionPane.showInputDialog(null, "Server address:");
		}
		this.socket = new Socket(Configuration.serverAddr, Configuration.portNum);

		this.output = new ObjectOutputStream(this.socket.getOutputStream());
		this.output.writeObject(new ADTEstablishMessage(-1));
		try {
			this.input = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {

			e.printStackTrace();
			System.exit(0);
		}
		this.established = true;

		DebugUtility.debug(ADTClient.class, "Connection established: port (" + Configuration.portNum + ")");
	}

	/**
	 * Assumption: no server connection has been made yet, so we need to try and
	 * start the server.
	 */
	public void tryStartServer() {
		try {
			DebugUtility.debug(ADTClient.class, "Trying to start server...");
			ADTServer.getInstance().start();
		} catch (IllegalThreadStateException e) {
			DebugUtility.error(ADTClient.class, "Illegal server thread state.");
			DebugUtility.error(ADTClient.class, "Resetting server thread.");
			ADTServer.getInstance().interrupt();
			ADTServer.resetInstance();
		}
	}

	/**
	 * Main entry point for the client.
	 * 
	 */
	@Override
	public void run() {
		establishConnection();

		String command;

		while (!this.endSession) {
			try {
				ADTBaseMessage msg = (ADTBaseMessage) (this.input.readObject());
				command = msg.getCommand();
				processLine(msg);
				// processLine(msg.getSender() + "," + command);
				DebugUtility.trace(ADTClient.class, "Command: " + command);
			} catch (SocketException e) {
				DebugUtility.error(ADTClient.class, "Lost connection to server. Attempting to re-establish...");
				this.socket = null;
				this.output = null;
				this.input = null;
				establishConnection();
			} catch (IOException e) {
				e.printStackTrace();
				DebugUtility.error(ADTClient.class, this.sessionID + " " + e.getMessage());
			} catch (NullPointerException e1) {
				e1.printStackTrace();
				DebugUtility.error(ADTClient.class, this.sessionID + " " + e1.getMessage());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				DebugUtility.error(ADTClient.class, this.sessionID + " " + e);
			}
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DebugUtility.debug(ADTClient.class, this.sessionID + " disconnected.");
	}

	/**
	 * Read a line and process the received command.
	 * 
	 * @param msg - the next message to process
	 */
	public void processLine(ADTBaseMessage msg) {
		String inputLine = msg.getSender() + "," + msg.getCommand();
		DebugUtility.trace(ADTClient.class, this.sessionID + " Processing " + inputLine);

		String[] data = inputLine.split(",");
		String command = data[1];
		if (command.equals("forced")) {
			DebugUtility.debug(getClass(), "Forced unlock all");
			RundownTable.getInstance().editCellAt(-1, -1);
			LockedCells.getLockedCells().clear();
			for (Integer user : LockedCells.getLockedCells().keySet()) {
				this.sendMessage(new ADTUnlockAllMessage(this.sessionID));
				LockedCells.unlockUser(user);
			}
		} else if (command.equals("id")) {
			this.sessionID = Integer.parseInt(data[2]);
			GUI.FRAMES.getInstanceOf(RundownFrame.class)
					.setTitle("Airspace Deconfliction Tool - User " + this.sessionID);
			DebugUtility.debug(ADTClient.class, "Joined as user: " + this.sessionID);
			Output.showInfoMessage("Connected", "You are now connected to the host and will receive updates.");

		} else if (command.equals("set")) {
			String val = data[2];
			int row = Integer.parseInt(data[3]);
			int col = Integer.parseInt(data[4]);

			((RundownTableModel) GUI.MODELS.getInstanceOf(RundownTableModel.class)).setValueAt(val, row, col, false,
					false);
			GUI.FRAMES.getInstanceOf(RundownFrame.class).repaint();
		} else if (command.equals("atodat")) {
			if (!data[2].trim().equals("")) {
				File f = new File(data[2]);
				try {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
					ATOAssets.resetInstance((ATOAssets) (ois.readObject()));
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				Configuration.getInstance().setATODatFileLoc(f.getAbsolutePath());
			}
		} else if (command.equals("unlock all")) {
			Integer user = Integer.parseInt(data[0]);
			LockedCells.unlockUser(user);
		} else if (command.equals("unlocked")) {
			Integer user = Integer.parseInt(data[0]);
			Integer row = Integer.parseInt(data[2]);
			Integer column = Integer.parseInt(data[3]);
			LockedCells.setUnlocked(user, row, column);
		} else if (command.equals("locked")) {
			Integer user = Integer.parseInt(data[0]);
			Integer row = Integer.parseInt(data[2]);
			Integer column = Integer.parseInt(data[3]);
			LockedCells.setLocked(user, row, column);
		} else if (command.equals("end")) {
			// this.endSession = true;
		}
		GUI.FRAMES.getInstanceOf(RundownFrame.class).repaint();
	}

	/**
	 * Send a message to the ADT server.
	 * 
	 * @param message - the message to forward
	 */
	public void sendMessage(ADTBaseMessage message) {
		try {
			this.output.writeObject(message);
			DebugUtility.trace(ADTClient.class,
					"Sent message: " + message.getSender() + "," + message.getCommand());

		} catch (NullPointerException e) {
			DebugUtility.error(ADTClient.class, "Server/client connection wasn't established.");
			DebugUtility.error(ADTClient.class, "Could not send message.");
		} catch (IOException e) {
			DebugUtility.error(ADTClient.class, "Could not output message: " + message.getCommand());
			e.printStackTrace();
		}
	}

	/**
	 * Disconnect from the session
	 */
	public void endSession() {
		this.sendMessage(new ADTUnlockAllMessage(this.sessionID));
		this.sendMessage(new ADTEndSessionMessage(this.sessionID));
		// ADTBaseMessage(this.sessionID, "end"));
		// ADTMessageFactory.createMessage(ADTUnlockAllMessage.class, this.sessionID));

		this.sessionID = -1;
		this.endSession = true;
		DebugUtility.debug(ADTClient.class, "Disconnected from server.");
	}

	/**
	 * The server configuration has changed, so the client needs to restart
	 */
	public void newServer() {
		endSession();
		this.socket = null;
		this.run();
	}
}
