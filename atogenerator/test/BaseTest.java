package test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;

import junit.framework.TestSuite;

/**
 * A self-registering BaseTest
 */
public abstract class BaseTest extends TestSuite {
	/**
	 * 
	 */
	protected final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/**
	 * 
	 */
	protected final static ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	/**
	 * 
	 */
	protected PrintStream originalOut = System.out;
	/**
	 * 
	 */
	protected PrintStream originalErr = System.err;

	/**
	 * 
	 */
	public BaseTest() {
	}

	/**
	 * Before each test - redirect System.out / err
	 */
	@SuppressWarnings("static-method")
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}

	/**
	 * After each test - restore System.out/err
	 */
	@After
	public void restoreStreams() {
		this.originalOut.println(outContent);
		System.setOut(this.originalOut);
		System.setErr(this.originalErr);
	}
}
