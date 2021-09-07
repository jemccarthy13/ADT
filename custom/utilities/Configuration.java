package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import structures.KeypadFinderCGRS;
import structures.KeypadFinderGARS;
import swing.SingletonHolder;

/**
 * Holds program configuration and constants
 */
public class Configuration {

	/** The port number used to connect client and server */
	public static int portNum = 8085;
	/** The server address used for client connections */
	private static String serverAddr = "localhost";

	private static int compact = 0;
	private boolean SHOW_MESSAGES = true;
	private String ATODatFileLoc = " ";
	private String ATOProjFileLoc = "";
	private String ATOLoadLoc = "";
	private boolean production = true;
	private boolean isTest = false;

	private static Configuration instance = new Configuration();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static Configuration getInstance() {

		return instance;
	}

	private void tryLoadConfig() {
		Properties properties = new Properties();
		File file = null;
		try {
			file = new File("./resources/configuration.properties");
			FileInputStream fileInput = new FileInputStream(file);
			properties.loadFromXML(fileInput);
			fileInput.close();
		} catch (FileNotFoundException e) {
			DebugUtility.error(Configuration.class, e.getLocalizedMessage());
			try {
				URL propURL = Configuration.class.getResource("resources/configuration.properties");
				file = new File(propURL.toURI());
				FileInputStream fileInput = new FileInputStream(file);
				properties.loadFromXML(fileInput);
				fileInput.close();
			} catch (URISyntaxException e1) {
				// todo Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// todo Auto-generated catch block
				e1.printStackTrace();
			} catch (InvalidPropertiesFormatException e1) {
				// todo Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// todo Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Object key : properties.keySet()) {
			if (key.equals("server_host")) {
				Configuration.serverAddr = properties.get(key).toString();
			} else if (key.equals("port_num")) {
				Configuration.portNum = Integer.valueOf(properties.get(key).toString());
			} else if (key.equals("show_messages")) {
				if (properties.get(key).equals("false")) {
					this.SHOW_MESSAGES = false;
				}
				this.SHOW_MESSAGES = true;
			} else if (key.equals("grids")) {
				GridSettings settings = (GridSettings) SingletonHolder.getInstanceOf(GridSettings.class);
				if (properties.get(key).toString().equals("GARS")) {
					settings.setKeypadFinder(new KeypadFinderGARS());
				} else
					settings.setKeypadFinder(new KeypadFinderCGRS());
			} else if (key.equals("ato")) {
				this.ATOLoadLoc = properties.get(key).toString();
			} else if (key.equals("production")) {
				this.production = (properties.get(key).toString().equals("false")) ? false : true;
			}
		}

	}

	private Configuration() {

		tryLoadConfig();
		if (!this.production && this.ATOLoadLoc.equals("")) {
			this.ATOLoadLoc = "./resources/ATOs/Cosmo Shield - USMTF00.txt";
			this.SHOW_MESSAGES = false;
		}
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

	/**
	 * @return true if we are not production
	 */
	public boolean isDebug() {
		return !this.production;
	}

	/**
	 * @return true iff running JUnit test, to prevent Output info Boxes and auto
	 *         confirm input
	 */
	public boolean isTest() {
		return this.isTest;
	}

	/**
	 * @param isTest whether or not we are Junit testing
	 */
	public void setTest(boolean isTest) {
		this.SHOW_MESSAGES = !isTest;
		this.isTest = isTest;
	}
}
