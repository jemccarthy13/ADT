package structures;

import java.util.ArrayList;

/**
 * An ArrayList of Airspaces for the Airspace Manager
 * 
 * TODO - think of a way to store and load airspaces (probably another file)
 */
public class AirspaceList extends ArrayList<Airspace> {

	private static final long serialVersionUID = 2248757932153418225L;
	private static AirspaceList instance = new AirspaceList();

	private AirspaceList() {
		this.add(new Airspace("TEST", "98AL", "000", "120"));
	}

	/**
	 * Singleton implementation.
	 * 
	 * @return single instance
	 */
	public static AirspaceList getInstance() {
		return instance;
	}
}
