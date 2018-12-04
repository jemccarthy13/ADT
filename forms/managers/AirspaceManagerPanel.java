package managers;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import utilities.AltCellListener;
import utilities.Fonts;

/**
 * The buttons / fields at the top of the airspace manager
 * 
 * @author John McCarthy
 */
public class AirspaceManagerPanel extends JPanel {

	private static final long serialVersionUID = -7767311380915462474L;
	private static AirspaceManagerPanel instance = new AirspaceManagerPanel();

	private AirspaceManagerPanel() {

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 75, 75, 75, 200, 75 };
		gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel nameLbl = new JLabel("Name:");
		nameLbl.setFont(Fonts.serif);
		nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_nameLbl = new GridBagConstraints();
		gbc_nameLbl.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameLbl.insets = new Insets(0, 0, 5, 5);
		gbc_nameLbl.gridx = 0;
		gbc_nameLbl.gridy = 0;
		this.add(nameLbl, gbc_nameLbl);

		JTextField nameField = new JTextField();
		nameField.setFont(Fonts.serif);
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.gridwidth = 3;
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.insets = new Insets(0, 0, 5, 5);
		gbc_nameField.gridx = 1;
		gbc_nameField.gridy = 0;
		this.add(nameField, gbc_nameField);

		JLabel gridsLbl = new JLabel("Grids:");
		gridsLbl.setFont(Fonts.serif);
		gridsLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_1 = new GridBagConstraints();
		gbc_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_1.insets = new Insets(0, 0, 5, 5);
		gbc_1.gridx = 0;
		gbc_1.gridy = 1;
		this.add(gridsLbl, gbc_1);

		JTextField gridsField = new JTextField();
		gridsField.setHorizontalAlignment(SwingConstants.LEFT);
		gridsField.setFont(Fonts.serif);
		GridBagConstraints gbc_gridsField = new GridBagConstraints();
		gbc_gridsField.gridwidth = 3;
		gbc_gridsField.fill = GridBagConstraints.HORIZONTAL;
		gbc_gridsField.insets = new Insets(0, 0, 5, 5);
		gbc_gridsField.gridx = 1;
		gbc_gridsField.gridy = 1;
		this.add(gridsField, gbc_gridsField);

		JLabel altLowLbl = new JLabel("Alt (Lower):");
		altLowLbl.setFont(Fonts.serif);
		altLowLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_3 = new GridBagConstraints();
		gbc_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_3.insets = new Insets(0, 0, 5, 5);
		gbc_3.gridx = 0;
		gbc_3.gridy = 2;
		this.add(altLowLbl, gbc_3);

		JTextField lowAltField = new JTextField();
		lowAltField.setFont(Fonts.serif);
		GridBagConstraints gbc_lowAltField = new GridBagConstraints();
		gbc_lowAltField.gridwidth = 1;
		gbc_lowAltField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lowAltField.insets = new Insets(0, 0, 5, 5);
		gbc_lowAltField.gridx = 1;
		gbc_lowAltField.gridy = 2;
		this.add(lowAltField, gbc_lowAltField);

		JLabel altUpLbl = new JLabel("Alt (Upper):");
		altUpLbl.setFont(Fonts.serif);
		altUpLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_4 = new GridBagConstraints();
		gbc_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_4.insets = new Insets(0, 0, 0, 5);
		gbc_4.gridx = 0;
		gbc_4.gridy = 3;
		this.add(altUpLbl, gbc_4);

		JTextField upAltField = new JTextField();
		upAltField.setFont(Fonts.serif);
		GridBagConstraints gbc_upAltField = new GridBagConstraints();
		gbc_upAltField.gridwidth = 1;
		gbc_upAltField.fill = GridBagConstraints.HORIZONTAL;
		gbc_upAltField.insets = new Insets(0, 0, 5, 5);
		gbc_upAltField.gridx = 1;
		gbc_upAltField.gridy = 3;
		this.add(upAltField, gbc_upAltField);

		JButton addNew = new JButton("Add New");
		addNew.setFont(Fonts.serif);
		GridBagConstraints gbc_addNew = new GridBagConstraints();
		gbc_addNew.gridwidth = 1;
		gbc_addNew.fill = GridBagConstraints.HORIZONTAL;
		gbc_addNew.insets = new Insets(0, 50, 5, 5);
		gbc_addNew.gridx = 4;
		gbc_addNew.gridy = 1;
		this.add(addNew, gbc_addNew);

		upAltField.addFocusListener(new AltCellListener(upAltField));
		lowAltField.addFocusListener(new AltCellListener(lowAltField));
	}

	/**
	 * Singleton implementation
	 * 
	 * @return single instance
	 */
	public static Component getInstance() {
		return instance;
	}

}
