package rundown.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;

import main.RundownFrame;
import rundown.model.RundownTable;
import rundown.model.RundownTableModel;

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
	 * @param action
	 *            the Action to invoke when cell data is changed
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
		RundownTableModel.getInstance().setLocked(RundownFrame.getClient().getSessionID(), this.row, this.column);
	}

	/*
	 * Update the Cell history when necessary
	 */
	private void processEditingStopped() {
		RundownFrame.getClient().sendMessage("unlocked," + this.row + "," + this.column);
		RundownTableModel.getInstance().setUnlocked(RundownFrame.getClient().getSessionID(), this.row, this.column);
	}
}