package rundown;

import java.util.HashMap;

import javax.swing.DefaultRowSorter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import main.RundownFrame;
import structures.ATOAssets;
import structures.Asset;
import structures.RundownAssets;

/**
 * Rundown table model stores the data for the rundown
 * 
 * @author John McCarthy
 *
 */
public class RundownTableModel extends AbstractTableModel implements TableModelListener {
	private static final long serialVersionUID = -9017185692217463087L;

	private String[] columnNames = { "VCS", "Mode 2", "Location", "Alt L", "Alt U", "Status" };
	private String[] fullColumnNames = { "VCS", "Mode 2", "Location", "Alt L", "Alt U", "Status", "Type", "Category",
			"Full Callsign" };

	private HashMap<Integer, Integer[]> lockedCells = new HashMap<Integer, Integer[]>();

	private boolean compactMode = false;

	private static RundownTableModel instance = new RundownTableModel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static RundownTableModel getInstance() {
		return instance;
	}

	/**
	 * @return the current list of locked cells
	 */
	public HashMap<Integer, Integer[]> getLockedCells() {
		return this.lockedCells;
	}

	/**
	 * Set a particular cell as locked by the given user
	 * 
	 * @param user
	 *            - user who has locked the cell
	 * @param row
	 *            - row of the cell
	 * @param column
	 *            - col of the cell
	 */
	public void setLocked(Integer user, Integer row, Integer column) {
		if (!this.lockedCells.containsKey(user)) {
			this.lockedCells.put(user, new Integer[2]);
		}
		this.lockedCells.get(user)[0] = row;
		this.lockedCells.get(user)[1] = column;
	}

	/**
	 * Clear all locks of a particular user
	 * 
	 * @param user
	 *            - the user whose locks should be cleared
	 */
	public void unlockUser(Integer user) {
		setUnlocked(user, -1, -1);
	}

	/**
	 * Set a particular cell as unlocked by the given user
	 * 
	 * @param user
	 *            - user who has unlocked the cell
	 * @param row
	 *            - row of the cell
	 * @param column
	 *            - col of the cell
	 */
	public void setUnlocked(Integer user, Integer row, Integer column) {
		if (this.lockedCells.containsKey(user)) {
			this.lockedCells.remove(user);
		}
	}

	/**
	 * Check if a given cell is marked as locked
	 * 
	 * @param row
	 *            - row of the cell
	 * @param column
	 *            - col of the cell
	 * @return true iff a user has the cell locked
	 */
	public boolean isLocked(int row, int column) {
		boolean locked = false;
		for (Integer user : this.lockedCells.keySet()) {
			if (this.lockedCells.get(user)[0] == row && this.lockedCells.get(user)[1] == column) {
				locked = true;
			}
		}
		return locked;
	}

	private RundownTableModel() {
		this.addTableModelListener(this);
	}

	// By default forward all events to all the listeners.
	@Override
	public void tableChanged(TableModelEvent e) {
		fireTableChanged(e);
	}

	/**
	 * @param val
	 *            - new value for if the table is in compact mode or not
	 */
	public void setCompact(boolean val) {
		this.compactMode = val;
	}

	/**
	 * @return true iff table is in compact mode
	 */
	public boolean isCompactMode() {
		return this.compactMode;
	}

	@Override
	public int getRowCount() {
		return RundownAssets.getInstance().size();
	}

	@Override
	public int getColumnCount() {
		int colCount = this.fullColumnNames.length;
		if (this.compactMode) {
			colCount = this.columnNames.length;
		}
		return colCount;
	}

	@Override
	public String getColumnName(int columnIndex) {
		String colName = this.fullColumnNames[columnIndex];
		if (this.compactMode) {
			colName = this.columnNames[columnIndex];
		}
		return colName;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return !isLocked(rowIndex, columnIndex);
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
	 * @param aValue
	 *            - the value to put in the cell
	 * @param rowIndex
	 *            - the row of the cell
	 * @param columnIndex
	 *            - the col of the cell
	 * @param sendMessage
	 *            - true to send a message to clients
	 * @param doLookup
	 *            - true to copy over data if found
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setValueAt(Object aValue, int rowIndex, int columnIndex, boolean sendMessage, boolean doLookup) {
		while (RundownAssets.getInstance().size() <= rowIndex) {
			RundownAssets.getInstance().add(new Asset());
		}
		if (RundownAssets.getInstance().size() <= rowIndex) {
			RundownAssets.getInstance().add(new Asset());
		}
		String val = aValue.toString().toUpperCase();

		switch (columnIndex) {
		case 0:
			RundownAssets.getInstance().get(rowIndex).setVCS(val);
			if (doLookup)
				ATOAssets.getInstance().lookup(rowIndex, 0, val);
			break;
		case 1:
			RundownAssets.getInstance().get(rowIndex).setMode2(val);
			if (doLookup)
				ATOAssets.getInstance().lookup(rowIndex, 1, val);
			break;
		case 2:
			RundownAssets.getInstance().get(rowIndex).setAirspace(val);
			break;
		case 3:
			RundownAssets.getInstance().get(rowIndex).setLowerAlt(val);
			break;
		case 4:
			RundownAssets.getInstance().get(rowIndex).setUpperAlt(val);
			break;
		case 5:
			RundownAssets.getInstance().get(rowIndex).setStatus(val);
			break;
		case 6:
			RundownAssets.getInstance().get(rowIndex).setTypeCat(val);
			break;
		case 7:
			RundownAssets.getInstance().get(rowIndex).setSpecType(val);
			break;
		case 8:
			RundownAssets.getInstance().get(rowIndex).setFullCallsign(val);
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

}
