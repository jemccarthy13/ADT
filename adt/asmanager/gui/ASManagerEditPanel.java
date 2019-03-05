package asmanager.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.HashSet;

import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import rundown.model.RundownTable;
import structures.Airspace;
import structures.AirspaceList;
import swing.ADTLabel;
import swing.ActionButton;
import swing.BasePanel;
import swing.GBC;
import swing.MouseClickListener;
import swing.SingletonHolder;
import utilities.AltCellListener;
import utilities.GridSettings;
import utilities.Output;

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
			if (e.getSource().equals(ASManagerEditPanel.this.addBtn)) {
				if (ASManagerEditPanel.this.selector.getSelectedItem().toString().equals("Grids")) {
					ASManagerEditPanel.this.addAirspace();
				} else {
					Output.forceInfoMessage("Unimplemented", "Need to implement MGRS->Keypad conversion");
					HashSet<String> result = ((GridSettings) SingletonHolder.getInstanceOf(GridSettings.class))
							.getKeypadFinder().getKillboxFromCircle(ASManagerEditPanel.this.gridsField.getText(),
									Double.parseDouble(ASManagerEditPanel.this.radiusField.getText()));
					System.err.println(result.toString());
				}
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

	/**
	 * Field to store an airspace radius
	 */
	JTextField radiusField;

	/**
	 * Access to the radius lbl (for set visible)
	 */
	ADTLabel radiusLbl;

	/**
	 * Access to change the grid label text
	 */
	ADTLabel gridsLbl;

	/**
	 * Combobox whether this airspace is defined in terms of Grid reference or
	 * centerpoint/radius
	 */
	JComboBox selector;

	/**
	 * Set the edit panel to show/hide centerpoint/radius fields
	 * 
	 * @param show true to show centerpoint/radius, false to show grids
	 */
	public void setEdit(boolean show) {
		ASManagerEditPanel.this.radiusField.setVisible(show);
		ASManagerEditPanel.this.radiusLbl.setVisible(show);
		ASManagerEditPanel.this.gridsLbl.setText(show ? "Center Point:" : "Grids:");
	}

	/**
	 * Add the new airspace to the AirspaceList
	 */
	public void addAirspace() {
		Airspace a = new Airspace();
		a.setName(this.nameField.getText());
		a.setAirspace(this.gridsField.getText());
		a.setAltBlock(this.lowAltField.getText(), this.upAltField.getText());
		a.setColor(this.colorField.getBackground());
		a.setAddToRundown(true);

		AirspaceList list = (AirspaceList) SingletonHolder.getInstanceOf(AirspaceList.class);
		list.add(a);

		ASManagerPanel.getInstance().repaint();

		RundownTable.getInstance().getCellListener().checkAirspaceHighlights();
		((ManagerFrame) (SingletonHolder.getInstanceOf(ManagerFrame.class))).dispose();
	}

	@Override
	public void create() {
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 75, 75, 75, 200, 75 };
		gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		ADTLabel selectorLbl = new ADTLabel("Defined by:");
		selectorLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_selectorLbl = new GBC(0, 0);
		this.add(selectorLbl, gbc_selectorLbl);

		this.selector = new JComboBox();
		this.selector.addItem("Grids");
		this.selector.addItem("Center Point/Radius");
		GBC gbc_selector = new GBC(1, 0);
		this.add(this.selector, gbc_selector);

		this.selector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					Object item = event.getItem();
					boolean show = item.toString().equals("Grids") ? false : true;
					ASManagerEditPanel.this.setEdit(show);
				}
			}
		});

		ADTLabel nameLbl = new ADTLabel("Name:");
		nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_nameLbl = new GBC(0, 1);
		this.add(nameLbl, gbc_nameLbl);

		this.nameField = new JTextField();
		GBC gbc_nameField = new GBC(2, 1, 1);
		this.add(this.nameField, gbc_nameField);

		this.gridsLbl = new ADTLabel("Grids:");
		this.gridsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_1 = new GBC(0, 2);
		this.add(this.gridsLbl, gbc_1);

		this.gridsField = new JTextField();
		this.gridsField.setHorizontalAlignment(SwingConstants.LEFT);
		GBC gbc_gridsField = new GBC(3, 1, 2);
		this.add(this.gridsField, gbc_gridsField);

		this.radiusLbl = new ADTLabel("Radius:");
		this.radiusLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_radiusLbl = new GBC(0, 3);
		this.add(this.radiusLbl, gbc_radiusLbl);

		this.radiusField = new JTextField();
		GBC gbc_radius = new GBC(1, 1, 3);
		this.add(this.radiusField, gbc_radius);

		ADTLabel altLowLbl = new ADTLabel("Alt (Lower):");
		altLowLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_3 = new GBC(0, 4);
		this.add(altLowLbl, gbc_3);

		this.lowAltField = new JTextField();
		this.lowAltField.addFocusListener(new AltCellListener(this.lowAltField));
		GBC gbc_lowAltField = new GBC(1, 1, 4);
		this.add(this.lowAltField, gbc_lowAltField);

		ADTLabel altUpLbl = new ADTLabel("Alt (Upper):");
		altUpLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_4 = new GBC(0, 5);
		this.add(altUpLbl, gbc_4);

		this.upAltField = new JTextField();
		this.upAltField.addFocusListener(new AltCellListener(this.upAltField));
		GBC gbc_upAltField = new GBC(1, 1, 5);
		this.add(this.upAltField, gbc_upAltField);

		this.addBtn = new ActionButton("Add New", new AddListener());
		GBC gbc_addNew = new GBC(1, 4, 1);
		this.add(this.addBtn, gbc_addNew);

		ADTLabel clrLbl = new ADTLabel("Color:");
		altUpLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_5 = new GBC(3, 5);
		this.add(clrLbl, gbc_5);

		this.colorField = new JTextField();
		this.colorField.setBackground(Color.WHITE);
		this.colorField.setEditable(false);
		this.colorField.addMouseListener(new MouseClickListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color c = JColorChooser.showDialog(ASManagerEditPanel.this.colorField, "Color Chooser",
						ASManagerEditPanel.this.colorField.getBackground());
				ASManagerEditPanel.this.colorField.setBackground(c);
			}
		});
		GridBagConstraints gbc_color = new GBC(1, 4, 5);
		this.add(this.colorField, gbc_color);

		this.setEdit(false);
	}
}
