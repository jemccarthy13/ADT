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

import rundown.RundownTableModel;
import server.ADTServer;
import structures.ATOAssets;
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
	public void establishSession() {

		// call the run part of the thread
		DebugUtility.printDebug("(Client) Establishing session with ADT server...");
		this.start();

		// wait until the connection is established before proceeding
		while (!this.established) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * While the socket and printWriter are unavailable, try to establish a
	 * connection
	 */
	public void establishConnection() {
		while (this.socket == null || this.printWriter == null) {
			try {
				DebugUtility.logMessage("(Client) Trying to establish connection...");
				tryConnect();
			} catch (BindException e) {
				DebugUtility.printError("(Client) Bind Exception: " + e.getMessage());
			} catch (ConnectException e) {
				DebugUtility.printError("(Client) " + e.getMessage());
				DebugUtility.printError("(Client) Connection refused.");
				DebugUtility.printError("(Client) Port " + Configuration.portNum + " may have no response.");

				tryStartServer();
			} catch (UnknownHostException e) {
				DebugUtility.printError("(Client) Uknown host: " + e.getMessage());
			} catch (IOException e) {
				DebugUtility.printError("(Client) IO Error: " + e.getMessage());
			}
		}
	}

	/**
	 * Try to connect to the server.
	 * 
	 * @throws UnknownHostException
	 *             - if host isn't known (localhost should never cause this)
	 * @throws IOException
	 *             - if there are any IO errors with the BufferedReader
	 * @throws ConnectException
	 *             - if there are any issues connecting to the socket
	 */
	public void tryConnect() throws UnknownHostException, IOException, ConnectException {
		this.socket = null;
		this.printWriter = null;

		this.socket = new Socket("localhost", Configuration.portNum);
		this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
		this.printWriter.println("establish");

		this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		this.established = true;

		DebugUtility.printDebug("(Client) Connection established: port (" + Configuration.portNum + ")");
	}

	/**
	 * Assumption: no server connection has been made yet, so we need to try and
	 * start the server.
	 */
	public static void tryStartServer() {
		try {
			DebugUtility.printDebug("(Client) Trying to start server...");
			ADTServer.getInstance().start();
		} catch (IllegalThreadStateException e) {
			DebugUtility.printError("(Client) Illegal server thread state.");
			DebugUtility.printError("(Client) Resetting server thread.");
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

		String inputLine;
		boolean end = false;

		while (!end) {
			try {
				inputLine = this.reader.readLine();
				end = processLine(inputLine);
			} catch (SocketException e) {
				DebugUtility.printError("Lost connection to server. Attempting to re-establish...");
				this.socket = null;
				this.printWriter = null;
				this.reader = null;
				establishConnection();
			} catch (IOException e) {
				DebugUtility.printError("(Client) " + e.getMessage());
			} catch (NullPointerException e1) {
				DebugUtility.printError("(Client) " + e1.getMessage());
			}
		}
		if (end) {
			DebugUtility.printDebug("(Client) " + this.sessionID + " disconnected.");
		}
	}

	/**
	 * Read a line and process the received command.
	 * 
	 * @param inputLine
	 *            - the next command to process
	 * @return true if the server session should end (end command received)
	 */
	public boolean processLine(String inputLine) {
		boolean end = false;
		DebugUtility.logMessage("- (Client) Processing " + inputLine);

		String[] data = inputLine.split(",");
		String command = data[1];

		if (command.equals("id")) {
			this.sessionID = Integer.parseInt(data[2]);
			DebugUtility.printDebug("(Client) Joined as user: " + this.sessionID);
			Output.showInfoMessage("Connected", "You are now connected to the host and will receive updates.");

		} else if (command.equals("set")) {
			String val = data[2];
			int row = Integer.parseInt(data[3]);
			int col = Integer.parseInt(data[4]);

			RundownTableModel.setValueAt(val, row, col, false, false);
			RundownFrame.getInstance().repaint();
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
			RundownTableModel.getInstance().unlockUser(user);
		} else if (command.equals("unlocked")) {
			Integer user = Integer.parseInt(data[0]);
			Integer row = Integer.parseInt(data[2]);
			Integer column = Integer.parseInt(data[3]);
			RundownTableModel.getInstance().setUnlocked(user, row, column);
		} else if (command.equals("locked")) {
			Integer user = Integer.parseInt(data[0]);
			Integer row = Integer.parseInt(data[2]);
			Integer column = Integer.parseInt(data[3]);
			RundownTableModel.getInstance().setLocked(user, row, column);
		} else if (command.equals("end")) {
			end = true;
		}
		RundownFrame.getInstance().repaint();
		return end;
	}

	/**
	 * Send a message to the game server.
	 * 
	 * @param string
	 *            - the message to send
	 */
	public void sendMessage(String string) {
		DebugUtility.logMessage("(Client) Sending message: " + string);
		try {
			this.printWriter.println(this.sessionID + "," + string);
		} catch (NullPointerException e) {
			DebugUtility.printError("(Client) Server/client connection wasn't established.");
			DebugUtility.printError("(Client) Could not send message.");
		}
	}

	/**
	 * Disconnect from the session
	 */
	public void endSession() {
		this.sendMessage("end");
		DebugUtility.printDebug("(Client) Disconnected from server.");
	}
}
