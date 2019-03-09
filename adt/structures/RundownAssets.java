package structures;

import java.awt.Component;

import rundown.gui.RundownFrame;
import swing.SingletonHolder;

/**
 * A list of assets that are in the rundown / currently under control
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
			instance.add(new Asset());
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
	 * Forces an asset be placed into the rundown (i.e. ATOLookup) at the row
	 * specified by forcedRow
	 * 
	 * @param asst - the asset to force into forcedRow
	 */
	public static void force(Asset asst) {
		instance.get(RundownAssets.forcedRow).getID().setVCS(asst.getID().getVCS());
		instance.get(RundownAssets.forcedRow).getID().setMode2(asst.getID().getMode2());
		instance.get(RundownAssets.forcedRow).getID().setSpecType(asst.getID().getSpecType());
		instance.get(RundownAssets.forcedRow).getID().setTypeCat(asst.getID().getTypeCat());
		instance.get(RundownAssets.forcedRow).getID().setFullCallsign(asst.getID().getFullCallsign());
	}

	/**
	 * @param i - new forced row
	 */
	public static void setForcedRow(int i) {
		RundownAssets.forcedRow = i;
	}

	@Override
	public void create() {
		// Nothing is required
	}
}
