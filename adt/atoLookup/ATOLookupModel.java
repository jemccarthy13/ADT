package atoLookup;

import structures.ATOAssets;
import structures.Asset;
import structures.ListOfAsset;
import swing.SingletonHolder;
import utilities.ADTTableModel;

/**
 * A model to hold ATO Lookup table data for display to the user
 */
public class ATOLookupModel extends ADTTableModel {

	private static final long serialVersionUID = -4738119325760523923L;

	@Override
	public void addNew() {
		// Required method
		this.items.add(new Asset());
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
	public void create() {
		this.columnNames.clear();
		this.columnNames.add("Add");
		this.columnNames.add("Full Callsign");
		this.columnNames.add("VCS");
		this.columnNames.add("Mode 2");
		this.columnNames.add("Type");
		this.columnNames.add("Category");
		this.columnNames.add("On Station");
		this.columnNames.add("Off Station");
		this.items = (ListOfAsset) SingletonHolder.getInstanceOf(ATOAssets.class);
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.size();
	}
}
