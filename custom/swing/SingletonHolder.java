package swing;

import java.util.HashMap;

import utilities.DebugUtility;

/**
 * One central location where all Singletons
 */
public class SingletonHolder extends HashMap<Integer, Object> implements Singleton {

	/** */
	private static final long serialVersionUID = -7521308897521447933L;

	private static SingletonHolder instance = new SingletonHolder();

	/**
	 * @param c type of panel to get instance of
	 * @return base panel
	 */
	public static synchronized Object getInstanceOf(Class<?> c) {

		if (!instance.containsKey(c.hashCode())) {
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
			DebugUtility.trace(SingletonHolder.class, "On the fly created " + c.getSimpleName() + obj);

			instance.put(c.hashCode(), y);
		}
		return instance.get(c.hashCode());
	}

	@Override
	public void create() {
		instance = new SingletonHolder();
	}
}
