package swing;

import javax.swing.JFrame;

import main.ADTClient;
import rundown.gui.RundownFrame;
import utilities.DebugUtility;
import utilities.Fonts;

/**
 * Basis for panels
 */
public abstract class BaseFrame extends JFrame implements Singleton {

	// here to prevent use of instance
	/** */
	final public BaseFrame instance = null;

	/**
	 * Constructor
	 */
	protected BaseFrame() {
		setFont(Fonts.serif);
		this.create();
	}

	/**
	 * Serialization information
	 */
	private static final long serialVersionUID = -4628040775101357858L;

	@Override
	public abstract void create();

	/**
	 * Client session has been established
	 * 
	 * @param id - the ID of the client
	 */
	public void established(int id) {
		RundownFrame.getClient().setSessionID(Integer.valueOf(id));

		this.setTitle("Airspace Deconfliction Tool - User " + RundownFrame.getClient().getSessionID()
				+ System.getProperty("java.version"));

		DebugUtility.debug(ADTClient.class, "Joined as user: " + id);
		DebugUtility.debug(ADTClient.class, "You are now connected to the host and will receive updates.");

	}
}
