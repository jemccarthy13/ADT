package utilities;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * A altitude "cell listener" which verifies altitudes are only 3 digit numbers.
 * 
 * @author John McCarthy
 */
public class AltCellListener implements FocusListener {

	private JTextField field;

	/**
	 * Constructor
	 * 
	 * @param field
	 *            - save the field so I can access text value
	 */
	public AltCellListener(JTextField field) {
		this.field = field;
	}

	/**
	 * TODO - move this as a helper utility elsewhere
	 * 
	 * @param s
	 *            - the altitude string to check
	 * @param err
	 *            - if the check should cause an error
	 * @return - the string truncated to 3 digits, or "" if invalid
	 */
	public static String checkAltitude(String s) {
		String retStr = "";
		if (s.length() > 3) {
			retStr = s.substring(0, 3);
		} else {
			try {
				Integer.parseInt(s);
				retStr = s;
			} catch (NumberFormatException e) {
				// do nothing
			}
		}
		return retStr;
	}

	/**
	 * Handle all changes by checking for length and numeric
	 * 
	 * @param e
	 */
	public void change() {
		String s = this.field.getText();
		String s2 = checkAltitude(s);
		if (!s.equals(s2)) {
			Output.forceInfoMessage("Invalid altitude format", "Altitude must be 3 digit numeric FL");
			this.field.setText(s2);
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		// do nothing
	}

	@Override
	public void focusLost(FocusEvent e) {
		change();
	}

}
