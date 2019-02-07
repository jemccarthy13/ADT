package structures;

import java.util.ArrayList;
import java.util.Iterator;

import utilities.DebugUtility;

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

	/**
	 * Expand an airspace into component killboxes
	 * 
	 * @param representation
	 * @return the expansion of this airspace
	 */
	public String expand(String representation) {
		String result = "";
		DebugUtility.trace(AirspaceList.class, "Trying to find killboxes for " + representation);
		Iterator<Airspace> it = this.iterator();
		while (it.hasNext()) {
			Airspace as = it.next();
			if (as.getName().equals(representation)) {
				result = as.getAirspace();
			}
		}
		DebugUtility.trace(this.getClass(), "Expanded " + representation + " to '" + result + "'");

		return result.trim();
	}
}
