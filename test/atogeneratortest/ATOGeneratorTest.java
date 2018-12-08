package atogeneratortest;

import java.awt.AWTException;

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
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).dispose();
	}

	/**
	 * Test using set value at to set data in a row we don't have data in
	 */
	@Test
	public void testSave() {
		((ATOButtonPanel) GUI.PANELS.getInstanceOf(ATOButtonPanel.class)).saveBtn.doClick();

		BaseTest.fail("Implement save button to ask which file");
		BaseTest.fail("Check for file exists");
	}

	/**
	 * Test using set value at to set data in a row we don't have data in
	 */
	@Test
	public void testLoad() {
		BaseTest.fail("need to implement");
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
	}

	/**
	 * Test load
	 * 
	 * @throws AWTException
	 */
	@Test
	public void test() throws AWTException {
		ADTRobot r = new ADTRobot();

		setEditing(ATOTable.getInstance(), 0, 0);
		r.type("1001");
		r.tab();
		CellEditor editor = ATOTable.getInstance().getCellEditor(0, 0);

		String y = editor.getCellEditorValue().toString();
		DebugUtility.debug(ADTTest.class, "TO STRING: " + y.toString());
		Assert.assertTrue("1001".equals(ATOTable.getInstance().getValueAt(0, 0).toString()));
	}
}
