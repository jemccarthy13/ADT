package adttest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.RundownFrame;
import server.ADTServer;
import swing.GUI;
import utilities.BaseTest;
import utilities.TestServer;

/**
 * Test what happens when a server is already bound
 */
public class ServerTest extends BaseTest {

	/** The test server instance */
	static TestServer ts = TestServer.getInstance();

	/**
	 * Start the test server first
	 */
	@BeforeClass
	public static void startServer() {
		ts.start();
	}

	/**
	 * Stop the test server after this test is complete to prevent TestServer from
	 * blocking follow on tests
	 */
	@AfterClass
	public static void stopServer() {
		ts.stopCondition = true;
	}

	/**
	 * Now test the ADT Server
	 */
	@Test
	public void testADTServer() {
		ADTServer.resetInstance();
		ADTServer.getInstance().start();
		GUI.FRAMES.getInstanceOf(RundownFrame.class).setVisible(true);

		/** TODO */
		BaseTest.fail("Need to implement verification of the correct server");
	}
}
