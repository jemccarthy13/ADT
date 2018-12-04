package ato;

import java.awt.Component;
import java.awt.Font;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 * @author jemcc
 *
 */
public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1997111587617475155L;
	/**
	 * 
	 */
	JComponent component = new JTextField();

	/**
	 * @param table
	 * @param value
	 * @param isSelected
	 * @param rowIndex
	 * @param vColIndex
	 * @return Component
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex,
			int vColIndex) {

		((JTextField) this.component).setText((String) value);
		this.component.setFont(new Font("Verdana", 1, 24));

		return this.component;
	}

	@Override
	public Object getCellEditorValue() {
		return ((JTextField) this.component).getText();
	}

}
