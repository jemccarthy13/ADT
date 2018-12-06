package utilities;

import main.RundownFrame;
import server.ADTServer;

/**
 * A thread designed to be added to a shutdown hook of the rundown frame.
 * 
 * @author John McCarthy
 *
 */
public class ShutdownThread extends Thread {

	/**
	 * Run the thread's functions.
	 */
	@Override
	public void run() {
		// unlock all editing holds
		RundownFrame.getClient().sendMessage("unlock all");

		// unregister the client
		RundownFrame.getClient().sendMessage("end");

		// interrupt the server thread to neatly end it if we were the host
		ADTServer.getInstance().interrupt();
	}
}
