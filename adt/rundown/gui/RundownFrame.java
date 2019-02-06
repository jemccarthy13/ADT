package rundown.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import main.ADTClient;
import rundown.model.RundownTable;
import swing.BaseFrame;
import swing.GUI;
import utilities.Configuration;
import utilities.ImageLibrary;

/**
 * The main Frame of the ADT
 * 
 * TODO - Add a refresh thread. Store the current column sort selection do the
 * menu bar refresh click, then resort based on user's previous preference
 */
public class RundownFrame extends BaseFrame {

	private static final long serialVersionUID = -5736125343545871775L;
	// private static ADTClient client;
	private static ADTClient client;

	/**
	 * Create the frame.
	 */
	@Override
	public void create() {

		// make it look like windows rather than Java
		Configuration.setLookAndFeel(RundownFrame.class);

		// set some frame settings
		this.setIconImage(ImageLibrary.getImage("AF-Roundel"));
		this.setTitle("Airspace Deconfliction Tool");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// add the menu bar at top
		this.setJMenuBar(RundownMenuBar.getInstance());

		// add the rundown (buttons, table)
		this.add(GUI.PANELS.getInstanceOf(RundownPanel.class));

		// start the client - and start the server if one isn't found
		// client = new ADTClient();
		client = new ADTClient();
		client.establishSession();

		// handle the original sizing
		this.handleCompact();

		// display!
		this.setVisible(true);
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
	public static ADTClient getClient() {
		return RundownFrame.client;
	}
}
