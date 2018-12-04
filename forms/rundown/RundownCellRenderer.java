package rundown;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import utilities.Fonts;

/**
 * Change colors and handle cell formatting
 * 
 * @author John McCarthy
 *
 */
public class RundownCellRenderer extends DefaultTableCellRenderer {

	private static RundownCellRenderer instance = new RundownCellRenderer();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static RundownCellRenderer getInstance() {
		return instance;
	}

	private RundownCellRenderer() {
		this.setFont(Fonts.serifBold.deriveFont(Font.BOLD, 24));
	}

	private static final long serialVersionUID = 8107150085372915277L;
	private Color backgroundColor = getBackground();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		RundownTable.getInstance().getModel();
		if (RundownTableModel.getInstance().isLocked(row, column)) {
			c.setBackground(Color.gray.darker());
		} else if (!isSelected) {
			c.setBackground(this.backgroundColor);
		}
		return c;
	}
}