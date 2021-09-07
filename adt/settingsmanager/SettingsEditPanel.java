package settingsmanager;

import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.plaf.FontUIResource;

import structures.KeypadFinderCGRS;
import structures.KeypadFinderGARS;
import swing.ADTLabel;
import swing.BasePanel;
import swing.GBC;
import swing.SingletonHolder;
import utilities.DebugUtility;
import utilities.Fonts;
import utilities.GridSettings;

/**
 * Private text fields and selectors to manipulate the grid settings
 */
public class SettingsEditPanel extends BasePanel {

	/** Serialization information */
	private static final long serialVersionUID = 8029432007486333653L;

	/**
	 * Combobox whether the grid is CGRS or GARS
	 */
	JComboBox<String> selector;

	/** Field for setting the grid Origin */
	JTextField originField;

	/** Field for setting the starting row */
	JTextField startRowField;

	/** Field to set the start column */
	JTextField startColField;

	@Override
	public void create() {
		final GridSettings settings = ((GridSettings) SingletonHolder.getInstanceOf(GridSettings.class));
		Fonts.setUIFont(new FontUIResource(Fonts.serif));

		// add applicable boxes
		this.setLayout(new GridBagLayout());
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 75, 300 };
		gridBagLayout.rowHeights = new int[] { 30, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0 };
		setLayout(gridBagLayout);

		ADTLabel selectorLbl = new ADTLabel("Defined by:");
		selectorLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_selectorLbl = new GBC(0, 0);
		this.add(selectorLbl, gbc_selectorLbl);

		this.selector = new JComboBox<String>();
		this.selector.addItem("CGRS");
		this.selector.addItem("GARS");
		GBC gbc_selector = new GBC(1, 0);
		this.add(this.selector, gbc_selector);

		this.selector.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent event) {
				Object item = event.getItem();
				boolean isCGRS = item.toString().equals("CGRS") ? true : false;
				if (isCGRS) {
					((GridSettings) SingletonHolder.getInstanceOf(GridSettings.class))
							.setKeypadFinder(new KeypadFinderCGRS());
				} else {
					((GridSettings) SingletonHolder.getInstanceOf(GridSettings.class))
							.setKeypadFinder(new KeypadFinderGARS());
				}
			}
		});

		ADTLabel originLbl = new ADTLabel("Origin: ");
		originLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_1 = new GBC(0, 2);
		this.add(originLbl, gbc_1);

		this.originField = new JTextField();
		this.originField.setText(settings.getOrigin());
		this.originField.setHorizontalAlignment(SwingConstants.LEFT);
		GBC gbc_originField = new GBC(3, 1, 2);
		this.add(this.originField, gbc_originField);

		ADTLabel altLowLbl = new ADTLabel("Start Row:");
		altLowLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_3 = new GBC(0, 4);
		this.add(altLowLbl, gbc_3);

		this.startRowField = new JTextField();
		this.startRowField.setText(settings.getStartRow());
		GBC gbc_rowField = new GBC(1, 1, 4);
		this.add(this.startRowField, gbc_rowField);

		ADTLabel startColLbl = new ADTLabel("Start Column:");
		startColLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		GBC gbc_4 = new GBC(0, 5);
		this.add(startColLbl, gbc_4);

		this.startColField = new JTextField();
		this.startColField.setText(settings.getStartCol());
		GBC gbc_colField = new GBC(1, 1, 5);
		this.add(this.startColField, gbc_colField);

		this.startColField.getDocument().addDocumentListener(new SettingsChangeListener() {

			@Override
			protected void handleChange(DocumentEvent e) {
				settings.setStartCol(SettingsEditPanel.this.startColField.getText());
				DebugUtility.error(SettingsEditPanel.class, "Start column changed to " + settings.getStartCol());
			}
		});

		this.startRowField.getDocument().addDocumentListener(new SettingsChangeListener() {
			@Override
			protected void handleChange(DocumentEvent e) {
				settings.setStartRow(SettingsEditPanel.this.startRowField.getText());
				DebugUtility.error(SettingsEditPanel.class, "Start row changed to " + settings.getStartRow());
			}
		});
		this.originField.getDocument().addDocumentListener(new SettingsChangeListener() {
			@Override
			protected void handleChange(DocumentEvent e) {
				settings.setOrigin(SettingsEditPanel.this.originField.getText());
				DebugUtility.error(SettingsEditPanel.class, "Origin changed to " + settings.getOrigin());
			}
		});
	}

}
