package main;

import java.awt.Window;

import rundown.gui.RundownFrame;
import structures.ATOAssets;
import structures.Asset;
import structures.RundownAssets;
import swing.SingletonHolder;
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
				RundownFrame.refresh();

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
		Asset dude01 = new Asset("DE01", "2201", "", "F15C", "FTR", "DUDE01", "88AG");
		Asset dude02 = new Asset("DE02", "2202", "", "F15C", "FTR", "DUDE02", "88AG");
		Asset dude03 = new Asset("DE03", "2203", "", "F15C", "FTR", "DUDE03", "88AG");

		dude01.setAltBlock("120", "120");
		dude02.setAltBlock("200", "200");
		dude03.setAltBlock("300", "400");
		ATOAssets.getInstance().add(dude01);
		RundownAssets.getInstance().add(dude01);

		ATOAssets.getInstance().add(dude02);
		RundownAssets.getInstance().add(dude02);

		ATOAssets.getInstance().add(dude03);
		RundownAssets.getInstance().add(dude03);

		((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();

		((Window) SingletonHolder.getInstanceOf(RundownFrame.class)).setVisible(true);
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
		refreshThread.interrupt();
		try {
			refreshThread.start();
		} catch (IllegalThreadStateException e) {
			// keep running
		}

	}
}
