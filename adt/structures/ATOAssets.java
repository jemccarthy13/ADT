package structures;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import atoLookup.ATOLookupFrame;
import atoLookup.ATOSearchPanel;
import rundown.model.RundownTableModel;
import swing.SingletonHolder;
import utilities.DebugUtility;

/**
 * An ArrayList of Assets for the ATO Lookup form
 */
public class ATOAssets extends ArrayList<Asset> {

	/**
	 * Expose the FileChooser to tests
	 */
	static JFileChooser fc = new JFileChooser();

	private static final long serialVersionUID = 1480309653414453245L;
	private static ATOAssets instance = new ATOAssets();

	private ATOAssets() {
	}

	/**
	 * @return single instance
	 */
	public static ATOAssets getInstance() {
		return instance;
	}

	/**
	 * To handle override / re-import, allow ATOAssets to be overwritten.
	 * 
	 * @param newInstance - replacement instance
	 * 
	 * @todo - check if this is used anywhere
	 */
	public static void resetInstance(ATOAssets newInstance) {
		instance.clear();
		for (Asset ast : newInstance) {
			instance.add(ast);
		}
		((Component) SingletonHolder.getInstanceOf(ATOLookupFrame.class)).repaint();
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
	 * @param row    - the row of the rundown table that has the data
	 * @param column - the col of the rundown table that was edited
	 * @param val    - the value of the rundown table at that location
	 */
	public void lookup(int row, int column, String val) {
		int prevCount = 0;
		int atoFoundCount = 0;
		Asset prevAsset = null;
		Asset atoAsset = null;

		for (int x = 0; x < PreviousAssets.getInstance().size(); x++) {
			Asset ass = this.get(x);
			String compVal = "";
			if (column == 0) {
				compVal = ass.getID().getVCS();
			} else if (column == 1) {
				compVal = ass.getID().getMode2();
			} else {
				compVal = ass.getID().getFullCallsign();
			}
			if (val.equals(compVal)) {
				prevCount++;
				prevAsset = ass;
			}
		}

		for (int x = 0; x < this.size(); x++) {
			Asset ass = this.get(x);
			String compVal = "";
			if (column == 0) {
				compVal = ass.getID().getVCS();
			} else if (column == 1) {
				compVal = ass.getID().getMode2();
			} else {
				compVal = ass.getID().getFullCallsign();
			}
			if (val.equals(compVal)) {
				atoFoundCount++;
				atoAsset = ass;
			}
		}

		if (atoFoundCount > 1) {
			DebugUtility.trace(this.getClass(), "Found more than one: " + val);

			((RundownTableModel) SingletonHolder.getInstanceOf(RundownTableModel.class)).setValueAt("", row, column);
			// Open the ATO Lookup frame
			// Enter the search terms in the correct UI
			// Press search button and wait for user to choose correct asset
			((Component) SingletonHolder.getInstanceOf(ATOLookupFrame.class)).setVisible(true);
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
		} else if (prevCount == 1 && atoFoundCount == 1) {
			copyAsset(prevAsset, row);
		} else if (prevCount == 0 && atoFoundCount == 1) {
			copyAsset(atoAsset, row);
		} else if (prevCount == 0 && atoFoundCount == 0) {
			DebugUtility.trace(this.getClass(), "Tried lookup for: " + val + ". No asset found");
		}
	}

	/**
	 * Copy a particular asset into a given row
	 * 
	 * @param asst - the asset to copy
	 * @param row  - the row to copy into
	 */
	public void copyAsset(Asset asst, int row) {
		RundownTableModel m = (RundownTableModel) SingletonHolder.getInstanceOf(RundownTableModel.class);
		m.setValueAt(asst.getID().getVCS(), row, 0, true, false);
		m.setValueAt(asst.getID().getMode2(), row, 1, true, false);
		m.setValueAt(asst.getAirspace(), row, 2, true, false);
		m.setValueAt(asst.getAlt().getLower(), row, 3, true, false); // lower
		m.setValueAt(asst.getAlt().getUpper(), row, 4, true, false); // upper
		m.setValueAt(asst.getStatus(), row, 5, true, false); // status
		m.setValueAt(asst.getID().getSpecType(), row, 6, true, false);
		m.setValueAt(asst.getID().getTypeCat(), row, 7, true, false);
		m.setValueAt(asst.getID().getFullCallsign(), row, 8, true, false);
	}
}
