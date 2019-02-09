package asmanager.model;

import java.awt.Color;

import javax.swing.table.AbstractTableModel;

import structures.Airspace;
import structures.AirspaceList;
import swing.SingletonHolder;

/**
 * A table model for the airspace manager's airspaces
 * 
 */
public class AirspaceTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 6374738048245958214L;
	private static AirspaceTableModel instance = new AirspaceTableModel();

	private String[] columnNames = { "Activate", "Color", "Name", "Location", "Alt L", "Alt U" };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> retClass = String.class;
		if (columnIndex == 0) {
			retClass = Boolean.class;
		} else if (columnIndex == 1) {
			retClass = Color.class;
		}
		return retClass;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		AirspaceList list = (AirspaceList) SingletonHolder.getInstanceOf(AirspaceList.class);
		while (list.size() <= rowIndex) {
			list.add(new Airspace());
		}
		if (list.size() <= rowIndex) {
			list.add(new Airspace());
		}
		switch (columnIndex) {
		case 0:
			list.get(rowIndex).setAddToRundown((Boolean) aValue);
			break;
		case 1:
			list.get(rowIndex).setColor((Color) aValue);
			break;
		case 2:
			list.get(rowIndex).setName(aValue.toString().toUpperCase());
			break;
		case 3:
			list.get(rowIndex).setAirspace(aValue.toString().toUpperCase());
			break;
		case 4:
			list.get(rowIndex).getAlt().setLower(aValue.toString().toUpperCase());
			break;
		case 5:
			list.get(rowIndex).getAlt().setUpper(aValue.toString().toUpperCase());
			break;
		default:
			break;
		}

		((AirspaceList) (SingletonHolder.getInstanceOf(AirspaceList.class))).checkAddNew();
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
		return ((AirspaceList) SingletonHolder.getInstanceOf(AirspaceList.class)).size();
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
		AirspaceList list = ((AirspaceList) SingletonHolder.getInstanceOf(AirspaceList.class));
		switch (columnIndex) {
		case 0:
			return list.get(rowIndex).isAddToRundown();
		case 1:
			return list.get(rowIndex).getColor();
		case 2:
			return list.get(rowIndex).getName();
		case 3:
			return list.get(rowIndex).getAirspace();
		case 4:
			return list.get(rowIndex).getAlt().getLower();
		case 5:
			return list.get(rowIndex).getAlt().getUpper();
		default:
		}
		return null;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.columnNames[columnIndex];
	}
}
