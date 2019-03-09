package structures;

import java.util.ArrayList;

import swing.Singleton;

/**
 * An ArrayList of Assets built to integrate with Tables via the setValueAt and
 * getValueAt functions
 */
public abstract class ListOfAsset extends ArrayList<Asset> implements Singleton {

	/**
	 * Constructor
	 */
	protected ListOfAsset() {
		this.create();
	}

	/**
	 * Serialization information
	 */
	private static final long serialVersionUID = -4628040775101357858L;

	@Override
	public abstract void create();

	/**
	 * @param row
	 * @param col
	 * @return object at a particular location
	 */
	public Object getValueAt(int row, int col) {
		Asset chosen = this.get(row);
		switch (col) {
		case 0:
			return chosen.isAddToRundown();
		case 1:
			return chosen.getID().getFullCallsign();
		case 2:
			return chosen.getID().getVCS();
		case 3:
			return chosen.getID().getMode2();
		case 4:
			return chosen.getID().getSpecType();
		case 5:
			return chosen.getID().getTypeCat();
		case 6:
			return chosen.getOnStation();
		case 7:
			return chosen.getOffStation();
		default:
			return chosen.getID().getFullCallsign();
		}
	}

	/**
	 * @param aValue
	 * @param row
	 * @param col
	 */
	public void setValueAt(Object aValue, int row, int col) {
		Asset chosen = this.get(row);
		switch (col) {
		case 0:
			chosen.setAddToRundown((Boolean) aValue);
			break;
		case 1:
			chosen.getID().setFullCallsign(aValue.toString());
			break;
		case 2:
			chosen.getID().setVCS(aValue.toString());
			break;
		case 3:
			chosen.getID().setMode2(aValue.toString());
			break;
		case 4:
			chosen.getID().setSpecType(aValue.toString());
			break;
		case 5:
			chosen.getID().setTypeCat(aValue.toString());
			break;
		case 6:
			chosen.setOnStation(aValue.toString());
			break;
		case 7:
			chosen.setOffStation(aValue.toString());
			break;
		default:
			break;
		}
	}
}
