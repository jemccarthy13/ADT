package ato;

import java.util.HashMap;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * Rundown table model stores the data for the rundown
 * 
 * @author John McCarthy
 *
 */
public class ATOTableModel extends AbstractTableModel implements TableModelListener {
	private static final long serialVersionUID = -9017185692217463087L;

	private String[] fullColumnNames = { "MSN#", "AMCMSN", "PKGID", "MSNCC", "MSN", "SECMSN", "ALERT", "DEPLOC",
			"ARRLOC", "#AC", "ACTYPE", "CALLSIGN", "CONFIG", "SECCONFIG", "M1", "M2", "M3" };

	private HashMap<Integer, Integer[]> lockedCells = new HashMap<Integer, Integer[]>();

	private static ATOTableModel instance = new ATOTableModel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOTableModel getInstance() {
		return instance;
	}

	/**
	 * @return the current list of locked cells
	 */
	public HashMap<Integer, Integer[]> getLockedCells() {
		return this.lockedCells;
	}

	private ATOTableModel() {
		this.addTableModelListener(this);
	}

	// By default forward all events to all the listeners.
	@Override
	public void tableChanged(TableModelEvent e) {
		fireTableChanged(e);
	}

	@Override
	public int getRowCount() {
		return ATOData.getInstance().size();
	}

	@Override
	public int getColumnCount() {
		int colCount = this.fullColumnNames.length;
		return colCount;
	}

	@Override
	public String getColumnName(int columnIndex) {
		String colName = this.fullColumnNames[columnIndex];
		return colName;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		default:
			return ATOData.getInstance().get(rowIndex).getVal(columnIndex);
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
	public static void setValueAt(Object aValue, int rowIndex, int columnIndex, boolean sendMessage, boolean doLookup) {
		while (ATOData.getInstance().size() <= rowIndex) {
			ATOData.getInstance().add(new ATOAsset());
		}
		if (ATOData.getInstance().size() <= rowIndex) {
			ATOData.getInstance().add(new ATOAsset());
		}
		String val = aValue.toString().toUpperCase();

		switch (columnIndex) {
		default:
			ATOData.getInstance().get(rowIndex).set(columnIndex, val);
			break;
		}

		ATOData.checkAddNew();
	}

}
