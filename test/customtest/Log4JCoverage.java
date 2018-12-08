package customtest;

import java.awt.AWTException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
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
		DebugUtility.fatal("Hello");
		DebugUtility.error(Log4JCoverage.class, "Oh no!", new Exception("Exception Hello World"));
		Assert.assertTrue(BaseTest.outContent.toString().contains("Exception Hello World"));

		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig("ADTLogger");
		Level prevLevel = loggerConfig.getLevel();

		loggerConfig.setLevel(Level.OFF);
		ctx.updateLoggers();

		DebugUtility.debug(Log4JCoverage.class, "Should not appear");
		Assert.assertFalse(BaseTest.outContent.toString().contains("Should not appear"));

		loggerConfig.setLevel(prevLevel);

		Assert.assertTrue("DebugUtility".equals(DebugUtility.getInstance().getClass().getSimpleName()));
	}
}
