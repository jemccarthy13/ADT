package adttest;

import org.junit.Test;

import asmanager.gui.ManagerFrame;
import rundown.gui.RundownButtonPanel;
import structures.ADTRobot;
import swing.SingletonHolder;
import teststructures.BaseTest;

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
		((ManagerFrame) SingletonHolder.getInstanceOf(ManagerFrame.class)).dispose();
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
