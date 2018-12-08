package adttest;

import java.awt.AWTException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.ADTApp;
import main.RundownFrame;
import rundown.gui.RundownButtonPanel;
import rundown.model.RundownTable;
import swing.GUI;
import utilities.ADTRobot;
import utilities.BaseTest;

/**
 * For the ADT
 */
public class ADTTest extends BaseTest {

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
	 * Test load
	 */
	@Test
	public void test() {
		RundownButtonPanel panel = ((RundownButtonPanel) GUI.PANELS.getInstanceOf(RundownButtonPanel.class));
		panel.atoLookupBtn.doClick();
	}

	/**
	 * Test edit
	 * 
	 * @throws AWTException
	 */
	@Test
	public void edit() throws AWTException {
		setEditing(RundownTable.getInstance(), 0, 0);
		ADTRobot.type("Y");
		ADTRobot.tab();
		Assert.assertTrue("Y".equals(RundownTable.getInstance().getValueAt(0, 0).toString()));
	}
}
