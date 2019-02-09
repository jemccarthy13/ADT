package asmanager.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import structures.Airspace;
import structures.AirspaceList;
import swing.ADTLabel;
import swing.ActionButton;
import swing.BasePanel;
import swing.SingletonHolder;
import utilities.AltCellListener;
import utilities.DebugUtility;

/**
 * The buttons / fields at the top of the airspace manager
 */
public class ASManagerEditPanel extends BasePanel {

	private static final long serialVersionUID = -7767311380915462474L;

	/**
	 * Action button to add a new airspace
	 */
	ActionButton addBtn;

	/**
	 * the name of the new airspace
	 */
	JTextField nameField;

	private class AddListener implements ActionListener {

		public AddListener() {
			// default empty constructor
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.err.println("Action performed");
			if (e.getSource().equals(ASManagerEditPanel.this.addBtn)) {
				// do the add
				Airspace a = new Airspace();
				a.setName(ASManagerEditPanel.this.nameField.getText());
				a.setAirspace(ASManagerEditPanel.this.gridsField.getText());
				String lower = ASManagerEditPanel.this.lowAltField.getText();
				String upper = ASManagerEditPanel.this.upAltField.getText();
				a.setAltBlock(lower, upper);
				a.setColor(ASManagerEditPanel.this.colorField.getBackground());
				AirspaceList list = (AirspaceList) SingletonHolder.getInstanceOf(AirspaceList.class);
				list.add(a);
				list.checkAddNew();
				System.out.println("adding: " + a.getName());
				DebugUtility.error(ASManagerEditPanel.class, "Added airspace: " + a.getName());
				ASManagerPanel.getInstance().repaint();
			}
		}
	}

	/**
	 * Grids of the added airspace
	 */
	JTextField gridsField;

	/**
	 * Lower altitude block of the airspace
	 */
	JTextField lowAltField;

	/**
	 * Upper altitude block of the airspace
	 */
	JTextField upAltField;

	/**
	 * Field to store color of added airspace
	 */
	JTextField colorField;

	@Override
	public void create() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 75, 75, 75, 200, 75 };
		gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		ADTLabel nameLbl = new ADTLabel("Name:");
		nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_nameLbl = new GridBagConstraints();
		gbc_nameLbl.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nameLbl.gridx = 0;
		gbc_nameLbl.gridy = 0;
		this.add(nameLbl, gbc_nameLbl);

		this.nameField = new JTextField();
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.gridwidth = 3;
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.insets = new Insets(0, 0, 5, 5);
		gbc_nameField.gridx = 1;
		gbc_nameField.gridy = 0;
		this.add(this.nameField, gbc_nameField);

		ADTLabel gridsLbl = new ADTLabel("Grids:");
		gridsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_1.insets = new Insets(0, 0, 5, 5);
		gbc_1.gridx = 0;
		gbc_1.gridy = 1;
		this.add(gridsLbl, gbc_1);

		this.gridsField = new JTextField();
		this.gridsField.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_gridsField = new GridBagConstraints();
		gbc_gridsField.gridwidth = 3;
		gbc_gridsField.fill = GridBagConstraints.HORIZONTAL;
		gbc_gridsField.insets = new Insets(0, 0, 5, 5);
		gbc_gridsField.gridx = 1;
		gbc_gridsField.gridy = 1;
		this.add(this.gridsField, gbc_gridsField);

		ADTLabel altLowLbl = new ADTLabel("Alt (Lower):");
		altLowLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_3 = new GridBagConstraints();
		gbc_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_3.insets = new Insets(0, 0, 5, 5);
		gbc_3.gridx = 0;
		gbc_3.gridy = 2;
		this.add(altLowLbl, gbc_3);

		this.lowAltField = new JTextField();
		this.lowAltField.addFocusListener(new AltCellListener(this.lowAltField));
		GridBagConstraints gbc_lowAltField = new GridBagConstraints();
		gbc_lowAltField.gridwidth = 1;
		gbc_lowAltField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lowAltField.insets = new Insets(0, 0, 5, 5);
		gbc_lowAltField.gridx = 1;
		gbc_lowAltField.gridy = 2;
		this.add(this.lowAltField, gbc_lowAltField);

		ADTLabel altUpLbl = new ADTLabel("Alt (Upper):");
		altUpLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_4 = new GridBagConstraints();
		gbc_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_4.insets = new Insets(0, 0, 0, 5);
		gbc_4.gridx = 0;
		gbc_4.gridy = 3;
		this.add(altUpLbl, gbc_4);

		this.upAltField = new JTextField();
		this.upAltField.addFocusListener(new AltCellListener(this.upAltField));
		GridBagConstraints gbc_upAltField = new GridBagConstraints();
		gbc_upAltField.gridwidth = 1;
		gbc_upAltField.fill = GridBagConstraints.HORIZONTAL;
		gbc_upAltField.insets = new Insets(0, 0, 5, 5);
		gbc_upAltField.gridx = 1;
		gbc_upAltField.gridy = 3;
		this.add(this.upAltField, gbc_upAltField);

		this.addBtn = new ActionButton("Add New", new AddListener());
		GridBagConstraints gbc_addNew = new GridBagConstraints();
		gbc_addNew.gridwidth = 1;
		gbc_addNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_addNew.insets = new Insets(0, 50, 5, 5);
		gbc_addNew.gridx = 4;
		gbc_addNew.gridy = 1;
		this.add(this.addBtn, gbc_addNew);

		ADTLabel clrLbl = new ADTLabel("Color:");
		altUpLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_5 = new GridBagConstraints();
		gbc_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_5.insets = new Insets(0, 0, 0, 5);
		gbc_5.gridx = 3;
		gbc_5.gridy = 3;
		this.add(clrLbl, gbc_5);

		this.colorField = new JTextField();
		this.colorField.setBackground(Color.WHITE);
		this.colorField.setEditable(false);
		this.colorField.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Color c = JColorChooser.showDialog(ASManagerEditPanel.this.colorField, "Color Chooser",
						ASManagerEditPanel.this.colorField.getBackground());
				ASManagerEditPanel.this.colorField.setBackground(c);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// do nothing
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// do nothing
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// do nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// do nothing
			}

		});
		GridBagConstraints gbc_color = new GridBagConstraints();
		gbc_color.gridwidth = 1;
		gbc_color.fill = GridBagConstraints.HORIZONTAL;
		gbc_color.insets = new Insets(0, 50, 5, 5);
		gbc_color.gridx = 3;
		gbc_color.gridy = 3;
		this.add(this.colorField, gbc_color);

	}
}
