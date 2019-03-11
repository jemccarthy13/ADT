package adttest;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.ADTApp;
import rundown.gui.RundownFrame;
import rundown.gui.RundownMenuBar;
import structures.ATOAssets;
import structures.PreviousAssets;
import structures.RundownAssets;
import swing.BaseFrame;
import swing.SingletonHolder;
import teststructures.BaseTest;
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
		ADTApp.main(new String[] {});
	}

	/**
	 * Destroy frame after test
	 */
	@AfterClass
	public static void dispose() {
		((BaseFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).setVisible(false);
		((BaseFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).dispose();
	}

	/**
	 * Test the settings options
	 */
	@Test
	public void testSettings() {
		Configuration.getInstance().setATOLoadLoc("./resources/ATOs/TESTATO USMTF00.txt");
		RundownMenuBar.getInstance().doImport.doClick();

		Assert.assertTrue(((ATOAssets) SingletonHolder.getInstanceOf(ATOAssets.class)).size() == 1056);

		// random sample one to make sure it's the right guy
	}

	/**
	 * Test to make sure rundown can be zeroized
	 */
	@Test
	public void testZeroize() {
		RundownMenuBar.getInstance().zeroize.doClick();

		Assert.assertTrue(((ATOAssets) SingletonHolder.getInstanceOf(ATOAssets.class)).size() == 0);
		Assert.assertTrue(RundownAssets.getInstance().size() == 1);
		Assert.assertTrue(RundownAssets.getInstance().get(0).isBlank());
		Assert.assertTrue(((PreviousAssets) SingletonHolder.getInstanceOf(PreviousAssets.class)).size() == 0);

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
