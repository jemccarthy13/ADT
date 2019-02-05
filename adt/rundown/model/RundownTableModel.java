package rundown.model;

import javax.swing.DefaultRowSorter;

import rundown.gui.RundownFrame;
import structures.ATOAssets;
import structures.Asset;
import structures.LockedCells;
import structures.RundownAssets;
import utilities.ADTTableModel;

/**
 * Rundown table model stores the data for the rundown
 */
public class RundownTableModel extends ADTTableModel<Asset> {
	private static final long serialVersionUID = -9017185692217463087L;

	@Override
	public void create() {
		this.fullColumnNames.add("VCS");
		this.fullColumnNames.add("Mode 2");
		this.fullColumnNames.add("Location");
		this.fullColumnNames.add("Alt L");
		this.fullColumnNames.add("Alt U");
		this.fullColumnNames.add("Status");
		this.fullColumnNames.add("Type");
		this.fullColumnNames.add("Category");
		this.fullColumnNames.add("Full Callsign");
		this.fullColumnNames.add("In Conflict");

		this.items = RundownAssets.getInstance();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return !LockedCells.isLocked(rowIndex, columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return RundownAssets.getInstance().get(rowIndex).getVCS();
		case 1:
			return RundownAssets.getInstance().get(rowIndex).getMode2();
		case 2:
			return RundownAssets.getInstance().get(rowIndex).getAirspace();
		case 3:
			return RundownAssets.getInstance().get(rowIndex).getLowerAlt();
		case 4:
			return RundownAssets.getInstance().get(rowIndex).getUpperAlt();
		case 5:
			return RundownAssets.getInstance().get(rowIndex).getStatus();
		case 6:
			return RundownAssets.getInstance().get(rowIndex).getTypeCat();
		case 7:
			return RundownAssets.getInstance().get(rowIndex).getSpecType();
		case 9:
			return RundownAssets.getInstance().get(rowIndex).inConflict;
		default:
			return RundownAssets.getInstance().get(rowIndex).getFullCallsign();
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		setValueAt(aValue, rowIndex, columnIndex, true, true);
	}

	/**
	 * Set the value of a particular cell. Then send a message to the clients and
	 * perform the ATO lookup if desired.
	 * 
	 * @param aValue      - the value to put in the cell
	 * @param rowIndex    - the row of the cell
	 * @param columnIndex - the col of the cell
	 * @param sendMessage - true to send a message to clients
	 * @param doLookup    - true to copy over data if found
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setValueAt(Object aValue, int rowIndex, int columnIndex, boolean sendMessage, boolean doLookup) {
		while (this.items.size() <= rowIndex) {
			this.items.add(new Asset());
		}
		String val = aValue.toString().toUpperCase();

		switch (columnIndex) {
		case 0:
			this.items.get(rowIndex).setVCS(val);
			if (doLookup) {
				RundownAssets.setForcedRow(rowIndex);
				ATOAssets.staticInstance().lookup(rowIndex, 0, val);
			}
			break;
		case 1:
			this.items.get(rowIndex).setMode2(val);
			if (doLookup) {
				RundownAssets.setForcedRow(rowIndex);
				ATOAssets.staticInstance().lookup(rowIndex, 1, val);
			}
			break;
		case 2:
			this.items.get(rowIndex).setAirspace(val);
			break;
		case 3:
			this.items.get(rowIndex).setLowerAlt(val);
			break;
		case 4:
			this.items.get(rowIndex).setUpperAlt(val);
			break;
		case 5:
			this.items.get(rowIndex).setStatus(val);
			break;
		case 6:
			this.items.get(rowIndex).setTypeCat(val);
			break;
		case 7:
			this.items.get(rowIndex).setSpecType(val);
			break;
		case 8:
			this.items.get(rowIndex).setFullCallsign(val);
			break;
		default:
			break;
		}

		if (sendMessage) {
			RundownFrame.getClient().sendMessage("set," + val + "," + rowIndex + "," + columnIndex);
		}
		RundownAssets.checkAddNew();

		((DefaultRowSorter) RundownTable.getInstance().getRowSorter()).setRowFilter(null);
	}

	@Override
	public void addNew() {
		this.items.add(new Asset());
	}
}
