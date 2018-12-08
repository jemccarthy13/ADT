package adttest;

import org.junit.AfterClass;
import org.junit.Test;

import atoLookup.ATOLookupFrame;
import main.RundownFrame;
import managers.ManagerFrame;
import rundown.gui.RundownButtonPanel;
import swing.GUI;
import utilities.BaseTest;

/**
 * Tests for airspace manager functionality
 */
public class ASManagerTest extends BaseTest {

	/**
	 * Main manager for searching through the ATO.
	 */
	@Test
	public void testATOLookup() {
		RundownButtonPanel panel = (RundownButtonPanel) GUI.PANELS.getInstanceOf(RundownButtonPanel.class);
		panel.atoLookupBtn.doClick();
		GUI.FRAMES.getInstanceOf(ATOLookupFrame.class).dispose();
	}

	/**
	 * Main manager for airspaces
	 */
	@Test
	public void testASManager() {
		RundownButtonPanel panel = (RundownButtonPanel) GUI.PANELS.getInstanceOf(RundownButtonPanel.class);
		panel.asMgrBtn.doClick();
		GUI.FRAMES.getInstanceOf(ManagerFrame.class).dispose();
	}

	/**
	 * Close the Rundown after these tests
	 */
	@AfterClass
	public static void dispose() {
		GUI.FRAMES.getInstanceOf(RundownFrame.class).dispose();
	}

}
