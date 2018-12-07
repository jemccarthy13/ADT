package utilities;

import javax.swing.UIManager;

/**
 * Holds program configuration and constants
 */
public class Configuration {
	/** The port number used to connect client and server */
	public static final int portNum = 8085;

	private static boolean compact = false;

	private final boolean SHOW_MESSAGES = false;

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
			DebugUtility.error("Unable to set system look and feel. ", e1);
		}
	}

	/**
	 * @return true iff compact checkbox is true (checked)
	 */
	public static boolean isCompactMode() {
		return compact;
	}

	/**
	 * @param selected set the compact mode based on selected option
	 */
	public static void setCompact(boolean selected) {
		compact = selected;
	}
}
