package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import utilities.Configuration;
import utilities.DebugUtility;
import utilities.Output;

/**
 * A server used to communicate between Rundowns.
 */
public class ADTServer extends Thread {

	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static BufferedReader bufferedReader;
	private static int connections = 0;
	private static HashMap<Integer, ADTServerThread> clients;

	private static ADTServer instance = new ADTServer();
	private boolean endCondition;

	private ADTServer() {
		clients = new HashMap<Integer, ADTServerThread>();
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
			serverSocket = new ServerSocket(Configuration.portNum);
			Output.showInfoMessage("Started Server", "Started the rundown server. You are the host.");
		} catch (BindException e3) {
			DebugUtility.error(ADTServer.class, "Port already bound: " + Configuration.portNum);
			serverSocket = null;
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		while (this.endCondition == false && serverSocket != null) {
			try {
				clientSocket = serverSocket.accept();
				// Create a reader
				bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				DebugUtility.debug(ADTServer.class, "Started server thread: " + connections);
				ADTServerThread client = new ADTServerThread(connections, bufferedReader, clientSocket);
				new Thread(client).start();
				clients.put(connections, client);
				client.sendMessage(connections + ",id," + connections);

				client.sendRundown();
				client.sendATOData();
				client.sendLocks();

				connections++;
			} catch (BindException e1) {
				DebugUtility.error(ADTServer.class, "Port " + Configuration.portNum + " already in use.");
			} catch (IOException e) {
				DebugUtility.error(ADTServer.class, e.getMessage());
			}
		}
		try {
			if (serverSocket != null) {
				serverSocket.close();
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
	public static void sendMessage(String message, Integer origin) {
		for (Integer key : clients.keySet()) {
			if (key != origin) {
				clients.get(key).sendMessage(message);
				DebugUtility.trace(ADTServer.class, "Sent " + message + " to " + key);
			}
		}
	}

	/**
	 * Stop sending messages to the given client.
	 * 
	 * @param id
	 */
	public void removeClient(int id) {
		clients.remove(id);
	}

	/**
	 * Set flag to end the server
	 */
	public void setStop() {
		this.endCondition = true;
		for (int client : clients.keySet()) {
			clients.get(client).sendMessage("-1,end");
		}
	}
}
