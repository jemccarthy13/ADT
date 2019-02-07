package structures;

/**
 * Representation of the different killboxes from present keypad
 */
@SuppressWarnings("javadoc")
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

	public static final DIR SELF = new DIR(0, 0);
	public static final DIR LT = new DIR(0, -1);
	public static final DIR RT = new DIR(0, 1);
	public static final DIR UP = new DIR(1, 0);
	public static final DIR DOWN = new DIR(-1, 0);
	public static final DIR UPLT = new DIR(1, -1);
	public static final DIR UPRT = new DIR(1, 1);
	public static final DIR DOWNLT = new DIR(-1, -1);
	public static final DIR DOWNRT = new DIR(-1, 1);
}