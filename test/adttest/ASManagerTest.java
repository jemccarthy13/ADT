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
	 * Main manager for named airspace
	 */
	@Test
	public void testOpenASManager() {
		RundownButtonPanel panel = (RundownButtonPanel) GUI.PANELS.getInstanceOf(RundownButtonPanel.class);
		panel.asMgrBtn.doClick();
	}

	/**
	 * 
	 */
	@Test
	public void testAddAirspace() {
		BaseTest.fail("Unimplemented");
	}

	/**
	 * 
	 */
	@Test
	public void testActivateAirspace() {
		BaseTest.fail("Unimplemented");
	}
}
