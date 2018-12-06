package swing;

import javax.swing.JFrame;

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
}
