package datastructures;

import java.util.ArrayList;

import swing.ActionButton;

/**
 * Representation of an asset listed in the ATO
 */
public class ATOAsset extends ArrayList<Object> {

	private static final long serialVersionUID = 8634221915912471387L;

	/**
	 * Create a blank asset
	 */
	public ATOAsset() {
		for (int x = 0; x < 17; x++) {
			this.add("-");
		}
		this.add(new ActionButton("Add", null));
	}

	/**
	 * String together the information for the asset, to be written to the ATO
	 */
	@Override
	public String toString() {
		String retVal = "AMSNDAT/";
		for (int x = 0; x < this.size(); x++) {
			if (x == 7) {
				retVal += "DEPLOC:";
			} else if (x == 8) {
				retVal += "ARRLOC:";
			}
			if (x == 14) {
				retVal += "1";
			}
			if (x == 15) {
				retVal += "2";
			}
			if (x == 16) {
				retVal += "3";
			}
			retVal += this.get(x) + "/";
			if (x == 8) {
				retVal += "/\nMSNACFT/";
			}

		}
		retVal += "/";
		return retVal;
	}

	/**
	 * @param x
	 * @return the attribute desired
	 */
	public String getVal(int x) {
		return (String) this.get(x);
	}

	/**
	 * @return whether or not this ATO line is completely blank
	 */
	public boolean isBlank() {
		boolean retVal = true;
		for (int x = 0; x < this.size() - 1; x++) {
			if (this.get(x) != null && !this.get(x).equals("-")) {
				retVal = false;
			}
		}
		return retVal;
	}
}
