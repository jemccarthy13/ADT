package table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import datastructures.ATOAsset;
import datastructures.ATOData;
import utilities.ADTTableModel;

/**
 * ATO table model stores the data for ATO generator table
 */
public class ATOTableModel extends ADTTableModel<ATOAsset> {
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
		// this.addTableModelListener(this);
		this.fullColumnNames.add("MSN#");
		this.fullColumnNames.add("AMCMSN");
		this.fullColumnNames.add("PKGID");
		this.fullColumnNames.add("MSNCC");
		this.fullColumnNames.add("MSN");
		this.fullColumnNames.add("SECMSN");
		this.fullColumnNames.add("ALERT");
		this.fullColumnNames.add("DEPLOC");
		this.fullColumnNames.add("ARRLOC");
		this.fullColumnNames.add("#AC");
		this.fullColumnNames.add("ACTYPE");
		this.fullColumnNames.add("CALLSIGN");
		this.fullColumnNames.add("CONFIG");
		this.fullColumnNames.add("SECCONFIG");
		this.fullColumnNames.add("M1");
		this.fullColumnNames.add("M2");
		this.fullColumnNames.add("M3");
		this.fullColumnNames.add("AR");

		this.items = ATOData.getInstance();
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
		return super.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		super.setValueAt(aValue.toString().toUpperCase(), rowIndex, columnIndex);
		ATOData.checkAddNew();
	}

	@Override
	public void addNew() {
		this.items.add(new ATOAsset());
	}
}
