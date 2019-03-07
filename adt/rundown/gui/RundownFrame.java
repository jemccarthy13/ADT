package rundown.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import main.ADTClient;
import rundown.model.RundownTable;
import swing.BaseFrame;
import swing.SingletonHolder;
import utilities.Configuration;
import utilities.DebugUtility;
import utilities.ImageLibrary;
import utilities.Output;

/**
 * The main Frame of the ADT
 * 
 * TODO - Add a refresh thread. Store the current column sort selection do the
 * menu bar refresh click, then resort based on user's previous preference
 */
public class RundownFrame extends BaseFrame {

	private static final long serialVersionUID = -5736125343545871775L;
	// private static ADTClient client;
	private ADTClient client;

	/**
	 * Wrapper to repaint the rundown
	 */
	public static void refresh() {
		((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
	}

	/**
	 * Create the frame.
	 */
	@Override
	public void create() {
		// start the client - and start the server if one isn't found
		this.client = new ADTClient();
		this.client.start();

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// make it look like windows rather than Java
		Configuration.setLookAndFeel(RundownFrame.class);

		// set some frame settings
		this.setIconImage(ImageLibrary.getImage("/graphics/AF-Roundel.jpg"));
		this.setTitle("Airspace Deconfliction Tool");

		// add the menu bar at top
		this.setJMenuBar(RundownMenuBar.getInstance());

		// add the rundown (buttons, table)
		this.add((Component) SingletonHolder.getInstanceOf(RundownPanel.class));

		// handle the original sizing
		this.handleCompact();

		// don't display until the client/server connection has been established
		int attempts = 0;
		while (!this.client.isConnected() && attempts < 10) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// keep waiting
			}
			attempts++;
		}

		// display
		if (this.client.isConnected()) {
			this.setVisible(true);
		} else {
			/** TODO - better exit handler */
			DebugUtility.error(Output.class, "Unable to connect to server.");
			System.exit(0);
		}
	}

	/**
	 * Resize the columns of the table
	 */
	public void handleCompact() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		if (Configuration.getCompact() != 0) {
			/** @TODO change this to not be a hard value */
			this.setSize(800, (int) (d.height * 0.7));
		} else {
			this.setSize(800, (int) (d.height * 0.7));
		}
		RundownTable.getInstance().resizeColumns();
		this.repaint();
		RundownTable.getInstance().repaint();
	}

	/**
	 * Expose the Rundown client
	 * 
	 * @return the client
	 */
	public ADTClient getClient() {
		return this.client;
	}
}
