package managers;

import javax.swing.table.AbstractTableModel;

import structures.Airspace;
import structures.AirspaceList;

/**
 * A table model for the airspace manager's airspaces
 * 
 * @author John McCarthy
 *
 */
public class AirspaceTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 6374738048245958214L;
	private static AirspaceTableModel instance = new AirspaceTableModel();

	private String[] columnNames = { "Add", "Color", "Name", "Location", "Alt L", "Alt U" };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> retClass = String.class;
		if (columnIndex == 0) {
			retClass = Boolean.class;
		}
		return retClass;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		while (AirspaceList.getInstance().size() <= rowIndex) {
			AirspaceList.getInstance().add(new Airspace());
		}
		if (AirspaceList.getInstance().size() <= rowIndex) {
			AirspaceList.getInstance().add(new Airspace());
		}
		switch (columnIndex) {
		case 0:
			AirspaceList.getInstance().get(rowIndex).setAddToRundown((Boolean) aValue);
			break;
		case 1:
			AirspaceList.getInstance().get(rowIndex).setName(aValue.toString());
			break;
		case 2:
			AirspaceList.getInstance().get(rowIndex).setAirspace(aValue.toString());
			break;
		case 3:
			AirspaceList.getInstance().get(rowIndex).setLowerAlt(aValue.toString());
			break;
		case 4:
			AirspaceList.getInstance().get(rowIndex).setUpperAlt(aValue.toString());
			break;
		default:
			break;
		}
	}

	/**
	 * Singleton implementation
	 * 
	 * @return single instance
	 */
	public static AirspaceTableModel getInstance() {
		return instance;
	}

	@Override
	public int getRowCount() {
		return AirspaceList.getInstance().size();
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return AirspaceList.getInstance().get(rowIndex).isAddToRundown();
		case 1:
			return AirspaceList.getInstance().get(rowIndex).getName();
		case 2:
			return AirspaceList.getInstance().get(rowIndex).getName();
		case 3:
			return AirspaceList.getInstance().get(rowIndex).getAirspace();
		case 4:
			return AirspaceList.getInstance().get(rowIndex).getLowerAlt();
		case 5:
			return AirspaceList.getInstance().get(rowIndex).getUpperAlt();
		default:
		}
		return null;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.columnNames[columnIndex];
	}
}
