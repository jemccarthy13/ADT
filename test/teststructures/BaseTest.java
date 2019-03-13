package teststructures;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;

import javax.swing.JTable;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import junit.framework.TestSuite;
import structures.ADTRobot;
import utilities.Configuration;
import utilities.DebugUtility;

/**
 * A self-registering BaseTest
 */
public abstract class BaseTest extends TestSuite {

	/**
	 * Flag to Assert.fail unimplemented tests.
	 */
	static boolean assertNotImplemented = false;

	/**
	 * Fail the test and cause the message to appear. This is more of a forceful
	 * "todo" to implement tests, yet give me the ability to run on jenkins.
	 * 
	 * @todo - convert notImplemented to a configuration variable, along with log
	 *       levels and most of the data from the Configuration class.
	 * 
	 * @param msg message to print
	 */
	public static void fail(String msg) {
		if (assertNotImplemented) {
			Assert.fail(msg);
		}
	}

	/**
	 * Simulate user has clicked on or interacted with the table
	 * 
	 * @param jt  a JTable to be edited
	 * @param row the row being edited
	 * @param col the column being edited
	 */
	public static void setEditing(JTable jt, int row, int col) {
		jt.requestFocus();
		ADTRobot.sleep(500);
		jt.setEditingRow(row);
		jt.editCellAt(row, col);
		ADTRobot.sleep(500);
	}

	/**
	 * 
	 */
	@Rule
	public TestWatcher watch = new TestWatcher() {
		@Override
		public void starting(Description desc) {
			DebugUtility.error(BaseTest.class, "Starting: " + desc.getMethodName());
			Configuration.getInstance().setTest(true);
		}
	};
	/**
	 * 
	 */
	public static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
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
		DebugUtility.setLevel(Level.FINEST);
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

		BaseTest.outContent.reset();
		BaseTest.errContent.reset();
	}

	/**
	 * Set to trace before each method because jenkins.
	 */
	@Before
	public void setTrace() {
		DebugUtility.trace(BaseTest.class, "Log4j trace");
	}
}
