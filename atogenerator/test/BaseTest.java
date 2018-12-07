package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.AfterClass;
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
}
