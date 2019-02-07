package structures;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Defines a interface of how to find keypads in a particular reference system
 */
@SuppressWarnings("javadoc")
public interface KeypadFinder {

	public class DIR {

		private int rOff, cOff;

		public DIR(int rowOffset, int columnOffset) {
			this.rOff = rowOffset;
			this.cOff = columnOffset;
		}

		public int getColumnOffset() {
			return this.cOff;
		}

		public int getRowOffset() {
			return this.rOff;
		}
	}

	public static final DIR SELF = new DIR(0, 0);
	public static final DIR LT = new DIR(0, -1);
	public static final DIR RT = new DIR(0, 1);
	public static final DIR UP = new DIR(1, 0);
	public static final DIR DOWN = new DIR(-1, 0);
	public static final DIR UPLT = new DIR(1, -1);
	public static final DIR UPRT = new DIR(1, 1);
	public static final DIR DOWNLT = new DIR(-1, -1);
	public static final DIR DOWNRT = new DIR(-1, 1);

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
	public HashSet<String> findKeypads(DIR direct, String killbox, String[] keypads);

	/**
	 * Get the keypads from a given representation
	 * 
	 * @param representation
	 * @return - the individual keypads of that representation
	 */
	public HashSet<String> getKeypads(String representation);

	/**
	 * well darn
	 * 
	 * @param coords
	 * @param centerPoint
	 * @param radius
	 * @param origin
	 * @return - list of keypads that reside within a circle
	 */
	public ArrayList<String> getKeypadsInCircle(ArrayList<String> coords, String centerPoint, long radius,
			String origin);
}
