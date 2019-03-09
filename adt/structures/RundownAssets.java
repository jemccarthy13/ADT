package structures;

import java.awt.Component;

import rundown.gui.RundownFrame;
import swing.SingletonHolder;
import utilities.DebugUtility;

/**
 * A list of assets that are in the rundown.
 */
public class RundownAssets extends ListOfAsset {

	private static final long serialVersionUID = 1480309653414453245L;
	private static RundownAssets instance = new RundownAssets();
	private static int forcedRow = -1;

	/**
	 * Start a new instance with one blank asset
	 */
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
			((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
		}
	}

	/**
	 * @return true iff add is overwriting data
	 */
	public boolean forceAdd() {
		return RundownAssets.forcedRow != -1;
	}

	/**
	 * @param ass
	 */
	public static void force(Asset ass) {
		instance.get(RundownAssets.forcedRow).getID().setVCS(ass.getID().getVCS());
		instance.get(RundownAssets.forcedRow).getID().setMode2(ass.getID().getMode2());
		instance.get(RundownAssets.forcedRow).getID().setSpecType(ass.getID().getSpecType());
		instance.get(RundownAssets.forcedRow).getID().setTypeCat(ass.getID().getTypeCat());
		instance.get(RundownAssets.forcedRow).getID().setFullCallsign(ass.getID().getFullCallsign());
	}

	/**
	 * @param i
	 */
	public static void setForcedRow(int i) {
		DebugUtility.debug(RundownAssets.class, "Setting forced row - " + i);
		RundownAssets.forcedRow = i;
	}

	@Override
	public void create() {
		// Auto-generated method stub
	}
}
