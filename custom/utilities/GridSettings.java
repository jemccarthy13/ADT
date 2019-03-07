package utilities;

import structures.KeypadFinder;
import swing.Singleton;

/**
 * Whether we use CGRS or GARS. If CGRS, hold the origin, and starting row and
 * starting column.
 */
public class GridSettings implements Singleton {

	private String startRow = "1";
	private String startCol = "A";

	private String origin = "1000S02500E";
	private KeypadFinder finder = new CGRSKeypadFinder();

	@Override
	public void create() {
		this.setKeypadFinder(new CGRSKeypadFinder());
	}

	/**
	 * @return start row of the CGRS grid
	 */
	public String getStartRow() {
		return this.startRow;
	}

	/**
	 * @param startRow new start row for CGRS grid
	 */
	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}

	/**
	 * @return the start column for CGRS grid
	 */
	public String getStartCol() {
		return this.startCol;
	}

	/**
	 * @param startCol the new start column for CGRS grid
	 */
	public void setStartCol(String startCol) {
		this.startCol = startCol;
	}

	/**
	 * @return the KeypadFinder we are using
	 */
	public KeypadFinder getKeypadFinder() {
		return this.finder;
	}

	/**
	 * @param finder the new KeypadFinder to use
	 */
	public void setKeypadFinder(KeypadFinder finder) {
		this.finder = finder;
	}

	/**
	 * @return the grid origin
	 */
	public String getOrigin() {
		return this.origin;
	}

	/**
	 * Set a new origin
	 * 
	 * @param text the new origin to use
	 */
	public void setOrigin(String text) {
		this.origin = text;
	}
}
