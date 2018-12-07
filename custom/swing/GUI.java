package swing;

import utilities.ADTTableModel;

/**
 * Containers for frames and panels
 */
public class GUI {

	private GUI() {
	}

	/**
	 * Frames will be stored here
	 */
	public static SwingContainer<BaseFrame> FRAMES = new SwingContainer<BaseFrame>();
	/**
	 * Panels will be stored here
	 */
	public static SwingContainer<BasePanel> PANELS = new SwingContainer<BasePanel>();

	/**
	 * Table models will be stored here
	 */
	public static SwingContainer<ADTTableModel<?>> MODELS = new SwingContainer<ADTTableModel<?>>();
}
