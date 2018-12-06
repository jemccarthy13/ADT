package atoLookup;

import structures.ADTTableModel;
import structures.ATOAssets;
import structures.Asset;

/**
 * A model to hold ATO Lookup table data for display to the user
 */
public class ATOLookupModel extends ADTTableModel<Asset> {

	private static final long serialVersionUID = -4738119325760523923L;

	@Override
	public void addNew() {
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
		this.fullColumnNames.clear();
		this.fullColumnNames.add("Add");
		this.fullColumnNames.add("Full Callsign");
		this.fullColumnNames.add("VCS");
		this.fullColumnNames.add("Mode 2");
		this.fullColumnNames.add("Type");
		this.fullColumnNames.add("Category");
		this.fullColumnNames.add("On Station");
		this.fullColumnNames.add("Off Station");
		this.items = ATOAssets.staticInstance();
	}
}
