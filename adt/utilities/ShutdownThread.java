package utilities;

import main.ADTClient;
import messages.ADTEndSessionMessage;
import messages.ADTUnlockUserMessage;
import rundown.gui.RundownFrame;
import server.ADTServer;
import swing.SingletonHolder;

/**
 * A thread designed to be added to a shutdown hook of the rundown frame.
 */
public class ShutdownThread extends Thread {

	/**
	 * The ShutdownThread is a added as a hook when the rundown frame is closed.
	 * 
	 * The run() method is therefore a cleanup method to handle closing the loop
	 * with the server when a client leaves.
	 * 
	 * To help prevent errors, the client sends an "unlock all" message, with an end
	 * session message. The client's stop condition is set for eventual garbage
	 * collection. Then, interrupt() some threads to ensure cleanup.
	 * 
	 */
	@Override
	public void run() {
		ADTClient client = ((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).getClient();

		// unlock all editing holds
		client.sendMessage(new ADTUnlockUserMessage(client.getSessionID()));

		// unregister the client
		client.sendMessage(new ADTEndSessionMessage(client.getSessionID()));
		client.setStop();

		// interrupt the server thread to neatly end it if we were the host
		ADTServer.getInstance().interrupt();
		client.interrupt();
	}
}
