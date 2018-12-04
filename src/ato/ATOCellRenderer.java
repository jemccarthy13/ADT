package ato;

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
public class ATOCellRenderer extends DefaultTableCellRenderer {

	private static ATOCellRenderer instance = new ATOCellRenderer();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOCellRenderer getInstance() {
		return instance;
	}

	private ATOCellRenderer() {
		this.setFont(Fonts.serifBold.deriveFont(Font.BOLD, 24));
	}

	private static final long serialVersionUID = 8107150085372915277L;
	private Color backgroundColor = getBackground();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (!isSelected) {
			c.setBackground(this.backgroundColor);
		}
		return c;
	}
}