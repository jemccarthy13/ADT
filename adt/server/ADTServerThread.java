package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import rundown.model.RundownTableModel;
import structures.LockedCells;
import swing.GUI;
import utilities.Configuration;
import utilities.DebugUtility;

/**
 * A thread to read server socket input.
 */
public class ADTServerThread extends Thread {

	private int id;
	private BufferedReader input;
	private Socket socket;
	private PrintWriter printWriter;

	/**
	 * Constructor for a server thread.
	 * 
	 * @param id     - the ID number of the thread
	 * @param reader - input to process
	 * @param socket - the connection socket to create a writer for
	 */
	public ADTServerThread(int id, BufferedReader reader, Socket socket) {
		this.input = reader;
		this.id = id;
		this.socket = socket;
		try {
			this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
		} catch (IOException e) {
			DebugUtility.error(ADTServerThread.class, "Unable to create printWriter");
		}
	}

	/**
	 * Start the server.
	 */
	@Override
	public synchronized void start() {
		// Get the client message
		String inputLine = "";
		while (inputLine != null && !inputLine.contains("end")) {
			try {
				inputLine = this.input.readLine();
			} catch (IOException e) {
				DebugUtility.error(ADTServerThread.class, "Unable to readline! User:" + this.id);
			}
			if (inputLine != null) {
				if (inputLine.contains("establish")) {
					DebugUtility.debug(ADTServerThread.class, "Server thread started. User: " + this.id);
				} else if (inputLine.contains("end")) {
					DebugUtility.debug(ADTServerThread.class, "User " + this.id + " left session.");
					ADTServer.getInstance().removeClient(this.id);
					this.interrupt();
				} else {
					DebugUtility.trace(ADTServerThread.class, "User " + this.id + " sending command: " + inputLine);
					String[] msgArr = inputLine.split(",");
					ADTServer.sendMessage(inputLine, Integer.parseInt(msgArr[0]));
				}
			}
		}
	}

	/**
	 * Run the server
	 */
	@Override
	public void run() {
		start();
	}

	/**
	 * Send a message to the game server.
	 * 
	 * @param string - the message to send
	 */
	public void sendMessage(String string) {
		try {
			this.printWriter.println(string);
		} catch (NullPointerException e) {
			DebugUtility.error(ADTServerThread.class, "Server/client connection wasn't established.");
		}
	}

	/**
	 * Typically done on ATO import or client connect, this method will send the
	 * location of the .DAT file so other clients can load the ATO contents without
	 * the processing time
	 */
	public void sendATOData() {
		sendMessage("-1,atodat," + Configuration.getInstance().getATODatFileLoc());
	}

	/**
	 * Typically done on client connect, this method will send the current list of
	 * locked cells to the newly joined client.
	 */
	public void sendLocks() {
		HashMap<Integer, Integer[]> lockedCells = LockedCells.getLockedCells();
		for (Integer key : lockedCells.keySet()) {
			sendMessage(key + ",locked," + lockedCells.get(key)[0] + "," + lockedCells.get(key)[1]);
		}
	}

	/**
	 * Done on initial client connect, this sends the rundown to the client.
	 */
	public void sendRundown() {
		RundownTableModel model = (RundownTableModel) GUI.MODELS.getInstanceOf(RundownTableModel.class);
		for (int r = 0; r < model.getRowCount(); r++) {
			for (int c = 0; c < model.getColumnCount(); c++) {
				sendMessage("-1,set," + model.getValueAt(r, c) + "," + r + "," + c);
			}
		}
	}
}
