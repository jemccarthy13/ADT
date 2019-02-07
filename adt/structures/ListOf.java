package structures;

import java.util.ArrayList;

import swing.Singleton;

/**
 * Basis for panels
 * 
 * @param <T> class which this is a list of
 */
public abstract class ListOf<T> extends ArrayList<T> implements Singleton {

	/**
	 * Constructor
	 */
	protected ListOf() {
		this.create();
	}

	/**
	 * Serialization information
	 */
	private static final long serialVersionUID = -4628040775101357858L;

	@Override
	public abstract void create();
}
