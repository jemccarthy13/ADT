package rundown;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import main.RundownFrame;

/**
 * Resize columns when the rundown Compact Mode button is pressed
 * 
 * @author John McCarthy
 *
 */
public class RundownCompactListener implements ActionListener {

	/**
	 * Constructor
	 * 
	 * @param model
	 *            the model of the listener
	 */
	public RundownCompactListener() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		RundownTableModel.getInstance().setCompact(((JCheckBox) (e.getSource())).isSelected());
		System.out.println("Setting compact: " + RundownTableModel.getInstance().isCompactMode());
		RundownFrame.getInstance().handleCompact();
	}

}
