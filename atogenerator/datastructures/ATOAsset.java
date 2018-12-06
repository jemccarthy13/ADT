package datastructures;

import java.io.Serializable;

/**
 * Representation of an asset listed in the ATO
 */
public class ATOAsset implements Serializable {

	/**
	 * attributes for the ATO data
	 */
	String[] attributes = new String[17];

	private static final long serialVersionUID = 8634221915912471387L;

	/**
	 * Create a blank asset
	 */
	public ATOAsset() {
		for (int x = 0; x < this.attributes.length; x++) {
			this.attributes[x] = "-";
		}
	}

	/**
	 * String together the information for the asset, to be written to the ATO
	 */
	@Override
	public String toString() {
		String retVal = "AMSNDAT/";
		for (int x = 0; x < this.attributes.length; x++) {
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
			retVal += this.attributes[x] + "/";
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
		return this.attributes[x];
	}

	/**
	 * @return whether or not this ATO line is completely blank
	 */
	public boolean isBlank() {
		boolean retVal = true;
		for (int x = 0; x < this.attributes.length; x++) {
			if (this.attributes[x] != null && !this.attributes[x].equals("-")) {
				retVal = false;
			}
		}
		return retVal;
	}

	/**
	 * @param columnIndex
	 * @param val
	 */
	public void set(int columnIndex, String val) {
		this.attributes[columnIndex] = val;
	}

}
