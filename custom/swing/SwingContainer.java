package swing;

import java.util.HashMap;

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
	public T getInstanceOf(Class<?> c) {
		if (!this.containsKey(c.hashCode())) {
			Object y = null;
			try {
				System.out.println("On the fly creating " + c.getName());
				y = c.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			this.put(c.hashCode(), (T) y);
		}
		return this.get(c.hashCode());
	}
}
