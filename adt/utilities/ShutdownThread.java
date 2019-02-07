package utilities;

import messages.ADTEndSessionMessage;
import messages.ADTUnlockUserMessage;
import rundown.gui.RundownFrame;
import server.ADTServer;

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
		// unlock all editing holds
		RundownFrame.getClient().sendMessage(new ADTUnlockUserMessage(RundownFrame.getClient().getSessionID()));

		// unregister the client
		RundownFrame.getClient().sendMessage(new ADTEndSessionMessage(RundownFrame.getClient().getSessionID()));
		RundownFrame.getClient().setStop();

		// interrupt the server thread to neatly end it if we were the host
		ADTServer.getInstance().interrupt();
		RundownFrame.getClient().interrupt();
	}
}
