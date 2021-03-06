package adttest;

import java.awt.Window;

import rundown.gui.RundownFrame;
import server.ADTServer;
import structures.ADTRobot;
import swing.SingletonHolder;
import teststructures.BaseTest;
import teststructures.TestServer;
import utilities.DebugUtility;

/**
 * Test what happens when a server is already bound
 */
public class ServerTest {

	/** The test server instance */
	static TestServer ts = TestServer.getInstance();

	/**
	 * Before this test we need to stop the server
	 */
	public static void b4test() {
		ADTServer.getInstance().setStop();
	}

	/**
	 * Now test the ADT Server
	 */
	public void testADTServer() {
		ADTServer.getInstance().setStop();
		// while (BaseTest.outContent.toString().contains("RundownFrame")
		// && !BaseTest.outContent.toString().contains("(ADTServer ) Stopped")) {
		// ADTRobot.sleep(500);
		// }
		ADTServer.resetInstance();
		ADTRobot.sleep(5000);
		DebugUtility.debug(ServerTest.class, "Starting test server...");
		ts.start();

		ADTRobot.sleep(5000);

		/** @todo - assert no ADTServer connections were made */
		BaseTest.fail("Need to implement verification of the correct server");

		DebugUtility.debug(ServerTest.class, "Restarting test server...");
		ADTServer.resetInstance();
		ADTRobot.sleep(5000);

		ADTServer.getInstance().start();

		/** @todo - assert a successful connection to ADTServer */
		// BaseTest.fail("Need to implement verification of the correct server");

		((Window) SingletonHolder.getInstanceOf(RundownFrame.class)).setVisible(true);
		ts.stopCondition = true;

		// while (!BaseTest.outContent.toString().contains("(TestServer")) {
		// ADTRobot.sleep(500);
		// }

		ADTServer.resetInstance();
		/** @todo - assert connection was lost */
		BaseTest.fail("Need to implement verification of the correct server");

		ADTServer.getInstance().start();
		/** @todo - assert rundown frame is now the owner */
		BaseTest.fail("Need to implement verification of the correct server");
	}
}
