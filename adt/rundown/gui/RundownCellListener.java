package rundown.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;

import main.ADTClient;
import main.RundownFrame;
import rundown.model.RundownTable;
import structures.Asset;
import structures.LockedCells;
import structures.RundownAssets;
import utilities.DebugUtility;
import utilities.Output;

/**
 * This class listens for changes made to the data in the table via the
 * TableCellEditor. When editing is started, the cell is locked for other
 * clients. When editing stops, the cell is unlocked for other clients.
 *
 * The source of the Action is a TableCellListener instance.
 */
public class RundownCellListener implements PropertyChangeListener, Runnable {

	private int row;
	private int column;

	/**
	 * Create a TableCellListener.
	 *
	 * @param action the Action to invoke when cell data is changed
	 */
	public RundownCellListener() {
	}

	//
	// Implement the PropertyChangeListener interface
	//
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
		RundownFrame.getClient().sendMessage("locked," + this.row + "," + this.column);
		LockedCells.setLocked(RundownFrame.getClient().getSessionID(), this.row, this.column);
	}

	/*
	 * Update the Cell history when necessary
	 */
	private void processEditingStopped() {

		String errMsg = "";

		if (this.column == 3 || this.column == 4) {
			if (((String) (RundownTable.getInstance().getValueAt(this.row, this.column))).length() != 3) {
				errMsg = "Altitude needs to be 3 digit FL (000-999)";
			}
		}

		if (this.column == 1) {
			if (((String) (RundownTable.getInstance().getValueAt(this.row, this.column))).length() < 4) {
				errMsg = "Mode 2 must be four digits (XXXX)";
			}
		}

		if (!errMsg.equals("")) {
			DebugUtility.error(ADTClient.class, errMsg);
			Output.forceInfoMessage("", errMsg);
			RundownTable.getInstance().setValueAt("", this.row, this.column);
		}

		RundownFrame.getClient().sendMessage("unlocked," + this.row + "," + this.column);
		LockedCells.setUnlocked(RundownFrame.getClient().getSessionID(), this.row, this.column);

		// HO-REE SHIT. The time has come.
		boolean hasConflict = false;
		if (this.column == 2 || this.column == 3 || this.column == 4) {
			Asset first = RundownAssets.getInstance().get(this.row);
			int count = 0;
			for (Asset other : RundownAssets.getInstance()) {
				if (this.row != count) {
					if (first.sharesAirspaceWith(other).size() > 0) {
						DebugUtility.error(this.getClass(), "OVERLAP");
						hasConflict = true;
						other.inConflict = true;
					}
				}
				count++;
			}
			if (hasConflict) {
				first.inConflict = true;
			} else {
				first.inConflict = false;
			}

			int cnt1 = 0;
			hasConflict = false;
			for (Asset fst : RundownAssets.getInstance()) {
				if (fst.inConflict == true) {
					count = 0;
					for (Asset other : RundownAssets.getInstance()) {
						if (cnt1 != count) {
							if (fst.sharesAirspaceWith(other).size() > 0) {
								DebugUtility.error(this.getClass(), "OVERLAP");
								hasConflict = true;
								other.inConflict = true;
							}
						}
						count++;
					}
					if (hasConflict) {
						fst.inConflict = true;
					} else {
						fst.inConflict = false;
					}
				}
				cnt1++;
			}
		}
	}
}