package panels;

import java.util.HashMap;

/**
 * One central location where all JPanels can be retrieved from
 */
public class Panels extends HashMap<Integer, BasePanel> {

	/** */
	private static final long serialVersionUID = -7521308897521447933L;

	/**
	 * Singleton instance
	 */
	private static Panels instance = new Panels();

	/**
	 * Singleton instance
	 * 
	 * @return the painter instance
	 */
	public static Panels getInstance() {
		return instance;
	}

	/**
	 * @param c type of panel to get instance of
	 * @return base panel
	 */
	public static BasePanel getInstanceOf(Class<?> c) {
		if (!instance.containsKey(c.hashCode())) {
			Object y = null;
			try {
				System.out.println("On the fly creating " + c.getName());
				y = c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			instance.put(c.hashCode(), (BasePanel) y);
		}
		return instance.get(c.hashCode());
	}

	/**
	 * Add a scene to the painters
	 * 
	 * @param panel - the panel to register
	 */
	public static void register(BasePanel panel) {
		System.out.println("Registering: " + panel.getClass().getName());
		instance.put(Integer.valueOf(panel.getClass().hashCode()), panel);
	}
}
