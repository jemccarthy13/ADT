package test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import javax.swing.CellEditor;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import main.ADTApp;
import main.RundownFrame;
import rundown.gui.RundownButtonPanel;
import rundown.model.RundownTable;
import swing.GUI;
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
		RundownTable.getInstance().requestFocus();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RundownTable.getInstance().setEditingRow(0);
		RundownTable.getInstance().editCellAt(0, 0);
		Robot r;
		r = new Robot();
		r.keyPress(KeyEvent.VK_Y);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.keyRelease(KeyEvent.VK_Y);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.keyPress(KeyEvent.VK_TAB);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		r.keyRelease(KeyEvent.VK_TAB);

		CellEditor editor = RundownTable.getInstance().getCellEditor(0, 0);

		String y = editor.getCellEditorValue().toString();
		DebugUtility.debug(ADTTest.class, "TO STRING: " + y.toString());
		Assert.assertTrue("Y".equals(RundownTable.getInstance().getValueAt(0, 0).toString()));
	}
}
