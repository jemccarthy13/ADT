package atoLookup;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import swing.Singleton;

/**
 * A cell renderer to render RundownTable cells
 */
public class ATOLookupCellRenderer extends DefaultTableCellRenderer implements Singleton {

	/** Serialization information */
	private static final long serialVersionUID = 758930412805083375L;

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

		// get the component to render from the table
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		// for the status column, also add a tooltip text (mouse hover) so we can see
		// all of the status
		if (column == 8) {
			String tooltipText = ATOLookupTable.getInstance().getValueAt(row, column).toString();
			if (tooltipText != null && !tooltipText.equals(""))
				((JComponent) c).setToolTipText(tooltipText);
		}
		return c;
	}
}
