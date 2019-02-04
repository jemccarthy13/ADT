package utilities;

import javax.swing.JOptionPane;

/**
 * Handle generating user output.
 */
public class Output {

	/**
	 * Show an information dialog
	 * 
	 * @param title - the title of the window
	 * @param msg   - the message to show
	 */
	public static void showInfoMessage(String title, String msg) {
		if (Configuration.getInstance().isShowMessages()) {
			JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
		} else {
			DebugUtility.debug(Output.class, msg);
		}
	}

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
}
