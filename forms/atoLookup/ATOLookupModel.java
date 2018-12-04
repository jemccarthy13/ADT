package atoLookup;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import structures.ATOAssets;
import structures.Asset;

/**
 * A model to hold ATO Lookup table data for display to the user
 * 
 * @author John McCarthy
 *
 */
public class ATOLookupModel extends AbstractTableModel {

	private static final long serialVersionUID = -4738119325760523923L;

	/** Header names */
	String[] fullColumnNames = { "Add", "Full Callsign", "VCS", "Mode 2", "Type", "Category", "On Station",
			"Off Station" };
	private static ATOLookupModel instance = new ATOLookupModel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOLookupModel getInstance() {
		return instance;
	}

	private ATOLookupModel() {
	}

	@Override
	public int getRowCount() {
		return ATOAssets.getInstance().size();
	}

	@Override
	public int getColumnCount() {
		return this.fullColumnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.fullColumnNames[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> retClass = String.class;
		if (columnIndex == 0) {
			retClass = Boolean.class;
		}
		return retClass;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < ATOAssets.getInstance().size()) {
			switch (columnIndex) {
			case 0:
				return ATOAssets.getInstance().get(rowIndex).isAddToRundown();
			case 1:
				return ATOAssets.getInstance().get(rowIndex).getFullCallsign();
			case 2:
				return ATOAssets.getInstance().get(rowIndex).getVCS();
			case 3:
				return ATOAssets.getInstance().get(rowIndex).getMode2();
			case 4:
				return ATOAssets.getInstance().get(rowIndex).getSpecType();
			case 5:
				return ATOAssets.getInstance().get(rowIndex).getTypeCat();
			case 6:
				return ATOAssets.getInstance().get(rowIndex).getOnStation();
			case 7:
				return ATOAssets.getInstance().get(rowIndex).getOffStation();
			default:
				return ATOAssets.getInstance().get(rowIndex).getFullCallsign();
			}
		}
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		while (ATOAssets.getInstance().size() <= rowIndex) {
			ATOAssets.getInstance().add(new Asset());
		}
		if (ATOAssets.getInstance().size() <= rowIndex) {
			ATOAssets.getInstance().add(new Asset());
		}
		switch (columnIndex) {
		case 0:
			ATOAssets.getInstance().get(rowIndex).setAddToRundown((Boolean) aValue);
			break;
		case 1:
			ATOAssets.getInstance().get(rowIndex).setFullCallsign(aValue.toString());
			break;
		case 2:
			ATOAssets.getInstance().get(rowIndex).setVCS(aValue.toString());
			break;
		case 3:
			ATOAssets.getInstance().get(rowIndex).setMode2(aValue.toString());
			break;
		case 4:
			ATOAssets.getInstance().get(rowIndex).setSpecType(aValue.toString());
			break;
		case 5:
			ATOAssets.getInstance().get(rowIndex).setTypeCat(aValue.toString());
			break;
		case 6:
			ATOAssets.getInstance().get(rowIndex).setOnStation(aValue.toString());
			break;
		case 7:
			ATOAssets.getInstance().get(rowIndex).setOffStation(aValue.toString());
			break;
		default:
			break;
		}
	}

	/**
	 * Need to implement, but I have nothing to put here.
	 */
	@Override
	public void addTableModelListener(TableModelListener l) {
		// Do nothing
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// Do nothing
	}

}
