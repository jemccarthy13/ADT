package panels;

import javax.swing.JPanel;

import utilities.Fonts;

/**
 * Basis for panels
 */
public abstract class BasePanel extends JPanel implements Singleton {

	// here to prevent use of instance
	/**
	 * 
	 */
	final public BasePanel instance = null;

	/**
	 * Constructor
	 */
	protected BasePanel() {
		setFont(Fonts.serif);
		this.create();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4628040775101357858L;

	@Override
	public abstract void create();
}
