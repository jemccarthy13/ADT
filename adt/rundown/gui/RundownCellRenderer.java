package rundown.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import rundown.model.RundownTable;
import structures.LockedCells;
import swing.Singleton;

/**
 * A cell renderer to render RundownTable cells
 */
public class RundownCellRenderer extends DefaultTableCellRenderer implements Singleton {

	/**
	 * Serialization information
	 */
	private static final long serialVersionUID = 8107150085372915277L;

	/**
	 * Singleton requirement
	 */
	@Override
	public void create() {
		// empty constructor
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (RundownTable.getInstance().getValueAt(row, 9).equals(Boolean.TRUE) && (column == 3 || column == 4)) {
			c.setBackground(Color.RED);
		} else if (LockedCells.isLocked(row, column)) {
			c.setBackground(Color.gray.darker());
		} else if (column == 2) {
			Color colr = (Color) (RundownTable.getInstance().getValueAt(row, 10));
			if (colr != Color.WHITE) {
				c.setBackground(colr);
			}
		} else if (!isSelected) {
			c.setBackground(RundownTable.getInstance().getBackground());
		}

		if (column == 5) {
			String tooltipText = RundownTable.getInstance().getValueAt(row, column).toString();
			if (tooltipText != null && !tooltipText.equals(""))
				((JComponent) c).setToolTipText(tooltipText);
		}
		return c;
	}
}
