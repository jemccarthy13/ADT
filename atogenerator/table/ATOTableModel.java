package table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import datastructures.ATOAsset;
import datastructures.ATOData;
import structures.ATOAssets;
import structures.ListOfAsset;
import swing.SingletonHolder;
import utilities.ADTTableModel;

/**
 * ATO table model stores the data for ATO generator table
 */
public class ATOTableModel extends ADTTableModel {
	private static final long serialVersionUID = -9017185692217463087L;

	private MouseListener listener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("AR BUTTON PRESSED");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// nothing
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// nothing
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// nothing
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// nothing
		}

	};

	@Override
	public void create() {
		this.columnNames.add("MSN#");
		this.columnNames.add("AMCMSN");
		this.columnNames.add("PKGID");
		this.columnNames.add("MSNCC");
		this.columnNames.add("MSN");
		this.columnNames.add("SECMSN");
		this.columnNames.add("ALERT");
		this.columnNames.add("DEPLOC");
		this.columnNames.add("ARRLOC");
		this.columnNames.add("#AC");
		this.columnNames.add("ACTYPE");
		this.columnNames.add("CALLSIGN");
		this.columnNames.add("CONFIG");
		this.columnNames.add("SECCONFIG");
		this.columnNames.add("M1");
		this.columnNames.add("M2");
		this.columnNames.add("M3");
		this.columnNames.add("AR");
		this.items = (ListOfAsset) (SingletonHolder.getInstanceOf(ATOAssets.class));
	}

	@Override
	public Class<?> getColumnClass(int col) {
		if (col == 17) {
			return JButton.class;
		}
		return super.getColumnClass(col);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 17) {
			JButton button = new JButton("PRESS");
			button.addMouseListener(this.listener);
			return button;
		}
		return ((ATOAsset) ((ATOData) SingletonHolder.getInstanceOf(ATOData.class)).get(rowIndex)).getItems()
				.get(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ATOAsset chosen = (ATOAsset) ((ATOData) SingletonHolder.getInstanceOf(ATOData.class)).get(rowIndex);

		chosen.getItems().set(columnIndex, aValue);
		ATOData.checkAddNew();
	}

	@Override
	public void addNew() {
		ATOData.addNew();
	}

	@Override
	public int getRowCount() {
		return ((ATOData) SingletonHolder.getInstanceOf(ATOData.class)).size();
	}
}
