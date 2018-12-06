package utilities;

import javax.swing.UIManager;

/**
 * Holds program configuration and constants
 * 
 * @author John McCarthy
 *
 */
public class Configuration {

	/** The location of the test ATO */
	public static final String testATOFilePath = "C:\\Users\\caitr\\git\\ADT\\ATO XX CHG 0 USMTF00.txt";

	/** The port number used to connect client and server */
	public static final int portNum = 8085;

	private final boolean SHOW_MESSAGES = false;
	private boolean testATO = false;

	private String ATODatFileLoc = " ";

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
	 * @return true iff we should be using the test ATO file
	 */
	public boolean isTestATO() {
		return this.testATO;
	}

	/**
	 * @return true iff show messages is turned on
	 */
	public boolean isShowMessages() {
		return this.SHOW_MESSAGES;
	}

	/**
	 * Set the look and feel of the application.
	 */
	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			DebugUtility.printError("Unable to set system look and feel. " + e1.getMessage());
		}
	}
}
