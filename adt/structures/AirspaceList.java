package structures;

import java.awt.Color;
import java.util.Iterator;

import asmanager.gui.ASManagerPanel;
import utilities.DebugUtility;

/**
 * An ArrayList of Airspaces for the Airspace Manager
 * 
 * TODO - think of a way to store and load airspaces (probably another file)
 */
public class AirspaceList extends ListOf<Airspace> {

	private static final long serialVersionUID = 2248757932153418225L;

	/**
	 * Check the rundown for an entirely blank row. If one doesn't exist, add one
	 * (to create the feel of a continuous form)
	 */
	public void checkAddNew() {
		boolean blankRow = false;
		for (int x = 0; x < this.size(); x++) {
			if (this.get(x).isBlank()) {
				blankRow = true;
			}
		}
		if (!blankRow) {
			addNew();
			ASManagerPanel.getInstance().repaint();
			// ((Component) SingletonHolder.getInstanceOf(ASManagerPanel.class)).repaint();
		}
	}

	/**
	 * Add a new blank asset to the rundown
	 */
	public void addNew() {
		this.add(new Airspace());
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

	@Override
	public void create() {
		this.add(new Airspace("TEST", "98AL", "000", "200", Color.GREEN));
		this.add(new Airspace("CHARLIE", "98AL", "000", "200", Color.RED));
		this.add(new Airspace("BILLY", "100AL", "000", "120", Color.BLUE));
		this.add(new Airspace());
	}
}
