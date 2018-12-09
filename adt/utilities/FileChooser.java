package utilities;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import structures.Importer;

/**
 * Utility to choose and import a file.
 */
public class FileChooser {

	/**
	 * @param fc
	 * @param dialogTitle
	 * @param filter
	 * @param fileLoc
	 * @param imp
	 */
	public static void selectAndLoadFile(JFileChooser fc, String dialogTitle, FileNameExtensionFilter filter,
			String fileLoc, Importer imp) {
		Configuration.getInstance().setLoadSuccess(false);
		File f = new File(".");

		fc.setDialogTitle(dialogTitle);
		fc.setCurrentDirectory(f);
		fc.setFileFilter(filter);

		int result = JFileChooser.CANCEL_OPTION;
		String configFilePath = fileLoc;

		if (!configFilePath.equals("")) {
			File testFile = new File(configFilePath);
			if (testFile.exists()) {
				fc.setSelectedFile(testFile);
				fc.approveSelection();
				result = JFileChooser.APPROVE_OPTION;
			}
		} else {
			result = fc.showOpenDialog(null);
		}

		if (result == JFileChooser.APPROVE_OPTION) {
			Configuration.getInstance().setLoadSuccess(true);
			f = fc.getSelectedFile();

			if (f != null) {
				imp.doImport(f);
			}
		}
	}
}
