package adttest;

import org.junit.Test;

import rundown.gui.RundownButtonPanel;
import swing.SingletonHolder;
import utilities.ADTRobot;
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
		RundownButtonPanel panel = (RundownButtonPanel) SingletonHolder.getInstanceOf(RundownButtonPanel.class);
		panel.asMgrBtn.doClick();
		ADTRobot.sleep(4000);
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
