package asmanager;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import rundown.gui.RundownFrame;
import rundown.model.RundownTable;
import swing.SingletonHolder;

/**
 * When the active checkbox is checked, "activate" that airspace
 */
public class AirspaceManagerCellListener implements PropertyChangeListener, Runnable {

	private int column;

	@Override
	public void run() {
		this.column = RundownTable.getInstance().getEditingColumn();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (this.column == 0) {
			RundownTable.getInstance().getCellListener().processEditingStopped();
			((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
		}
	}
}