package main;

import java.awt.Window;

import rundown.gui.RundownFrame;
import structures.ADTRobot;
import structures.ATOAssets;
import structures.Asset;
import structures.ListOfAsset;
import structures.RundownAssets;
import swing.SingletonHolder;
import utilities.Configuration;
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
				RundownFrame.refresh();
				ADTRobot.sleep(1000);
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

		initialize();

		((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();

		//// Here starts the real code -- everything above delete on production
		((Window) SingletonHolder.getInstanceOf(RundownFrame.class)).setVisible(true);
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());

		refreshThread.start();
	}

	/**
	 * Setup a rundown and test ATO data if it is not in production
	 */
	private static void initialize() {
		if (Configuration.getInstance().isDebug()) {
			Asset eagle01 = new Asset("EE01", "2201", "", "F15C", "FTR", "EAGLE01", "88AG");
			Asset eagle02 = new Asset("EE02", "2202", "", "F15C", "FTR", "EAGLE02", "88AG");
			Asset eagle03 = new Asset("EE03", "2203", "", "F15C", "FTR", "EAGLE03", "88AG");
			Asset engle03 = new Asset("EE03", "2204", "", "F15C", "FTR", "ENGLE03", "88AG");

			eagle01.setAltBlock("120", "120");
			eagle02.setAltBlock("200", "200");
			eagle03.setAltBlock("300", "400");

			ListOfAsset atoAssets = (ListOfAsset) SingletonHolder.getInstanceOf(ATOAssets.class);
			atoAssets.add(engle03);

			atoAssets.add(eagle01);
			RundownAssets.getInstance().add(eagle01);

			atoAssets.add(eagle02);
			RundownAssets.getInstance().add(eagle02);

			atoAssets.add(eagle03);
			RundownAssets.getInstance().add(eagle03);
		}
	}
}
