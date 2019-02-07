package adttest;

import org.junit.Test;

import rundown.gui.RundownButtonPanel;
import swing.SingletonHolder;
import utilities.BaseTest;

/**
 * Test the ATO Lookup functionality
 */
public class ATOLookupTest extends BaseTest {

	/**
	 * Main manager for searching through the ATO.
	 */
	@Test
	public void testATOLookup() {
		RundownButtonPanel panel = (RundownButtonPanel) SingletonHolder.getInstanceOf(RundownButtonPanel.class);
		panel.atoLookupBtn.doClick();
	}

	/**
	 * Test searching for a player in the ATO Lookup
	 */
	@Test
	public void testATOSearch() {
		BaseTest.fail("Unimplemented");
	}

}
