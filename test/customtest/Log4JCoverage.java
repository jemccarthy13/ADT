package customtest;

import java.awt.AWTException;
import java.util.logging.Level;

import org.junit.Assert;
import org.junit.Test;

import utilities.ADTRobot;
import utilities.BaseTest;
import utilities.DebugUtility;

/**
 * Covering the gap of DebugUtilitis/log4j
 */
public class Log4JCoverage extends BaseTest {

	/**
	 * Ensure 100% coverage of ADT Robot
	 * 
	 * @throws AWTException
	 */
	@Test
	public void interruption() throws AWTException {
		ADTRobot.sleep(250);
	}

	/**
	 * Test the lesser-used functionality of DebugUtils/Log4J combo
	 */
	@Test
	public void Testlog() {
		DebugUtility.error(Log4JCoverage.class, "Oh no!", new Exception("Exception Hello World"));
		Assert.assertTrue(BaseTest.errContent.toString().contains("Exception Hello World"));

		DebugUtility.setLevel(Level.OFF);

		DebugUtility.debug(Log4JCoverage.class, "Should not appear");
		Assert.assertFalse(BaseTest.outContent.toString().contains("Should not appear"));

		Assert.assertTrue("DebugUtility".equals(DebugUtility.getInstance().getClass().getSimpleName()));
	}
}
