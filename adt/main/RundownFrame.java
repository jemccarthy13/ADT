package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import rundown.gui.RundownMenuBar;
import rundown.gui.RundownPanel;
import rundown.model.RundownTable;
import swing.BaseFrame;
import swing.GUI;
import utilities.Configuration;
import utilities.ImageLibrary;

/**
 * The starting point of the Rundown application
 */
public class RundownFrame extends BaseFrame {

	private static final long serialVersionUID = -5736125343545871775L;

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
		if (Configuration.isCompactMode()) {
			this.setSize((int) (d.width * 0.30), (int) (d.height * 0.7));
		} else {
			this.setSize((int) (d.width * 0.4), (int) (d.height * 0.7));
		}
		RundownTable.getInstance().resizeColumns();
		this.repaint();
		RundownTable.getInstance().repaint();
	}

	/**
	 * @return the ADT client associated with this rundown
	 */
	public static ADTClient getClient() {
		return client;
	}
}
