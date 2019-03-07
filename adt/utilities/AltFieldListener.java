package utilities;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * A listener which verifies entered altitudes are only 3 digit numbers.
 * 
 * When a field focus is lost, call the change() function to check that field
 * and ensure the value entered is a valid 3-digit flight level
 */
public class AltFieldListener implements FocusListener {

	/** The stored source of the focus gain/loss event. */
	private JTextField field;

	/**
	 * Constructor
	 * 
	 * @param field - save the field to access text value later
	 */
	public AltFieldListener(JTextField field) {
		this.field = field;
	}

	/**
	 * Called on change. Check the given altitude is valid.
	 * 
	 * @param s - The altitude string to check
	 * @return Input string truncated to 3 digits, or "" if invalid
	 */
	public static String checkAltitude(String s) {
		String retStr = "";
		// longer than 3, truncate
		String testStr = s;
		if (testStr.length() > 3) {
			testStr = s.substring(0, 3);
		}

		// now check truncated string (or original) for
		// len == 3 and numeric
		try {
			// check numeric
			Integer.parseInt(testStr);
			if (testStr.length() == 3) {
				// numeric && length = 3 is successful
				retStr = testStr;
			}
		} catch (NumberFormatException e) {
			// do nothing so retStr still == ""
		}
		return retStr;

	}

	/**
	 * Handle focus lost by checking for correct length (3) and numeric.
	 */
	public void change() {
		// if s == s2 after the validation check, value was valid
		String s = this.field.getText();
		String s2 = checkAltitude(s);
		if (!s.equals(s2)) {
			Output.forceInfoMessage("Invalid altitude format", "Altitude must be 3 digit numeric FL");
			// on error force field back to blank
			this.field.setText(s2);
		}
	}

	/**
	 * Implemented from FocusListener. Do nothing.
	 */
	@Override
	public void focusGained(FocusEvent e) {
		// do nothing
	}

	/**
	 * Implemented from FocusListener. Call the change() and check values.
	 */
	@Override
	public void focusLost(FocusEvent e) {
		// set the stored source, then call validation checker
		this.field = (JTextField) e.getSource();
		change();
	}

}
