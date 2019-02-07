package utilities;

import java.io.File;

/**
 * Abstract class with one method (doImport)
 */
public interface Importer {

	/**
	 * Import the file
	 * 
	 * @param f
	 */
	public void doImport(File f);
}
