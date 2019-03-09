package structures;

/**
 * Representation of the different killboxes from present keypad
 */
public class DIR {

	private int rOff, cOff;

	/**
	 * Initialization constructor
	 * 
	 * @param rowOffset    - the row offset in the current direction
	 * @param columnOffset - the column offset in the current direction
	 */
	public DIR(int rowOffset, int columnOffset) {
		this.rOff = rowOffset;
		this.cOff = columnOffset;
	}

	/**
	 * @return column offset in this direction
	 */
	public int getColumnOffset() {
		return this.cOff;
	}

	/**
	 * @return row offset in this direction
	 */
	public int getRowOffset() {
		return this.rOff;
	}

	/** SELF has no change */
	public static final DIR SELF = new DIR(0, 0);
	/** LT is one column to the left */
	public static final DIR LT = new DIR(0, -1);
	/** RT is one column to the right */
	public static final DIR RT = new DIR(0, 1);
	/** UP is one row UP */
	public static final DIR UP = new DIR(1, 0);
	/** DOWN is one row DOWN */
	public static final DIR DOWN = new DIR(-1, 0);
	/** UPLT is one row up and one column to the left */
	public static final DIR UPLT = new DIR(1, -1);
	/** UPRT is one row up and one column to the right */
	public static final DIR UPRT = new DIR(1, 1);
	/** DOWNLT is one row down and one column to the left */
	public static final DIR DOWNLT = new DIR(-1, -1);
	/** DOWNRT is one row down and one column to the right */
	public static final DIR DOWNRT = new DIR(-1, 1);
}