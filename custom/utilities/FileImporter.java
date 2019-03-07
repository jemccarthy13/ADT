package utilities;

import java.io.File;

/**
 * Abstract class with one method (doImport) which specifies how to import a
 * given file
 */
public interface FileImporter {

	/**
	 * Import the file
	 * 
	 * @param f
	 */
	public void doImport(File f);
}
