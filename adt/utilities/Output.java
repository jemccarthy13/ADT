package utilities;

import javax.swing.JOptionPane;

/**
 * Handle generating user output.
 */
public class Output {

	/**
	 * Fore an information dialog to display
	 * 
	 * @param title - the title of the window
	 * @param msg   - the message to display
	 */
	public static void forceInfoMessage(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Show a confirmation dialog.
	 * 
	 * @param title - the title of the window
	 * @param msg   - the message to display
	 * @return - true iff "OK" was selected
	 */
	public static boolean showConfirmMessage(String title, String msg) {
		return JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.OK_CANCEL_OPTION) == 0;
	}

	/**
	 * If messages are turned on, show an info message dialog
	 * 
	 * @param title - the title of the dialog window
	 * @param msg   - the message to be displayed
	 */
	public static void showInfoMessage(String title, String msg) {
		if (Configuration.getInstance().isShowMessages()) {
			forceInfoMessage(title, msg);
		} else {
			DebugUtility.trace(Output.class, msg);
		}
	}
}
