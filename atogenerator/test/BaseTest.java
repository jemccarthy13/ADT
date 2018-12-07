package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import junit.framework.TestSuite;
import utilities.DebugUtility;

/**
 * A self-registering BaseTest
 */
public abstract class BaseTest extends TestSuite {

	/**
	 * 
	 */
	@Rule
	public TestWatcher watch = new TestWatcher() {
		@Override
		public void starting(Description desc) {
			DebugUtility.error(BaseTest.class, "Starting: " + desc.getMethodName());
		}
	};
	/**
	 * 
	 */
	protected static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/**
	 * 
	 */
	protected static ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	/**
	 * 
	 */
	protected static PrintStream outStream = new PrintStream(outContent);
	/**
	 * 
	 */
	protected static PrintStream errStream = new PrintStream(errContent);
	/**
	 * 
	 */
	protected static PrintStream originalOut = System.out;
	/**
	 * 
	 */
	protected static PrintStream originalErr = System.err;

	/**
	 * Before each test - redirect System.out / err
	 */
	@BeforeClass
	public static void setUpStreams() {
		System.setOut(outStream);
		System.setErr(errStream);
	}

	/**
	 * After each test - restore System.out/err
	 */
	@AfterClass
	public static void restoreStreams() {
		originalOut.println(outContent);
		originalErr.println(errContent);
	}

	/**
	 * Set to trace before each method because jenkins.
	 */
	@Before
	public void setTrace() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig("ADTLogger");
		loggerConfig.setLevel(Level.TRACE);
		ctx.updateLoggers();

		DebugUtility.log4j.trace("Log4j Set to trace");
	}
}
