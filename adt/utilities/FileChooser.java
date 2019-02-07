package utilities;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Utility to choose and import a file.
 * 
 * Also provides a
 */
public class FileChooser {

	/**
	 * A single chooser that all file choosing will utilize
	 */
	private static JFileChooser chooser = new JFileChooser();

	private static File defaultFile = new File(".");

	/**
	 * To help with testing, this FileChooser can either load a pre-configured test
	 * file, or ask the user for input.
	 * 
	 * @param dialogTitle - the title for the file selection dialog
	 * @param filter      - a file name extension filter to filter file types in the
	 *                    dialog
	 * @param fileLoc     - a pre-configured file to load
	 * @param imp         - an Importer, which represents an action to do with that
	 *                    file
	 */
	public static void selectAndLoadFile(String dialogTitle, FileNameExtensionFilter filter, String fileLoc,
			Importer imp) {

		// set chooser settings
		chooser.setDialogTitle(dialogTitle);
		chooser.setCurrentDirectory(defaultFile);
		chooser.setFileFilter(filter);

		// default to cancel
		int result = JFileChooser.CANCEL_OPTION;

		// if we have a test file, load it
		if (!fileLoc.equals("")) {
			File testFile = new File(fileLoc);
			if (testFile.exists()) {
				chooser.setSelectedFile(testFile);
				chooser.approveSelection();
				result = JFileChooser.APPROVE_OPTION;
			}
		} else {
			// otherwise, show the dialog to select a file
			result = chooser.showOpenDialog(null);
		}

		// if the user hasn't cancelled, or the test load file is configured, trigger
		// load success and do the import
		if (result == JFileChooser.APPROVE_OPTION) {
			imp.doImport(chooser.getSelectedFile());
		}
	}
}
