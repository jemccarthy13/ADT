package structures;

import java.io.Serializable;

/**
 * A structure representing Altitude blocks. Provides a method for checking 2D
 * overlap (altitudes in the same range)
 */
public class Altitude implements Serializable {

	/** for serialization */
	private static final long serialVersionUID = -4620665690270262450L;

	// it's easier if these are Strings because altitude blocks are represented
	// as flight levels (i.e. 000, 010, 090, 600) and we'd like to display the zeros
	private String lower = "";
	private String upper = "";

	/**
	 * Initialization constructor
	 * 
	 * @param low - lower altitude
	 * @param up  - upper altitude
	 */
	public Altitude(String low, String up) {
		this.lower = low;
		this.upper = up;
	}

	/**
	 * Default constructor
	 */
	public Altitude() {
	}

	/**
	 * @return - a formatted "FL XXX to FL XXX" altitude range
	 */
	@Override
	public String toString() {
		// reuse the lower value if upper is blank
		return "FL " + this.lower + " to FL " + (this.upper.equals("") ? this.lower : this.upper);
	}

	/**
	 * @return true iff altitudes haven't been set
	 */
	public boolean isBlank() {
		return this.lower.equals("") && this.upper.equals("");
	}

	/**
	 * Check for overlap in two altitude ranges given a desired separation
	 * (typically 500 or 1000 feet), in flight level either 005 or 010.
	 * 
	 * @param other  - the other altitude block to check for altitude overlap
	 * @param altSep - altitude separation allowed between blocks
	 * @return true iff altitude ranges overlap given the asset type
	 */
	public boolean overlaps(Altitude other, int altSep) {

		int max = 1;
		int min = 0;

		// we have two valid altitude blocks
		if (!(other.isBlank() || this.isBlank())) {
			int newAltB = this.getLower().equals("") ? Integer.MAX_VALUE : Integer.parseInt(this.getLower());
			int newAltT = this.getUpper().equals("") ? Integer.MIN_VALUE : Integer.parseInt(this.getUpper());

			int othAltB = (other.getLower().equals("") ? Integer.MAX_VALUE : Integer.parseInt(other.getLower()))
					- altSep;
			int othAltT = (other.getUpper().equals("") ? Integer.MIN_VALUE : Integer.parseInt(other.getUpper()))
					+ altSep;

			// the max of the mins - the min of the max's is a check for overlap
			// see https://stackoverflow.com/a/39452639
			max = newAltB >= othAltB ? newAltB : othAltB;
			min = newAltT <= othAltT ? newAltT : othAltT;
		}
		return (max - min) <= 0;
	}

	/**
	 * @param low - new lower altitude to set
	 */
	public void setLower(String low) {
		this.lower = low;
	}

	/**
	 * @param up - new upper altitude to set
	 */
	public void setUpper(String up) {
		this.upper = up;
	}

	/**
	 * @return upper altitude of the block
	 */
	public String getUpper() {
		return this.upper;
	}

	/**
	 * @return lower altitude of the block
	 */
	public String getLower() {
		return this.lower;
	}

}
