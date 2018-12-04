
package ato;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import structures.ATOData;
import utilities.Fonts;

/**
 * The panel containing all of the rundown buttons (i.e. a form header of
 * sorts).
 * 
 * @author John McCarthy
 *
 */
public class ATOButtonPanel extends JPanel {

	private static final long serialVersionUID = 6980336047696920906L;

	private static ATOButtonPanel instance = new ATOButtonPanel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOButtonPanel getInstance() {
		return instance;
	}

	/**
	 * Create a new rundown button panel
	 * 
	 * @param table
	 */
	private ATOButtonPanel() {
		setLayout(new GridLayout(1, 4, 20, 20));
		setBorder(new EmptyBorder(20, 20, 20, 20));

		JButton printTest = new JButton("Save");
		printTest.setFont(Fonts.serif);
		printTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Use ObjectWriter to save the ATO as a .proj");
			}
		});

		JButton generateBtn = new JButton("Generate");
		generateBtn.setFont(Fonts.serif);
		generateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ATOData.output();
			}
		});

		add(new JLabel());
		add(printTest);
		add(generateBtn);
		add(new JLabel());
	}
}
