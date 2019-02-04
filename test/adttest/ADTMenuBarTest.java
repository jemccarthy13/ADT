package adttest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.ADTApp;
import main.RundownFrame;
import rundown.gui.RundownMenuBar;
import structures.ATOAssets;
import swing.GUI;
import utilities.BaseTest;
import utilities.Configuration;

/**
 * Test the ADT's menu bar options
 */
public class ADTMenuBarTest extends BaseTest {
	/**
	 * Load the ADTApp before any tests are run
	 */
	@BeforeClass
	public static void load() {
		ADTApp.main(null);
	}

	/**
	 * Destroy frame after test
	 */
	@AfterClass
	public static void dispose() {
		GUI.FRAMES.getInstanceOf(RundownFrame.class).setVisible(false);
		GUI.FRAMES.getInstanceOf(RundownFrame.class).dispose();
	}

	/**
	 * Test the settings options
	 */
	@Test
	public void testSettings() {
		Configuration.getInstance().setATOLoadLoc("./TESTATO.txt");
		RundownMenuBar.getInstance().doImport.doClick();

		Assert.assertTrue(ATOAssets.staticInstance().size() == 1000);

		// random sample one to make sure it's the right guy
	}

	/**
	 * Test to make sure rundown can be zeroized
	 */
	@Test
	public void testZeroize() {
		RundownMenuBar.getInstance().zeroize.doClick();

		BaseTest.fail("Zeroize not implemented");
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
