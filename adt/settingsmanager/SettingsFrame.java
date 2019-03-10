package settingsmanager;

import javax.swing.JPanel;

import swing.BaseFrame;
import swing.SingletonHolder;

/**
 * Allow for manipulation of the grid settings
 */
public class SettingsFrame extends BaseFrame {

	/** Serialization information */
	private static final long serialVersionUID = -1725185591885058796L;

	@Override
	public void create() {
		this.setSize(600, 400);
		this.setTitle("Settings");
		this.content = ((JPanel) SingletonHolder.getInstanceOf(SettingsEditPanel.class));
		super.create();
	}

}
