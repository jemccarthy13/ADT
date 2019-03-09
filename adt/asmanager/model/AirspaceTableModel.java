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
		Airspace aspace = (Airspace) list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			aspace.setAddToRundown((Boolean) aValue);
			break;
		case 1:
			aspace.setColor((Color) aValue);
			break;
		case 2:
			aspace.setName(aValue.toString().toUpperCase());
			break;
		case 3:
			aspace.setAirspace(aValue.toString().toUpperCase());
			break;
		case 4:
			aspace.getAlt().setLower(aValue.toString().toUpperCase());
			break;
		case 5:
			aspace.getAlt().setUpper(aValue.toString().toUpperCase());
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
		Airspace aspace = (Airspace) list.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return aspace.isAddToRundown();
		case 1:
			return aspace.getColor();
		case 2:
			return aspace.getName();
		case 3:
			return aspace.getAirspace();
		case 4:
			return aspace.getAlt().getLower();
		case 5:
			return aspace.getAlt().getUpper();
		default:
		}
		return null;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.columnNames[columnIndex];
	}
}
