package adttest;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.junit.Test;

import rundown.gui.RundownMenuBar;
import structures.ATOImporter;
import utilities.BaseTest;
import utilities.Configuration;
import utilities.FileChooser;

/**
 * Test the ADT's menu bar options
 */
public class ADTMenuBarTest {

	/**
	 * Test the settings options
	 */
	@Test
	public void testSettings() {
		Configuration.getInstance().setATOLoadLoc("TESTATO.txt");
		RundownMenuBar.getInstance().doImport.doClick();
		FileChooser.selectAndLoadFile(new JFileChooser(), "Choose an ATO file",
				new FileNameExtensionFilter("ATO", "txt"), Configuration.getInstance().getATODatFileLoc(),
				new ATOImporter());

		/** TODO - implement ATO import the same as ATO generator test */

		// If running a test, import a test ATO
		// Select it in the ATO import file chooser
		// test to verify checksum of all assets added to ATO search
		// random sample one to make sure it's the right guy
	}

	/**
	 * Test set grid settings
	 */
	@Test
	public void testSetGrid() {
		RundownMenuBar.getInstance().setGrid.doClick();

		BaseTest.fail("Set grid not implemented");
	}

	/**
	 * Test set grid settings
	 */
	@Test
	public void testTypeManager() {
		RundownMenuBar.getInstance().typeManager.doClick();

		BaseTest.fail("Type manager not implemented");
	}

	/**
	 * Test set grid settings
	 */
	@Test
	public void testRefresh() {
		RundownMenuBar.getInstance().refresh.doClick();

		BaseTest.fail("Not sure how to test refrsh on menu bar");
	}
}
