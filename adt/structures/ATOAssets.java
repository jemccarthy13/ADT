package structures;

import java.util.ArrayList;

import javax.swing.JFileChooser;

import atoLookup.ATOLookupFrame;
import rundown.model.RundownTableModel;
import swing.GUI;

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
		instance = newInstance;
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
				RundownTableModel m = (RundownTableModel) GUI.MODELS.getInstanceOf(RundownTableModel.class);
				m.setValueAt(ass.getVCS(), row, 0, true, false);
				m.setValueAt(ass.getMode2(), row, 1, true, false);
				m.setValueAt(ass.getAirspace(), row, 2, true, false);
				m.setValueAt(ass.getLowerAlt(), row, 3, true, false);
				m.setValueAt(ass.getUpperAlt(), row, 4, true, false);
				m.setValueAt(ass.getStatus(), row, 5, true, false);
				m.setValueAt(ass.getSpecType(), row, 6, true, false);
				m.setValueAt(ass.getTypeCat(), row, 7, true, false);
				m.setValueAt(ass.getFullCallsign(), row, 8, true, false);
			}
		}
	}
}
