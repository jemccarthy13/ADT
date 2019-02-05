package main;

import rundown.gui.RundownFrame;
import swing.GUI;
import utilities.DebugUtility;
import utilities.ShutdownThread;

/**
 * Main starting point of the ADT.
 * 
 * This class holds the main() method logic and code to start the main
 * RundownFrame class, along with a periodic refresh.
 */
public class ADTApp {

	/**
	 * A refresh thread to periodically refresh the RundownFrame
	 */
	private static Thread refreshThread = new Thread() {
		@Override
		public void run() {
			while (true) {
				// do the refresh
				GUI.FRAMES.getInstanceOf(RundownFrame.class).repaint();

				// delay, then go again
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					DebugUtility.error(RundownFrame.class, "Interrupted", e);
				}
			}
		}
	};

	/**
	 * Launch the application by performing the following actions:
	 * 
	 * 1) Flyweight load the RundownFrame and display it
	 * 
	 * 2) Add a shutdown hook to clean up the client/connection and associated
	 * resources on dispose of the ADT frame
	 * 
	 * 3)Start the refresh thread
	 * 
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		GUI.FRAMES.getInstanceOf(RundownFrame.class).setVisible(true);
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		refreshThread.start();
	}
}
