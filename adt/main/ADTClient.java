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
import utilities.Configuration;
import utilities.DebugUtility;

/**
 * An ADTClient is a thread started when a Rundown is opened.
 * 
 * The ADT Client thread will process all messages from the server to keep the
 * Rundowns synchronized across multiple users.
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

	private boolean connected = false;
	private boolean stopCondition = false;

	/**
	 * @return ADTClient ID
	 */
	public int getSessionID() {
		return this.sessionID;
	}

	/**
	 * Try to connect this client with the server
	 * 
	 * @return true iff socket is connected to a server
	 */
	private boolean tryConnect() {
		boolean success = false;
		try {
			this.socket = new Socket(Configuration.getServerAddress(), Configuration.portNum);
			this.output = new ObjectOutputStream(this.socket.getOutputStream());
			this.output.writeObject(new ADTEstablishMessage(-1));
			this.input = new ObjectInputStream(this.socket.getInputStream());

			/**
			 * @todo -- this success should check if the establish message receives a
			 *       response or somehow otherwise checks to verify the right server server
			 */
			success = true;

		} catch (BindException e) {
			DebugUtility.error(ADTClient.class, "Bind Exception: " + e.getMessage());
		} catch (ConnectException e) {
			// if the connection fails, chances are there's nothing listening
			DebugUtility.trace(ADTClient.class, "Connection refused. " + e.getMessage());
			DebugUtility.trace(ADTClient.class, "Port " + Configuration.portNum + " may have no response.");

			// so we should try and become the host
			new Thread(ADTServer.getInstance()).start();
		} catch (UnknownHostException e) {
			DebugUtility.error(ADTClient.class, "Uknown host: " + e.getMessage());
		} catch (IOException e) {
			DebugUtility.error(ADTClient.class, "Unknown IO Error: " + e.getMessage());
		}
		return success;
	}

	/**
	 * Try to connect to the server.
	 */
	private void connect() {
		this.connected = false;

		while (this.connected == false) {
			DebugUtility.trace(ADTClient.class, "Trying to connect...");

			this.connected = tryConnect();

			DebugUtility.trace(ADTClient.class, "Connection " + (this.connected ? "established" : "failed") + ": port ("
					+ Configuration.portNum + ")");
		}
	}

	/**
	 * Main entry point for the client.
	 * 
	 * run() is called as a result of start()
	 */
	@Override
	public void run() {

		// do the client/server connection
		this.connect();

		// then keep reading messages
		while (!this.stopCondition) {
			try {
				// get the next message and process it
				ADTBaseMessage msg = (ADTBaseMessage) (this.input.readObject());
				DebugUtility.trace(ADTClient.class, this.sessionID + " Processing " + msg.getClass().getSimpleName()
						+ " from " + msg.getSender() + ": " + msg.getCommand());
				msg.process();
				RundownFrame.refresh();
			} catch (SocketException e) {
				// or the server connection has been lost
				DebugUtility.error(ADTClient.class, "Lost connection to server. Attempting to re-establish...");
				this.connect();
			} catch (Exception e) {
				// or an unknown error has occurred
				e.printStackTrace();
				DebugUtility.error(ADTClient.class, "Unable to proccess ADTMessage.", e);
			}
		}
	}

	/**
	 * Send a message to the ADT server, with some error checks
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
			DebugUtility.error(ADTClient.class, "Could not output message: " + message.getCommand(), e);
		}
	}

	/**
	 * The server configuration has changed, so the client needs to restart
	 */
	public void newServer() {
		this.sendMessage(new ADTUnlockUserMessage(this.sessionID));
		this.sendMessage(new ADTEndSessionMessage(this.sessionID));

		this.sessionID = -1;
		DebugUtility.debug(ADTClient.class, "Disconnected from server.");
		this.start();
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
	}

	/**
	 * Trigger this client to stop reading messages
	 */
	public void setStop() {
		this.stopCondition = true;
	}
}
