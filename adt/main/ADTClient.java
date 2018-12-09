package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

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
	private PrintWriter printWriter = null;
	/** reader for reading messages */
	private BufferedReader reader = null;
	/** stores connection state */
	private boolean established = false;

	private boolean endSession;

	/**
	 * Construct a new client
	 */
	public ADTClient() {
	}

	/**
	 * @return ADTClient ID
	 */
	public int getSessionID() {
		return this.sessionID;
	}

	/**
	 * Setup and start the session
	 */
	public void newSession() {

		// call the run part of the thread
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
	public void connect() {
		int counter = 0;
		while ((this.socket == null || this.printWriter == null) && counter < 1000) {
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
		this.socket = new Socket("localhost", Configuration.portNum);
		this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
		this.printWriter.println("establish");

		this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		this.established = true;

		DebugUtility.debug(ADTClient.class, "Connection established: port (" + Configuration.portNum + ")");
	}

	/**
	 * Assumption: no server connection has been made yet, so we need to try and
	 * start the server.
	 */
	public static void tryStartServer() {
		try {
			DebugUtility.debug(ADTClient.class, "Trying to start server...");
			ADTServer.getInstance().start();
		} catch (IllegalThreadStateException e) {
			DebugUtility.error("Illegal server thread state.");
			DebugUtility.error("Resetting server thread.");
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
		connect();

		String inputLine;

		while (!this.endSession) {
			try {
				inputLine = this.reader.readLine();
				processLine(inputLine);
			} catch (SocketException e) {
				DebugUtility.error("Lost connection to server. Attempting to re-establish...");
				this.socket = null;
				this.printWriter = null;
				this.reader = null;
				connect();
			} catch (IOException e) {
				DebugUtility.error(this.sessionID + " " + e.getMessage());
			} catch (NullPointerException e1) {
				DebugUtility.error(this.sessionID + " " + e1.getMessage());
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
	 * @param inputLine - the next command to process
	 */
	public void processLine(String inputLine) {
		DebugUtility.debug(ADTClient.class, this.sessionID + " Processing " + inputLine);

		String[] data = inputLine.split(",");
		String command = data[1];

		if (command.equals("id")) {
			this.sessionID = Integer.parseInt(data[2]);
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
			this.endSession = true;
		}
		GUI.FRAMES.getInstanceOf(RundownFrame.class).repaint();
	}

	/**
	 * Send a message to the game server.
	 * 
	 * @param string - the message to send
	 */
	public void sendMessage(String string) {
		DebugUtility.debug(ADTClient.class, "Sending message: " + this.sessionID + string);
		try {
			this.printWriter.println(this.sessionID + "," + string);
		} catch (NullPointerException e) {
			DebugUtility.error(ADTClient.class, "Server/client connection wasn't established.");
			DebugUtility.error(ADTClient.class, "Could not send message.");
		}
	}

	/**
	 * Disconnect from the session
	 */
	public void endSession() {
		this.sendMessage("unlock all");
		this.sendMessage("end");
		this.sessionID = -1;
		this.endSession = true;
		DebugUtility.debug(ADTClient.class, "Disconnected from server.");
	}
}
