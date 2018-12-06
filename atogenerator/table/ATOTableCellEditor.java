package table;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import swing.MyTableCellEditor;

/**
 * A custom cell editor for the ATOMaker.
 */
public class ATOTableCellEditor extends MyTableCellEditor {

	/**
	 * auto generated for serialization
	 */
	private static final long serialVersionUID = -1129745665992406615L;

	/**
	 * Default constructor
	 */
	public ATOTableCellEditor() {
		super();
		this.addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				if (ATOTable.getInstance().getValueAt(ATOTable.getPrevRow(), ATOTable.getPrevCol()).equals("")) {
					ATOTable.getInstance().setValueAt("-", ATOTable.getPrevRow(), ATOTable.getPrevCol());
				}
			}

			@Override
			public void editingCanceled(ChangeEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

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
		super.getTableCellEditorComponent(table, value, isSelected, rowIndex, vColIndex);

		if (ATOTable.getPrevCol() != -1 && ATOTable.getPrevCol() != -1
				&& ATOTable.getInstance().getValueAt(rowIndex, vColIndex).equals("-")) {
			((JTextField) this.component).setText("");
			ATOTable.getInstance().setValueAt("", rowIndex, vColIndex);
			ATOTable.setPrevCol(vColIndex);
			ATOTable.setPrevRow(rowIndex);
		}

		return this.component;
	}
}
