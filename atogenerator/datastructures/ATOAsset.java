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

	@Override
	public String toString() {
		String retVal = "AMSNDAT/";
		for (int x = 0; x < 17; x++) {
			switch (x) {
			case 7:
				retVal += "DEPLOC:" + this.get(x);
				break;
			case 8:
				retVal += "ARRLOC:" + this.get(x) + "/";
				break;
			case 14:
				retVal += "1" + this.get(x);
				break;
			case 15:
				retVal += "2" + this.get(x);
				break;
			case 16:
				retVal += "3" + this.get(x);
				break;
			default:
				retVal += this.get(x);
				break;
			}
			retVal += "/";
		}
		return retVal + "/";
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
