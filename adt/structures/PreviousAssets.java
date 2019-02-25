package structures;

import java.awt.Component;
import java.util.ArrayList;

import atoLookup.ATOLookupFrame;
import swing.SingletonHolder;

/**
 * An container for Assets that were previously controlled
 */
public class PreviousAssets extends ArrayList<Asset> {

	/**
	 * Serializiation
	 */
	private static final long serialVersionUID = 8329278307881688968L;
	private static PreviousAssets instance = new PreviousAssets();

	private PreviousAssets() {
	}

	/**
	 * @return single instance
	 */
	public static PreviousAssets getInstance() {
		return instance;
	}

	/**
	 * To handle override / re-import, allow ATOAssets to be overwritten.
	 * 
	 * @param newInstance - replacement instance
	 * 
	 *                    TODO - check if this is used anywhere
	 */
	public static void resetInstance(PreviousAssets newInstance) {
		instance.clear();
		for (Asset ast : newInstance) {
			instance.add(ast);
		}
		((Component) SingletonHolder.getInstanceOf(ATOLookupFrame.class)).repaint();
	}

	/**
	 * Clear ATO assets from the ATO Lookup
	 */
	public static void zeroize() {
		instance.clear();
	}
}
