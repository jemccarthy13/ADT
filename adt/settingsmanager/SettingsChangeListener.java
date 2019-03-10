package settingsmanager;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Listen for changes to the settings. On any sort of change event, call
 * handleChange(), which updates the appropriate Setting
 */
public abstract class SettingsChangeListener implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		handleChange(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		handleChange(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		handleChange(e);
	}

	/**
	 * Handle a change to the document
	 * 
	 * @param e - event to handle
	 */
	protected abstract void handleChange(DocumentEvent e);

}
