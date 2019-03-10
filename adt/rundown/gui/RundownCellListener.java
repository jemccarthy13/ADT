package rundown.gui;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;

import main.ADTClient;
import messages.ADTLockedMessage;
import messages.ADTUpdateMessage;
import rundown.model.RundownTable;
import structures.Airspace;
import structures.AirspaceList;
import structures.Asset;
import structures.LockedCells;
import structures.RundownAssets;
import swing.Singleton;
import swing.SingletonHolder;
import utilities.ConflictComparer;
import utilities.DebugUtility;
import utilities.Output;

/**
 * This class listens for changes made to the data in the table via the
 * TableCellEditor. When editing is started, the cell is locked for other
 * clients. When editing stops, the cell is unlocked for other clients.
 *
 * The source of the Action is a TableCellListener instance.
 */
public class RundownCellListener implements Singleton, PropertyChangeListener, Runnable {

	private int row;
	private int column;

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		// A cell has started/stopped editing

		if ("tableCellEditor".equals(e.getPropertyName())) {
			if (RundownTable.getInstance().isEditing()) {
				processEditingStarted();
			} else {
				processEditingStopped();
			}
		}
	}

	/*
	 * Save information of the cell about to be edited
	 */
	private void processEditingStarted() {
		// The invokeLater is necessary because the editing row and editing
		// column of the table have not been set when the "tableCellEditor"
		// PropertyChangeEvent is fired.
		// This results in the "run" method being invoked

		SwingUtilities.invokeLater(this);
	}

	/*
	 * See above.
	 */
	@Override
	public void run() {
		this.row = RundownTable.getInstance().getEditingRow();
		this.column = RundownTable.getInstance().getEditingColumn();
		int modelRow = RundownTable.getInstance().convertRowIndexToModel(this.row);

		ADTClient client = ((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).getClient();

		client.sendMessage(new ADTLockedMessage(modelRow, this.column, true));
		LockedCells.setLocked(client.getSessionID(), this.row, this.column, true);
	}

	/**
	 * Check if a cell entry is a number
	 * 
	 * @param str - the value from the cell
	 * @return true iff string represents an integer
	 */
	private boolean isInt(String str) {
		boolean result = false;
		try {
			Integer.parseInt(str);
			result = true;
		} catch (NullPointerException e) {
			result = false;
		} catch (NumberFormatException e1) {
			result = false;
		}
		return result;
	}

	/**
	 * Editing is done, so we check the appropriate things (i.e. conflicts /
	 * highlighting)
	 */
	public void processEditingStopped() {

		String errMsg = "";

		String newValue = (String) (RundownTable.getInstance().getValueAt(this.row, this.column));

		// error check based on column
		if (!newValue.equals("")) {
			switch (this.column) {
			case 3:
			case 4:
				if (newValue.length() != 3 || !isInt(newValue)) {
					errMsg = "Altitude must be 3 digit numeric FL (000-999)";
				}
				break;
			case 1:
				if (newValue.length() != 4 || !isInt(newValue)) {
					errMsg = "Mode 2 must be four digits (####)";
				}
				break;
			default:
				break;
			}
		}

		if (!errMsg.equals("")) {
			int modelRow = RundownTable.getInstance().convertRowIndexToModel(this.row);
			DebugUtility.error(this.getClass(), errMsg);
			Output.forceInfoMessage("", errMsg);
			RundownTable.getInstance().setValueAt("", this.row, this.column);

			ADTClient client = ((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).getClient();

			client.sendMessage(new ADTLockedMessage(modelRow, this.column, false));

			LockedCells.setLocked(client.getSessionID(), this.row, this.column, false);

		} else {
			int modelRow = RundownTable.getInstance().convertRowIndexToModel(this.row);

			ADTClient client = ((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).getClient();

			client.sendMessage(new ADTUpdateMessage(modelRow, this.column, newValue));
			client.sendMessage(new ADTLockedMessage(modelRow, this.column, false));

			LockedCells.setLocked(client.getSessionID(), this.row, this.column, false);

			// HO-REE SHIT. The time has come.

			// we've changed airspace or altitude or aircraft type
			if (this.column == 2 || this.column == 3 || this.column == 4 || this.column == 6) {

				ConflictComparer.checkConflicts(this.row);

				checkAirspaceHighlights();
			}

			((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
		}
	}

	/**
	 * Check the rundown for airspace highlighting
	 */
	public static void checkAirspaceHighlights() {
		// now loop through every asset that has a conflict
		for (Asset other : RundownAssets.getInstance()) {

			boolean hasConflict = false;
			for (Asset asst : (AirspaceList) (SingletonHolder.getInstanceOf(AirspaceList.class))) {
				Airspace as = (Airspace) asst;
				if (as.isAddToRundown() && as.conflictsWith(other).size() > 0) {
					hasConflict = true;
					DebugUtility.trace(RundownCellListener.class, as.getColor().toString());
					other.setAirspaceHighlightColor(as.getColor());
				}
			}
			if (!hasConflict) {
				other.setAirspaceHighlightColor(Color.WHITE);
			}
		}
	}

	@Override
	public void create() {
		// do nothing on create
	}
}