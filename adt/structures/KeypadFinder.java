package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Defines a interface of how to find keypads in a particular reference system
 * 
 * To implement a new reference system, extend this class and primarily define
 * keypadsFromKillbox, which is the base level representation (a singular
 * killbox such as 88AM in CGRS) expanded into keypads
 * 
 * For an example, see how CGRS and GARS KeypadFinders are defined.
 */
public abstract class KeypadFinder {

	private HashMap<String, HashSet<String>> killboxDict = new HashMap<String, HashSet<String>>();

	/**
	 * Find keypads in a given direction from a starting point killbox, direction,
	 * and the keypads in the adjacent killbox
	 * 
	 * i.e. (UPRT, "88AM", {7}) would return 89AM7 since 89AM is the killbox up and
	 * to the right of 88AM.
	 * 
	 * @param direct  - the direction of the adjacent killbox
	 * @param killbox - the starting killbox
	 * @param keypads - an array of keypads in the adjacent killbox
	 * @return - a list of Strings representing keypads
	 */
	public abstract HashSet<String> findKeypads(DIR direct, String killbox, String[] keypads);

	/**
	 * Get the keypads from a given representation
	 * 
	 * @param rep - the representation of approval
	 * @return - the individual keypads of that representation
	 */
	public HashSet<String> getKeypads(String rep) {
		HashSet<String> keypads = new HashSet<String>();
		if (this.killboxDict.containsKey(rep)) {
			keypads = this.killboxDict.get(rep);
		} else {
			if (rep.contains(" ")) {
				String app = rep.substring(rep.lastIndexOf(" "));

				String subRep = rep.substring(0, rep.lastIndexOf(" ")).trim();

				keypads.addAll(this.keypadsFromKillbox(app));
				keypads.addAll(this.getKeypads(subRep));

				this.killboxDict.put(rep, keypads);
			} else {
				keypads.addAll(this.keypadsFromKillbox(rep));
				this.killboxDict.put(rep, keypads);
			}
		}

		return keypads;
	}

	/**
	 * Get keypads from a given singular killbox, i.e. "88AM1+"
	 * 
	 * @param killbox - the representation (row, column, keypad/modifier)
	 * @return - keypads from the killbox
	 */
	public abstract HashSet<String> keypadsFromKillbox(String killbox);

	/**
	 * well darn
	 * 
	 * @param coords
	 * @param centerPoint
	 * @param radius
	 * @param origin
	 * @return - list of keypads that reside within a circle
	 */
	public abstract HashSet<String> getKeypadsInCircle(ArrayList<String> coords, String centerPoint, Double radius,
			String origin);

	/**
	 * damn
	 * 
	 * @param centerPt
	 * @param radius
	 * @return - list of keypads in circle
	 */
	public abstract HashSet<String> getKillboxFromCircle(String centerPt, Double radius);
}
