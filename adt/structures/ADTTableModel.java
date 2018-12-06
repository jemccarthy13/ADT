package structures;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import swing.Singleton;

/**
 * @param <T>
 */
public abstract class ADTTableModel<T> extends AbstractTableModel implements AddNew, Singleton {

	/** Serialization variable */
	private static final long serialVersionUID = -2333045189615857224L;

	/** The column headers for the Table */
	protected ArrayList<String> fullColumnNames = new ArrayList<String>();

	/**
	 * Constructor
	 */
	protected ADTTableModel() {
		this.create();
	}

	/**
	 * The items being stored in this model
	 */
	protected ArrayList<T> items;

	@Override
	public int getRowCount() {
		return this.items.size();
	}

	@Override
	public int getColumnCount() {
		return this.fullColumnNames.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return this.fullColumnNames.get(columnIndex);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < this.items.size()) {
			return ((ArrayList<Object>) this.items.get(rowIndex)).get(columnIndex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		while (this.items.size() <= rowIndex) {
			addNew();
		}
		if (this.items.size() <= rowIndex) {
			addNew();
		}
		((ArrayList<Object>) this.items.get(rowIndex)).set(columnIndex, aValue);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void addNew() {
		System.out.println("Not adding anything to ADTTableModel (parent)");
	}
}
