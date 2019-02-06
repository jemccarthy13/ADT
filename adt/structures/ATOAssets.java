package structures;

import java.util.ArrayList;

import javax.swing.JFileChooser;

import atoLookup.ATOLookupFrame;
import atoLookup.ATOSearchPanel;
import rundown.model.RundownTableModel;
import swing.GUI;
import utilities.DebugUtility;

/**
 * An ArrayList of Assets for the ATO Lookup form
 */
public class ATOAssets extends ArrayList<Asset> implements HasInstance {

	/**
	 * Expose the FileChooser to tests
	 */
	static JFileChooser fc = new JFileChooser();

	private static final long serialVersionUID = 1480309653414453245L;
	private static ATOAssets instance = new ATOAssets();

	private ATOAssets() {
	}

	/**
	 * Singleton implementation.
	 * 
	 * @return single instance
	 */
	@Override
	public ATOAssets getInstance() {
		return instance;
	}

	/**
	 * @return single instance
	 */
	public static ATOAssets staticInstance() {
		return instance;
	}

	/**
	 * To handle override / re-import, allow ATOAssets to be overwritten.
	 * 
	 * @param newInstance - replacement instance
	 * 
	 *                    TODO - check if this is used anywhere
	 */
	public static void resetInstance(ATOAssets newInstance) {
		instance.clear();
		for (Asset ast : newInstance) {
			instance.add(ast);
		}
		GUI.FRAMES.getInstanceOf(ATOLookupFrame.class).repaint();
	}

	/**
	 * Clear ATO assets from the ATO Lookup
	 */
	public static void zeroize() {
		instance.clear();
	}

	/**
	 * Do the ATO lookup and set the rundown row's information if a match is found.
	 * 
	 * TODO - handle if multiples found
	 * 
	 * @param row    - the row of the rundown table that has the data
	 * @param column - the col of the rundown table that was edited
	 * @param val    - the value of the rundown table at that location
	 */
	public void lookup(int row, int column, String val) {
		int foundCount = 0;
		Asset foundAsset = null;
		for (int x = 0; x < this.size(); x++) {
			Asset ass = this.get(x);
			String compVal = "";
			if (column == 0) {
				compVal = ass.getVCS();
			} else if (column == 1) {
				compVal = ass.getMode2();
			} else {
				compVal = ass.getFullCallsign();
			}
			if (val.equals(compVal)) {
				foundCount++;
				foundAsset = ass;
			}
		}

		if (foundCount > 1) {
			DebugUtility.trace(this.getClass(), "Found more than one: " + val);

			GUI.MODELS.getInstanceOf(RundownTableModel.class).setValueAt("", row, column);
			// Open the ATO Lookup frame
			// Enter the search terms in the correct UI
			// Press search button and wait for user to choose correct asset
			GUI.FRAMES.getInstanceOf(ATOLookupFrame.class).setVisible(true);
			switch (column) {
			case 0:
				ATOSearchPanel.vcsBox.setText(val + "$");
				break;
			case 1:
				ATOSearchPanel.mode2Box.setText(val);
				break;
			default:
				ATOSearchPanel.callsignBox.setText(val);
			}

			ATOSearchPanel.searchBtn.doClick();
		} else if (foundCount == 1) {
			RundownTableModel m = (RundownTableModel) GUI.MODELS.getInstanceOf(RundownTableModel.class);
			m.setValueAt(foundAsset.getVCS(), row, 0, true, false);
			m.setValueAt(foundAsset.getMode2(), row, 1, true, false);
			m.setValueAt(foundAsset.getSpecType(), row, 6, true, false);
			m.setValueAt(foundAsset.getTypeCat(), row, 7, true, false);
			m.setValueAt(foundAsset.getFullCallsign(), row, 8, true, false);
		} else if (foundCount == 0) {
			DebugUtility.trace(this.getClass(), "Tried lookup for: " + val + ". No asset found");
		}
	}
}
