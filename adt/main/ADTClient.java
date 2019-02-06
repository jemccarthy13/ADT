package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import messages.ADTBaseMessage;
import messages.ADTEndSessionMessage;
import messages.ADTEstablishMessage;
import messages.ADTUnlockUserMessage;
import rundown.gui.RundownFrame;
import server.ADTServer;
import swing.GUI;
import utilities.Configuration;
import utilities.DebugUtility;

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

	private boolean endSession;

	private boolean connected = false;

	/**
	 * @return ADTClient ID
	 */
	public int getSessionID() {
		return this.sessionID;
	}

	/**
	 * Try to connect to the server.
	 */
	private void connect() {
		this.connected = false;

		while (this.connected == false) {
			DebugUtility.trace(ADTClient.class, "Trying to connect...");
			// connect to the socket and setup reader/writer
			try {
				this.socket = new Socket(Configuration.getServerAddress(), Configuration.portNum);
				this.output = new ObjectOutputStream(this.socket.getOutputStream());
				this.output.writeObject(new ADTEstablishMessage(-1));
				this.input = new ObjectInputStream(this.socket.getInputStream());

				this.connected = true;

			} catch (BindException e) {
				DebugUtility.error(ADTClient.class, "Bind Exception: " + e.getMessage());
			} catch (ConnectException e) {
				// if the connection fails, chances are there's nothing listening
				DebugUtility.trace(ADTClient.class, "Connection refused.");
				DebugUtility.trace(ADTClient.class, "Port " + Configuration.portNum + " may have no response.");

				// so we should try and become the host
				tryStartServer();

			} catch (UnknownHostException e) {
				DebugUtility.error(ADTClient.class, "Uknown host: " + e.getMessage());
			} catch (IOException e) {
				DebugUtility.error(ADTClient.class, "Unknown IO Error: " + e.getMessage());
			}

			DebugUtility.trace(ADTClient.class, "Connection " + (this.connected ? "established" : "failed") + ": port ("
					+ Configuration.portNum + ")");
		}
	}

	/**
	 * Assumption: no server connection has been made yet, so we need to try and
	 * start the server.
	 */
	public void tryStartServer() {
		try {
			DebugUtility.trace(ADTClient.class, "Trying to start server...");
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

		this.connect();

		while (!this.endSession) {
			try {
				ADTBaseMessage msg = (ADTBaseMessage) (this.input.readObject());
				processLine(msg);
			} catch (SocketException e) {
				DebugUtility.error(ADTClient.class, "Lost connection to server. Attempting to re-establish...");
				this.socket = null;
				this.output = null;
				this.input = null;
				this.connect();
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
			DebugUtility.debug(ADTClient.class, this.sessionID + " disconnected.");
		} catch (IOException e) {
			e.printStackTrace();
			DebugUtility.error(ADTClient.class, "Failed to close connection socket.");
		}
	}

	/**
	 * Read a line and process the received command.
	 * 
	 * @param msg - the next message to process
	 */
	public void processLine(ADTBaseMessage msg) {
		DebugUtility.trace(ADTClient.class, this.sessionID + " Processing " + msg.getClass().getSimpleName() + " from "
				+ msg.getSender() + ": " + msg.getCommand());

		msg.process();

		if (msg.getCommand().contains("end")) {
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
			DebugUtility.trace(ADTClient.class, "Sent message: " + message.getSender() + "," + message.getCommand());

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
		this.sendMessage(new ADTUnlockUserMessage(this.sessionID));
		this.sendMessage(new ADTEndSessionMessage(this.sessionID));

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

	/**
	 * @return true iff client is connected to an ADTServer
	 */
	public boolean isConnected() {
		return this.connected;
	}

	/**
	 * A session ID for this client
	 * 
	 * @param id - the new ID for the client
	 */
	public void setSessionID(int id) {
		this.sessionID = id;
		this.connected = true;
	}
}
