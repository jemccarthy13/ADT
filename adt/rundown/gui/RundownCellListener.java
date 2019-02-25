package rundown.gui;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;

import messages.ADTLockedMessage;
import messages.ADTUpdateMessage;
import rundown.model.RundownTable;
import structures.Airspace;
import structures.AirspaceList;
import structures.Asset;
import structures.LockedCells;
import structures.RundownAssets;
import swing.SingletonHolder;
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
		RundownFrame.getClient().sendMessage(new ADTLockedMessage(this.row, this.column, true));
		LockedCells.setLocked(RundownFrame.getClient().getSessionID(), this.row, this.column, true);
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

		if (this.column == 3 || this.column == 4) {
			if (!newValue.equals("")) {
				if (newValue.length() != 3 || !isInt(newValue)) {
					errMsg = "Altitude needs to be 3 digit FL (000-999)";
				}
			}
		}

		if (this.column == 1) {
			if (!newValue.equals("")) {
				if (newValue.length() < 4 || !isInt(newValue)) {
					errMsg = "Mode 2 must be four digits (####)";
				}
			}
		}

		if (!errMsg.equals("")) {
			DebugUtility.error(this.getClass(), errMsg);
			Output.forceInfoMessage("", errMsg);
			RundownTable.getInstance().setValueAt("", this.row, this.column);
			RundownTable.getInstance().getModel().setValueAt("", this.row, this.column);
			RundownFrame.getClient().sendMessage(new ADTLockedMessage(this.row, this.column, false));
			LockedCells.setLocked(RundownFrame.getClient().getSessionID(), this.row, this.column, false);
			return;
		}
		RundownFrame.getClient().sendMessage(new ADTUpdateMessage(this.row, this.column, newValue));
		RundownFrame.getClient().sendMessage(new ADTLockedMessage(this.row, this.column, false));

		LockedCells.setLocked(RundownFrame.getClient().getSessionID(), this.row, this.column, false);

		// HO-REE SHIT. The time has come.

		boolean hasConflict = false;

		// we've changed airspace or altitude or aircraft type
		if (this.column == 2 || this.column == 3 || this.column == 4 || this.column == 6) {

			// so get the 'current' - changed asset
			Asset first = RundownAssets.getInstance().get(this.row);
			int count = 0;

			// loop through all other assets
			for (Asset other : RundownAssets.getInstance()) {
				// if not itself
				if (this.row != count) {
					// check for new overlaps
					if (first.conflictsWith(other).size() > 0) {
						DebugUtility.trace(this.getClass(),
								"New conflict between " + first.getAirspace() + " and " + other.getAirspace());
						// flag it
						hasConflict = true;
						other.setInConflict(true);
					}
				}
				// next
				count++;
			}
			if (hasConflict) {
				first.setInConflict(true);
			} else {
				first.setInConflict(false);
			}

			int cnt1 = 0;

			// now loop through every asset that has a conflict
			for (Asset fst : RundownAssets.getInstance()) {
				if (fst.isInConflict() == true) {
					hasConflict = false;
					count = 0;
					for (Asset other : RundownAssets.getInstance()) {
						if (cnt1 != count) {
							if (fst.conflictsWith(other).size() > 0) {
								DebugUtility.trace(this.getClass(), "Existing conflict between " + fst.getAirspace()
										+ " and " + other.getAirspace());
								hasConflict = true;
							}
						}
						count++;
					}
					if (hasConflict) {
						fst.setInConflict(true);
					} else {
						fst.setInConflict(false);
					}
				}
				cnt1++;
			}

			checkAirspaceHighlights();
		}

		((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
	}

	/**
	 * Check the rundown for airspace highlighting
	 */
	public void checkAirspaceHighlights() {
		// now loop through every asset that has a conflict
		for (Asset other : RundownAssets.getInstance()) {

			boolean hasConflict = false;
			for (Airspace as : (AirspaceList) (SingletonHolder.getInstanceOf(AirspaceList.class))) {
				if (as.isAddToRundown() && as.conflictsWith(other).size() > 0) {
					hasConflict = true;
					DebugUtility.trace(this.getClass(), as.getColor().toString());
					other.setAirspaceHighlightColor(as.getColor());
				}
			}
			if (!hasConflict) {
				other.setAirspaceHighlightColor(Color.WHITE);
			}
		}
	}
}