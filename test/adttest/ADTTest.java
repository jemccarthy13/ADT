package adttest;

import java.awt.AWTException;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.ADTApp;
import main.ADTClient;
import messages.ADTLockedMessage;
import rundown.gui.RundownButtonPanel;
import rundown.gui.RundownFrame;
import rundown.model.RundownTable;
import rundown.model.RundownTableModel;
import swing.GUI;
import utilities.ADTRobot;
import utilities.BaseTest;
import utilities.DebugUtility;

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
	 * Resize the columns
	 */
	@Test
	public void columnResize() {
		((RundownButtonPanel) GUI.PANELS.getInstanceOf(RundownButtonPanel.class)).compactCheck.doClick();
		ADTRobot.sleep(500);
		Assert.assertTrue(9 == GUI.MODELS.getInstanceOf(RundownTableModel.class).getColumnCount());
		((RundownButtonPanel) GUI.PANELS.getInstanceOf(RundownButtonPanel.class)).compactCheck.doClick();
		ADTRobot.sleep(1000);
		DebugUtility.debug(ADTTest.class,
				((RundownTableModel) GUI.MODELS.getInstanceOf(RundownTableModel.class)).getColumnCount() + "");
		Assert.assertTrue(RundownTable.getInstance().getColumnModel().getColumn(6).getWidth() == 5);
	}

	/**
	 * Test coverage of several unused paths of code.
	 */
	@Test
	public void rundownModelCoverage() {
		GUI.MODELS.getInstanceOf(RundownTableModel.class).setValueAt("Invalid", 5, 5);
		Assert.assertTrue(6 == GUI.MODELS.getInstanceOf(RundownTableModel.class).getRowCount());
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
	 * Test load
	 */
	@Test
	public void multiUserADT() {
		ADTClient client = new ADTClient();
		client.start();
		ADTRobot.sleep(3000);
		client.sendMessage(new ADTLockedMessage(0, 4, true));

		ADTRobot.sleep(3000);
		Assert.assertFalse(RundownTable.getInstance().isCellEditable(0, 4));
		client.interrupt();
		ADTRobot.sleep(4000);
		Assert.assertTrue(RundownTable.getInstance().isCellEditable(0, 4));
		client.interrupt();
	}

	/**
	 * Test altitude cell listener
	 */
	@Test
	public void testAltCellValidation() {
		BaseTest.fail("Try to enter an invalid altitude");
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
