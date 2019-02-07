package structures;

import java.io.Serializable;
import java.util.regex.Matcher;

import utilities.Patterns;

/**
 * A structure representing Altitude blocks that allows for checks
 */
public class Altitude implements Serializable {

	/** for serialization */
	private static final long serialVersionUID = -4620665690270262450L;
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
	 * Get the string representation of the altitude range for this asset
	 * 
	 * @return - altitude range, "XXX-XXX"
	 */
	public String getAltRange() {
		String rng = "";
		if (this.lower.equals("") && this.upper.equals("")) {
			rng = "-";
		} else if (this.lower.equals("")) {
			rng = this.upper + "-" + this.upper;
		} else if (this.upper.equals("")) {
			rng = this.lower + "-" + this.lower;
		} else {
			rng = this.lower + "-" + this.upper;
		}
		return rng;
	}

	/**
	 * Override to format string
	 * 
	 * @return - a formatted "FL XXX to FL XXX" altitude range
	 */
	@Override
	public String toString() {
		return "FL " + getAltRange().replaceAll("-", " to FL ");
	}

	/**
	 * @return true iff altitudes haven't been set
	 */
	public boolean isBlank() {
		return this.lower.equals("") && this.upper.equals("");
	}

	/**
	 * @param other  - the other altitude block to check for altitude overlap
	 * @param altSep - altitude separation allowed between blocks
	 * @return true iff altitude ranges overlap given the asset type
	 */
	public boolean overlaps(Altitude other, int altSep) {
		String curRng = getAltRange();
		String othRng = other.getAltRange();

		int max = 1;
		int min = 0;

		if (!(curRng.equals("-") || othRng.equals("-"))) {

			Matcher rng1Match = Patterns.altBlockPattern.matcher(curRng);
			Matcher rng2Match = Patterns.altBlockPattern.matcher(othRng);
			rng1Match.find();
			rng2Match.find();

			int newAltB = Integer.parseInt(rng1Match.group(1));
			int newAltT = Integer.parseInt(rng1Match.group(2));

			int curAltB = Integer.parseInt(rng2Match.group(1)) - altSep;
			int curAltT = Integer.parseInt(rng2Match.group(2)) + altSep;

			max = newAltB >= curAltB ? newAltB : curAltB;
			min = newAltT <= curAltT ? newAltT : curAltT;
		}
		return (max - min) <= 0;
	}

	/**
	 * Setter
	 * 
	 * @param low - new lower altitude
	 */
	public void setLower(String low) {
		this.lower = low;
	}

	/**
	 * Setter
	 * 
	 * @param up - new upper altitude
	 */
	public void setUpper(String up) {
		this.upper = up;
	}

	/**
	 * getter
	 * 
	 * @return upper altitude of the block
	 */
	public String getUpper() {
		return this.upper;
	}

	/**
	 * getter
	 * 
	 * @return lower altitude of the block
	 */
	public String getLower() {
		return this.lower;
	}

}
