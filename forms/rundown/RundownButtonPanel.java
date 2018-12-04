
package rundown;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import atoLookup.ATOLookupForm;
import main.RundownFrame;
import managers.ManagerFrame;
import test.Tester;
import utilities.Fonts;

/**
 * The panel containing all of the rundown buttons (i.e. a form header of
 * sorts).
 * 
 * @author John McCarthy
 *
 */
public class RundownButtonPanel extends JPanel {

	private static final long serialVersionUID = 6980336047696920906L;

	private static RundownButtonPanel instance = new RundownButtonPanel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static RundownButtonPanel getInstance() {
		return instance;
	}

	/**
	 * Create a new rundown button panel
	 * 
	 * @param table
	 */
	private RundownButtonPanel() {
		setLayout(new GridLayout(3, 3, 20, 20));
		setBorder(new EmptyBorder(20, 20, 20, 20));

		JButton atLookupBtn = new JButton("ATO Lookup");
		atLookupBtn.setFont(Fonts.serif);
		atLookupBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ATOLookupForm atoLookup = ATOLookupForm.getInstance();
				atoLookup.setLocationRelativeTo(RundownFrame.getInstance());
				atoLookup.setVisible(true);
			}
		});

		JButton asManagerBtn = new JButton("AS Manager");
		asManagerBtn.setFont(Fonts.serif);
		asManagerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ManagerFrame.openManager();
			}
		});

		JButton mildeconBtn = new JButton("MILDECON");
		mildeconBtn.setFont(Fonts.serif);

		JButton stacksBtn = new JButton("Stacks");
		stacksBtn.setFont(Fonts.serif);
		JButton metricsBtn = new JButton("Metrics");
		metricsBtn.setFont(Fonts.serif);

		JButton lowdownManagerBtn = new JButton("Lowdown Manager");
		lowdownManagerBtn.setFont(Fonts.serif);

		JButton getLowdownBtn = new JButton("Get Lowdown");
		getLowdownBtn.setFont(Fonts.serif);

		JCheckBox compactCheck = new JCheckBox("Compact Mode");
		compactCheck.setFont(Fonts.serif);
		compactCheck.addActionListener(new RundownCompactListener());
		compactCheck.setSelected(true);

		JButton test = new JButton("test");
		test.setFont(Fonts.serif);
		test.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Tester.main(null);
			}
		});

		add(atLookupBtn);
		add(stacksBtn);
		add(lowdownManagerBtn);
		add(asManagerBtn);
		add(metricsBtn);
		add(getLowdownBtn);
		add(mildeconBtn);
		add(compactCheck);
		add(test);
	}
}
