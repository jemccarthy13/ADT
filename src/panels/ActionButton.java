package panels;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import utilities.Fonts;

/**
 * A JButton with a constructor that registers to an ActionListener
 */
public class ActionButton extends JButton {

	/** Serialization variable */
	private static final long serialVersionUID = -7014978593989898339L;

	/**
	 * Create a new button, set it's title, and add an action listener
	 * 
	 * @param title    the title of the button
	 * @param listener listen to this button with this ActionListener
	 */
	public ActionButton(String title, ActionListener listener) {
		super();
		this.setText(title);
		this.addActionListener(listener);
		this.setFont(Fonts.serif);
	}
}
