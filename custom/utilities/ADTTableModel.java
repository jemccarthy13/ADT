package utilities;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import swing.Singleton;

/**
 * @param <T>
 */
public abstract class ADTTableModel<T> extends AbstractTableModel implements Singleton {

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

	/**
	 * Remove all items from the table
	 */
	public void clear() {
		this.items.clear();
	}

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

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
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
			this.addNew();
		}
		((ArrayList<Object>) this.items.get(rowIndex)).set(columnIndex, aValue);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	/**
	 * Add a new object of type T to the ADTTableModel
	 */
	public abstract void addNew();

	/**
	 * @param instance
	 */
	@SuppressWarnings("unchecked")
	public void setItems(ArrayList<?> instance) {
		this.items = (ArrayList<T>) instance;
	}
}
