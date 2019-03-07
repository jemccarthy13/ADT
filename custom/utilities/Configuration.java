package utilities;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Holds program configuration and constants
 */
public class Configuration {

	/** The port number used to connect client and server */
	public static final int portNum = 8085;
	/** The server address used for client connections */
	private static String serverAddr = "localhost";

	private static int compact = 0;

	private final boolean SHOW_MESSAGES = false;

	private String ATODatFileLoc = " ";
	private String ATOProjFileLoc = "";
	private String ATOLoadLoc = "./resources/ATOs/Cosmo Shield - USMTF00.txt";
	// private String ATOLoadLoc = "";

	private static Configuration instance = new Configuration();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static Configuration getInstance() {
		return instance;
	}

	private Configuration() {
	}

	/**
	 * @return Current ATO data file location
	 */
	public String getATOProjFileLoc() {
		return this.ATOProjFileLoc;
	}

	/**
	 * @param loc The new location of the ATO data file.
	 */
	public void setATOProjFileLoc(String loc) {
		this.ATOProjFileLoc = loc;
	}

	/**
	 * @return Current ATO data file location
	 */
	public String getATODatFileLoc() {
		return this.ATODatFileLoc;
	}

	/**
	 * @param loc The new location of the ATO data file.
	 */
	public void setATODatFileLoc(String loc) {
		this.ATODatFileLoc = loc;
	}

	/**
	 * @return true iff show messages is turned on
	 */
	public boolean isShowMessages() {
		return this.SHOW_MESSAGES;
	}

	/**
	 * Set the look and feel of the application.
	 * 
	 * @param class1
	 */
	public static void setLookAndFeel(Class<?> class1) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			DebugUtility.trace(class1, "Look and feel set");
		} catch (Exception e1) {
			DebugUtility.error(Configuration.class, "Unable to set system look and feel. ", e1);
		}
	}

	/**
	 * @return true iff compact checkbox is true (checked)
	 */
	public static int getCompact() {
		return compact;
	}

	/**
	 * @param i set the compact mode based on selected option
	 */
	public static void setCompact(int i) {
		compact = i;
	}

	/**
	 * Switch the compact mode on or off
	 */
	public static void switchCompact() {
		DebugUtility.trace(Configuration.class, "Compact switching...");
		DebugUtility.trace(Configuration.class, "Before: " + compact);
		if (compact == 3) {
			compact = 0;
		} else if (compact == 0) {
			compact = 3;
		}
		DebugUtility.trace(Configuration.class, "After: " + compact);
	}

	/**
	 * Set the ATO load from location
	 * 
	 * @param location
	 */
	public void setATOLoadLoc(String location) {
		this.ATOLoadLoc = location;
	}

	/**
	 * Retrieve the ATO load from location
	 * 
	 * @return location of desired ATO load
	 */
	public String getATOLoadLoc() {
		return this.ATOLoadLoc;
	}

	/**
	 * If a server IP address hasn't been configured, configure one
	 * 
	 * @return - the desired server IP address
	 */
	public static String getServerAddress() {
		if (serverAddr.equals("")) {
			serverAddr = JOptionPane.showInputDialog(null, "Server address:");
		}

		return serverAddr;
	}

	/**
	 * Reset the configured server IP address
	 * 
	 * @param userIn - the new server address
	 */
	public static void setServerAddress(String userIn) {
		serverAddr = userIn;
	}
}
