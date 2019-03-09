package structures;

import java.awt.Color;
import java.util.Iterator;

import utilities.DebugUtility;

/**
 * An ArrayList of Airspaces for the Airspace Manager
 * 
 * @todo - think of a way to store and load airspaces (probably another file)
 */
public class AirspaceList extends ListOfAsset {

	private static final long serialVersionUID = 2248757932153418225L;

	/**
	 * Expand an airspace into component killboxes
	 * 
	 * @param representation
	 * @return the expansion of this airspace
	 */
	public String expand(String representation) {
		String result = "";
		DebugUtility.trace(AirspaceList.class, "Trying to find killboxes for " + representation);
		Iterator<Asset> it = this.iterator();
		while (it.hasNext()) {
			Airspace as = (Airspace) it.next();
			if (as.getName().equals(representation)) {
				result = as.getAirspace();
			}
		}
		DebugUtility.trace(this.getClass(), "Expanded " + representation + " to '" + result + "'");

		return result.trim();
	}

	/**
	 * Called on creation. For testing, it'll add some sample airspaces.
	 * 
	 * @todo remove test airspaces in production
	 */
	@Override
	public void create() {
		this.add(new Airspace("TEST", "98AL", "000", "200", Color.GREEN));
		this.add(new Airspace("CHARLIE", "98AL", "000", "200", Color.RED));
		this.add(new Airspace("BILLY", "100AL", "000", "120", Color.BLUE));
	}
}
