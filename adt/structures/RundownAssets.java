package structures;

import java.util.ArrayList;

import main.RundownFrame;
import swing.GUI;

/**
 * A list of assets that are in the rundown.
 * 
 * @author John McCarthy
 */
public class RundownAssets extends ArrayList<Asset> {

	private static final long serialVersionUID = 1480309653414453245L;
	private static RundownAssets instance = new RundownAssets();

	private RundownAssets() {
		this.add(new Asset());
	}

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static RundownAssets getInstance() {
		return instance;
	}

	/**
	 * Remove all assets from the rundown.
	 */
	public static void zeroize() {
		instance.clear();
	}

	/**
	 * Add a new blank asset to the rundown
	 */
	public static void addNew() {
		instance.add(new Asset());
	}

	/**
	 * Check the rundown for an entirely blank row. If one doesn't exist, add one
	 * (to create the feel of a continuous form)
	 */
	public static void checkAddNew() {
		boolean blankRow = false;
		for (int x = 0; x < instance.size(); x++) {
			if (instance.get(x).isBlank()) {
				blankRow = true;
			}
		}
		if (!blankRow) {
			addNew();
			GUI.FRAMES.getInstanceOf(RundownFrame.class).repaint();
		}
	}
}
