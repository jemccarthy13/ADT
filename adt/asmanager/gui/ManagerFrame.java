package asmanager.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;

import rundown.gui.RundownFrame;
import swing.BaseFrame;
import swing.SingletonHolder;
import utilities.DebugUtility;
import utilities.Fonts;
import utilities.ImageLibrary;

/**
 * Main frame for the Airspace Manager, MILDECON assist, and Lowdown Managers.
 */
public class ManagerFrame extends BaseFrame {

	private static final long serialVersionUID = 311927586878557417L;

	@Override
	public void create() {
		Fonts.setUIFont(new FontUIResource(Fonts.serif));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setIconImage(ImageLibrary.getImage("searchIcon"));
		this.setTitle("Airspace Manager");
		this.setLocationRelativeTo((JFrame) (SingletonHolder.getInstanceOf(RundownFrame.class)));

		this.setLayout(new BorderLayout());
		setASManager();
	}

	/**
	 * Setup the airspace manager
	 */
	public void setASManager() {
		this.setSize(900, 900);
		this.add(((JPanel) SingletonHolder.getInstanceOf(ASManagerEditPanel.class)), BorderLayout.NORTH);
		this.add(ASManagerPanel.getInstance(), BorderLayout.CENTER);
		this.repaint();
	}

	/**
	 * Set the content panel to contain just the add form and shorten the user
	 * action chain
	 */
	public void setMildeon() {
		this.setSize(800, 300);
		this.remove(ASManagerPanel.getInstance());
		// Add the edit panel on top
		this.add(((JPanel) SingletonHolder.getInstanceOf(ASManagerEditPanel.class)));
		this.repaint();
	}

	/**
	 * Set the content panel to show a table of SAMs instead
	 */
	public void setLowdown() {
		DebugUtility.error(ManagerFrame.class, "SETUP AS MANAGER FRAME FOR LOWDOWN");
	}
}
