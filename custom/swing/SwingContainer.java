package swing;

import java.util.HashMap;

import utilities.DebugUtility;

/**
 * One central location where all JFrames can be retrieved from
 * 
 * @param <T> the type of swing component this map will hold
 */
public class SwingContainer<T> extends HashMap<Integer, T> {

	/** */
	private static final long serialVersionUID = -7521308897521447933L;

	/**
	 * @param c type of panel to get instance of
	 * @return base panel
	 */
	@SuppressWarnings("unchecked")
	public synchronized T getInstanceOf(Class<?> c) {

		if (!this.containsKey(c.hashCode())) {
			Object y = null;
			try {
				y = c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			String obj;
			if (y == null) {
				obj = "null";
			} else {
				obj = y.hashCode() + "";
			}
			DebugUtility.trace(SwingContainer.class, "On the fly created " + c.getName() + obj);

			this.put(c.hashCode(), (T) y);
		}
		return this.get(c.hashCode());
	}
}
