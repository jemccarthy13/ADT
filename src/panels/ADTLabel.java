package panels;

import javax.swing.JLabel;

import utilities.Fonts;

/**
 * Extends JLabel to set the font.
 */
public class ADTLabel extends JLabel {
	/** serialization variable */
	private static final long serialVersionUID = 6666777493038343827L;

	/**
	 * Constructor
	 */
	public ADTLabel() {
		super();
		this.setFont(Fonts.serif);
	}

	/**
	 * Constructor with text
	 * 
	 * @param string
	 */
	public ADTLabel(String string) {
		super(string);
	}
}
