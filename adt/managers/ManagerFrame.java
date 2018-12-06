package managers;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.RundownFrame;
import utilities.ImageLibrary;

/**
 * Main frame for the Airspace Manager, MILDECON assist, and Lowdown Managers.
 * 
 * @author John McCarthy
 * 
 *         TODO - massive implementation needed
 *
 */
public class ManagerFrame extends JFrame {

	private static final long serialVersionUID = 311927586878557417L;

	private static ManagerFrame instance = new ManagerFrame();

	private JPanel content;

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ManagerFrame getInstance() {
		return instance;
	}

	/**
	 * Display the manager frame
	 */
	public static void openManager() {
		instance.setLocationRelativeTo(RundownFrame.getInstance());
		instance.setVisible(true);
	}

	private ManagerFrame() {
		setSize(800, 900);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setIconImage(ImageLibrary.getImage("searchIcon"));
		this.setTitle("Airspace Manager");
		this.setLocationRelativeTo(RundownFrame.getInstance());
		this.setAirspacePanel();
		this.add(this.content);
	}

	/**
	 * Set the content panel to be the airspace manager.
	 */
	public void setAirspacePanel() {
		this.content = AirspaceManagerForm.getInstance();
	}
}
