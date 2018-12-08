package atogeneratortest;

import java.awt.event.KeyEvent;

import javax.swing.CellEditor;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import adttest.ADTTest;
import gui.ATOButtonPanel;
import gui.ATOGeneratorFrame;
import main.ATOGenerator;
import swing.GUI;
import table.ATOTable;
import table.ATOTableModel;
import utilities.ADTRobot;
import utilities.BaseTest;
import utilities.Configuration;
import utilities.DebugUtility;

/**
 * JUnit test cases
 */
public class ATOGeneratorTest extends BaseTest {
	/**
	 * Load the ADTApp before any tests are run
	 */
	@BeforeClass
	public static void load() {
		ATOGenerator.main(null);
	}

	/**
	 * Destroy frame after test
	 */
	@AfterClass
	public static void dispose() {
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).setVisible(false);
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).dispose();
	}

	/**
	 * Test using set value at to set data in a row we don't have data in
	 */
	@Test
	public void testCancelEdit() {

		setEditing(ATOTable.getInstance(), 0, 1);
		Assert.assertTrue(ATOTable.getInstance().getValueAt(0, 1).equals(""));

		ADTRobot.tab();
		Assert.assertTrue(ATOTable.getInstance().getValueAt(0, 1).equals("-"));

		setEditing(ATOTable.getInstance(), 0, 0);

		ADTRobot.simulatePressKey(KeyEvent.VK_SPACE);
		for (int x = 0; x < 10; x++) {
			ADTRobot.simulatePressKey(KeyEvent.VK_RIGHT);
		}
		for (int x = 0; x < 10; x++) {
			ADTRobot.backspace();
		}
		ADTRobot.tab();

		Assert.assertTrue(ATOTable.getInstance().getValueAt(0, 1).equals("-"));
	}

	/**
	 * Test using set value at to set data in a row we don't have data in
	 */
	@Test
	public void testSave() {
		GUI.MODELS.getInstanceOf(ATOTableModel.class).clear();
		GUI.MODELS.getInstanceOf(ATOTableModel.class).addNew();
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).repaint();
		setEditing(ATOTable.getInstance(), 0, 0);
		ADTRobot.type("1001");
		ADTRobot.tab();

		((ATOButtonPanel) GUI.PANELS.getInstanceOf(ATOButtonPanel.class)).saveBtn.doClick();

		BaseTest.fail("Implement save button to ask which file");
		BaseTest.fail("Check for file exists");
	}

	/**
	 * Test using set value at to set data in a row we don't have data in
	 */
	@Test
	public void testGenerate() {
		GUI.MODELS.getInstanceOf(ATOTableModel.class).clear();
		GUI.MODELS.getInstanceOf(ATOTableModel.class).addNew();
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).repaint();
		setEditing(ATOTable.getInstance(), 0, 0);
		ADTRobot.type("1001");
		ADTRobot.tab();
		((ATOButtonPanel) GUI.PANELS.getInstanceOf(ATOButtonPanel.class)).genBtn.doClick();
		Assert.assertTrue(outContent.toString().contains("TASKUNIT/552 AEW"));
	}

	/**
	 * Test using set value at to set data in a row we don't have data in
	 */
	@Test
	public void testLoad() {
		Configuration.getInstance().setATOProjFileLoc("TESTPROJ.proj");
		((ATOButtonPanel) GUI.PANELS.getInstanceOf(ATOButtonPanel.class)).loadBtn.doClick();

		Assert.assertTrue(outContent.toString().contains("Project loaded:"));
		Assert.assertTrue(outContent.toString().contains(Configuration.getInstance().getATOProjFileLoc()));
		Assert.assertTrue(Configuration.getInstance().isLoadSuccess());

		Configuration.getInstance().setATOProjFileLoc("MyProj.proj");
		((ATOButtonPanel) GUI.PANELS.getInstanceOf(ATOButtonPanel.class)).loadBtn.doClick();

	}

	/**
	 * Test using set value at to set data in a row we don't have data in
	 */
	@Test
	public void testDynamicAdd() {
		GUI.MODELS.getInstanceOf(ATOTableModel.class).clear();
		GUI.MODELS.getInstanceOf(ATOTableModel.class).addNew();
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).repaint();
		Assert.assertTrue(GUI.MODELS.getInstanceOf(ATOTableModel.class).getRowCount() == 1);
		setEditing(ATOTable.getInstance(), 0, 0);
		Assert.assertTrue(GUI.MODELS.getInstanceOf(ATOTableModel.class).getRowCount() == 2);
		setEditing(ATOTable.getInstance(), 1, 5);
		Assert.assertTrue(GUI.MODELS.getInstanceOf(ATOTableModel.class).getRowCount() == 3);
	}

	/**
	 * Test using set value at to set data in a row we don't have data in
	 */
	@Test
	public void testADTTableModel() {
		GUI.MODELS.getInstanceOf(ATOTableModel.class).clear();
		GUI.MODELS.getInstanceOf(ATOTableModel.class).setValueAt("Invalid", 2, 5);
		Assert.assertTrue("INVALID".equals(ATOTable.getInstance().getValueAt(2, 5).toString()));
		Assert.assertNull(GUI.MODELS.getInstanceOf(ATOTableModel.class).getValueAt(100, 100));
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).repaint();
	}

	/**
	 * Test load
	 */
	@Test
	public void test() {
		setEditing(ATOTable.getInstance(), 0, 0);
		ADTRobot.type("1001");
		ADTRobot.tab();
		CellEditor editor = ATOTable.getInstance().getCellEditor(0, 0);

		String y = editor.getCellEditorValue().toString();
		DebugUtility.debug(ADTTest.class, "TO STRING: " + y.toString());
		Assert.assertTrue("1001".equals(ATOTable.getInstance().getValueAt(0, 0).toString()));
	}
}
