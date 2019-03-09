package datastructures;

import java.util.ArrayList;

import structures.Asset;
import swing.ActionButton;

/**
 * Representation of an asset listed in the ATO
 */
public class ATOAsset extends Asset {

	private static final long serialVersionUID = 8634221915912471387L;

	private ArrayList<Object> items = new ArrayList<Object>();

	/**
	 * Create a blank asset
	 */
	public ATOAsset() {
		for (int x = 0; x < 17; x++) {
			this.items.add("-");
		}
		this.items.add(new ActionButton("Add", null));
	}

	@Override
	public String toString() {
		String retVal = "AMSNDAT/";
		for (int x = 0; x < 17; x++) {
			switch (x) {
			case 0:
				retVal += "I" + this.items.get(x);
				break;
			case 7:
				retVal += "DEPLOC:" + this.items.get(x);
				break;
			case 8:
				retVal += "ARRLOC:" + this.items.get(x) + "/\n";
				break;
			case 9:
				retVal += "MSNACFT:" + this.items.get(x);
				break;
			case 14:
				retVal += "1" + this.items.get(x);
				break;
			case 15:
				retVal += "2" + this.items.get(x);
				break;
			case 16:
				retVal += "3" + this.items.get(x);
				break;
			default:
				retVal += this.items.get(x);
				break;
			}
			retVal += "/";
		}
		retVal += "\nAMSNLOC/04000ZJUN2018/042359ZJUN2018//\n";
		return retVal;
	}

	/**
	 * @return whether or not this ATO line is completely blank
	 */
	@Override
	public boolean isBlank() {
		boolean retVal = true;
		for (int x = 0; x < this.items.size() - 1; x++) {
			if (this.items.get(x) != null && !this.items.get(x).equals("-")) {
				retVal = false;
			}
		}
		return retVal;
	}
}
