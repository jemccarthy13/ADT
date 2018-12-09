package adttest;

import org.junit.Test;

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
	}

	/**
	 * Main manager for airspaces
	 */
	@Test
	public void testASManager() {
		RundownButtonPanel panel = (RundownButtonPanel) GUI.PANELS.getInstanceOf(RundownButtonPanel.class);
		panel.asMgrBtn.doClick();
	}
}
