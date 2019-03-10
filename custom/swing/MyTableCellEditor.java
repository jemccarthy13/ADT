package swing;

import java.awt.Component;
import java.awt.Font;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

/**
 */
public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {
	/** Serialization information */
	private static final long serialVersionUID = -1997111587617475155L;

	/** Component to reference the text inside this cell */
	protected JComponent component = new JTextField();

	@Override
	public Object getCellEditorValue() {
		return ((JTextField) this.component).getText();
	}

	/**
	 * Get the component within the cell
	 * 
	 * @param table      - the table associated w/this cell editor
	 * @param value      - value in the table
	 * @param isSelected - whether or not cell is selected
	 * @param rowIndex   - the row of the cell
	 * @param vColIndex  - the column index of the cell
	 * @return Component Component referenced by the table at this location
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex,
			int vColIndex) {
		this.component.setFont(new Font("Verdana", 1, 24));
		return this.component;
	}
}
