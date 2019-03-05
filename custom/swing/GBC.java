package swing;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Custom GridBagConstraints helper
 */
public class GBC extends GridBagConstraints {

	/**
	 * Serialization information
	 */
	private static final long serialVersionUID = 7560384801380535809L;

	/**
	 * Create a GridBagConstraints object
	 */
	public void create() {
		this.fill = GridBagConstraints.HORIZONTAL;
		this.insets = new Insets(0, 0, 5, 5);
	}

	/**
	 * initialization constructor
	 * 
	 * @param x x position
	 * @param y y position
	 */
	public GBC(int x, int y) {
		this.setup(1, x, y);
	}

	/**
	 * Initialization constructor
	 * 
	 * @param width width of this component
	 * @param x     x position
	 * @param y     y position
	 */
	public GBC(int width, int x, int y) {
		this.setup(width, x, y);
	}

	/**
	 * Setup this gridbag constraint
	 * 
	 * @param width width of constraint
	 * @param x     x position
	 * @param y     y position
	 */
	public void setup(int width, int x, int y) {
		this.gridwidth = width;
		this.gridy = y;
		this.gridx = x;
		create();
	}
}
