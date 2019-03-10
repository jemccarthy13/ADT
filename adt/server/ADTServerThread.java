package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import messages.ADTAtoDatMessage;
import messages.ADTBaseMessage;
import messages.ADTBlankMessage;
import messages.ADTEndSessionMessage;
import messages.ADTEstablishMessage;
import messages.ADTIdMessage;
import messages.ADTLockedCellsMessage;
import messages.ADTUpdateMessage;
import rundown.model.RundownTableModel;
import structures.LockedCells;
import swing.SingletonHolder;
import utilities.DebugUtility;

/**
 * A thread to read server socket input.
 */
public class ADTServerThread extends Thread {

	private int id;
	private ObjectInputStream input;
	private ObjectOutputStream output;

	/**
	 * Constructor for a server thread.
	 * 
	 * @param id     - the ID number of the thread
	 * @param reader - input to process
	 * @param socket - the connection socket to create a writer for
	 */
	public ADTServerThread(int id, Socket socket) {
		this.id = id;
		try {
			this.input = new ObjectInputStream(socket.getInputStream());
			this.output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			DebugUtility.error(ADTServerThread.class, "Unable to create ServerThread input/output streams for " + id);
		}

		if (this.output != null) {
			this.sendMessage(new ADTIdMessage(id));
			this.sendRundown();
			this.sendATOData();
			this.sendLocks();
		}
	}

	/**
	 * Start the server.
	 */
	@Override
	public synchronized void start() {
		// Get the client message
		ADTBaseMessage msg = new ADTBlankMessage();
		while (!(msg instanceof ADTEndSessionMessage)) {
			try {
				DebugUtility.trace(ADTServerThread.class, "Trying to retrieve msg");
				msg = (ADTBaseMessage) (this.input.readObject());
			} catch (IOException e) {
				/**
				 * On some disconnects this error loops indefinitely. @todo - find out why
				 */
				DebugUtility.error(ADTServerThread.class, "Unable to readline! User:" + this.id);
			} catch (ClassNotFoundException e) {
				DebugUtility.error(ADTServerThread.class, "Could not cast to ADTMessage.");
				e.printStackTrace();
			}
			if (!(msg instanceof ADTEstablishMessage)) {
				DebugUtility.trace(ADTServerThread.class,
						"Trying to forward: " + msg.getCommand() + " from " + msg.getSender());
				ADTServer.sendMessage(msg);
			}

		}

		DebugUtility.debug(ADTServerThread.class, "User " + this.id + " left session.");
		ADTServer.getInstance().removeClient(this.id);
		this.interrupt();
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
	 * @param msg - the message to send
	 */
	public void sendMessage(ADTBaseMessage msg) {
		try {
			DebugUtility.trace(ADTServerThread.class, "Forwarding " + msg.getCommand());
			this.output.writeObject(msg);
		} catch (NullPointerException e) {
			DebugUtility.error(ADTServerThread.class, "Server/client connection wasn't established.");
		} catch (IOException e) {
			e.printStackTrace();
			DebugUtility.error(ADTServerThread.class, "Unable to send message:" + msg.getCommand());
		}
	}

	/**
	 * Typically done on ATO import or client connect, this method will send the
	 * location of the .DAT file so other clients can load the ATO contents without
	 * the processing time
	 */
	public void sendATOData() {
		sendMessage(new ADTAtoDatMessage());
	}

	/**
	 * Typically done on client connect, this method will send the current list of
	 * locked cells to the newly joined client.
	 */
	public void sendLocks() {
		for (Integer key : LockedCells.getInstance().keySet()) {
			int r = LockedCells.getInstance().get(key)[0];
			int c = LockedCells.getInstance().get(key)[1];
			sendMessage(new ADTLockedCellsMessage(key, r, c));
		}
	}

	/**
	 * Done on initial client connect, this sends the rundown to the client.
	 */
	public void sendRundown() {
		RundownTableModel model = (RundownTableModel) SingletonHolder.getInstanceOf(RundownTableModel.class);
		for (int r = 0; r < model.getRowCount(); r++) {
			for (int c = 0; c < model.getColumnCount(); c++) {
				sendMessage(new ADTUpdateMessage(r, c, model.getValueAt(r, c).toString()));
			}
		}
	}
}
