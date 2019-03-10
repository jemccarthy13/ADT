package asmanager.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import rundown.gui.RundownCellListener;
import rundown.gui.RundownFrame;
import rundown.model.RundownTable;
import swing.SingletonHolder;

/**
 * When the active checkbox is checked, "activate" that airspace
 */
public class AirspaceTableCellListener implements PropertyChangeListener, Runnable {

	private int column;

	@Override
	public void run() {
		this.column = RundownTable.getInstance().getEditingColumn();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (this.column == 0) {
			RundownCellListener.checkAirspaceHighlights();
			((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
		}
	}
}