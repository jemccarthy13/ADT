package utilities;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

/**
 * A simple server designed to operate on the same port and block the ADTServer
 */
public class TestServer extends Thread {

	/** The socket of the server */
	protected ServerSocket ss;
	/** set to true when the server should stop */
	public boolean stopCondition;
	private static TestServer instance = new TestServer();

	private TestServer() {
	}

	/**
	 * Start a test server
	 * 
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		instance.start();
	}

	/**
	 * Singleton implementation.
	 * 
	 * @return single instance
	 */
	public static TestServer getInstance() {
		return instance;
	}

	/**
	 * Start the ADT Server.
	 * 
	 * @note should not be called directly
	 */
	@Override
	public void run() {
		try {
			this.ss = new ServerSocket(Configuration.portNum);
			DebugUtility.debug(TestServer.class, "Started the test server.");
		} catch (BindException e3) {
			DebugUtility.error(TestServer.class, "Port already bound: " + Configuration.portNum);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		while (!this.stopCondition) {
			ADTRobot.sleep(250);
		}

		DebugUtility.debug(TestServer.class, "Stopped");
		try {
			this.ss.close();
		} catch (IOException e) {
			// yup
		}
	}
}
