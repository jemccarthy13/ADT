package main;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import rundown.gui.RundownMenuBar;
import rundown.gui.RundownPanel;
import rundown.model.RundownTable;
import swing.GUI;
import utilities.Configuration;
import utilities.ImageLibrary;
import utilities.ShutdownThread;

/**
 * The starting point of the Rundown application.
 * 
 * @author John McCarthy
 */
public class RundownFrame extends JFrame {

	private static final long serialVersionUID = -5736125343545871775L;

	private final static RundownFrame instance = new RundownFrame();

	/**
	 * Singleton implementation.
	 * 
	 * @return single instance of the rundown frame
	 */
	public static RundownFrame getInstance() {
		return instance;
	}

	private static ADTClient client;

	/**
	 * Launch the application.
	 * 
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		RundownFrame.getInstance().setVisible(true);
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
	}

	/**
	 * Create the frame.
	 */
	private RundownFrame() {

		// make it look like windows rather than Java
		Configuration.setLookAndFeel();

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
