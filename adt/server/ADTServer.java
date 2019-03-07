package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import messages.ADTBaseMessage;
import messages.ADTEndSessionMessage;
import messages.ADTIdMessage;
import utilities.Configuration;
import utilities.DebugUtility;

/**
 * A server used to communicate between Rundowns.
 * 
 * @todo - this class is the point at which a programmer would interface with
 *       external agencies.
 * 
 *       -- A new socket would be created to link to external source (i.e.
 *       datalink terminal)
 * 
 *       -- This new socket, and associated input/output streams would listen
 *       for incoming link messages of the correct type
 * 
 *       -- Having received a message, translate JXX.X to a ADTXXMessage and
 *       process as usual
 * 
 *       -- The rundown would have to change to have a right click option to
 *       "Publish Approval" which would publish datalink messages of the
 *       appropriate type utilizing this Server function "sendLinkMessage()"
 */
public class ADTServer extends Thread {

	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ObjectInputStream bufferedReader;
	private int connections = 0;
	private HashMap<Integer, ADTServerThread> clients;

	private static ADTServer instance = new ADTServer();
	private boolean endCondition;

	private ADTServer() {
		this.clients = new HashMap<Integer, ADTServerThread>();
	}

	/**
	 * Singleton implementation.
	 * 
	 * @return single instance
	 */
	public static ADTServer getInstance() {
		return instance;
	}

	/**
	 * Since ADT server threads can only be started once, the reset flags the
	 * previous instance for garbage collection (eventually) and creates a new
	 * thread to .start if needed.
	 */
	public static void resetInstance() {
		instance.endCondition = true;
		instance = new ADTServer();
		instance.start();
	}

	/**
	 * Main entry point for the server
	 * 
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		instance.start();
	}

	/**
	 * Start the ADT Server.
	 * 
	 * @Note should not be called directly
	 */
	@Override
	public void run() {
		try {
			this.serverSocket = new ServerSocket(Configuration.portNum);
			DebugUtility.debug(ADTServer.class, "Started the rundown server. You are the host.");
		} catch (BindException e3) {
			DebugUtility.error(ADTServer.class, "Port already bound: " + Configuration.portNum);
			this.serverSocket = null;
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		while (this.endCondition == false && this.serverSocket != null) {
			try {
				this.clientSocket = this.serverSocket.accept();
				// Create a reader
				this.bufferedReader = new ObjectInputStream(this.clientSocket.getInputStream());
				DebugUtility.debug(ADTServer.class, "Started server thread: " + this.connections);
				ADTServerThread client = new ADTServerThread(this.connections, this.bufferedReader, this.clientSocket);
				new Thread(client).start();
				this.clients.put(this.connections, client);
				client.sendMessage(new ADTIdMessage(this.connections));

				client.sendRundown();
				client.sendATOData();
				client.sendLocks();

				this.connections++;
			} catch (BindException e1) {
				DebugUtility.error(ADTServer.class, "Port " + Configuration.portNum + " already in use.");
			} catch (IOException e) {
				DebugUtility.error(ADTServer.class, e.getMessage());
			}
		}
		try {
			if (this.serverSocket != null) {
				this.serverSocket.close();
			}
		} catch (IOException e) {
			// idk
		}
		DebugUtility.debug(ADTServer.class, "Stopped");
	}

	/**
	 * Send a message to all registered clients.
	 * 
	 * @param message - the message to send
	 * @param origin  - the originator of the message
	 */
	public static void sendMessage(ADTBaseMessage message) {
		DebugUtility.debug(ADTServer.class, "Forwarding " + message.getCommand() + " from " + message.getSender());
		for (Integer key : instance.clients.keySet()) {
			if (key != message.getSender()) {
				instance.clients.get(key).sendMessage(message);
				DebugUtility.trace(ADTServer.class, "Sent " + message + " to " + key);
			}
		}
	}

	/**
	 * Send a message through datalink to other agencies
	 * 
	 * @todo - this function should communicate with Boeing's messaging library to
	 *       send/receive datalink messages
	 * 
	 * @todo - for real, this should just send API calls to a mediator or straight
	 *       to library
	 * 
	 * @param message - the internal rundown message, to translate to a link16
	 *                J-series message
	 */
	public static void sendLinkMessage(ADTBaseMessage message) {
		DebugUtility.error(ADTServer.class, "Link 16 messaging not implemented.");
	}

	/**
	 * Stop sending messages to the given client.
	 * 
	 * @param id
	 */
	public void removeClient(int id) {
		this.clients.remove(id);
	}

	/**
	 * Set flag to end the server
	 */
	public void setStop() {
		this.endCondition = true;
		for (int client : this.clients.keySet()) {
			this.clients.get(client).sendMessage(new ADTEndSessionMessage(-1));
		}
	}
}
