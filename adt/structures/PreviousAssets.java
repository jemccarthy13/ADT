package structures;

import java.awt.Component;

import atoLookup.ATOLookupFrame;
import swing.SingletonHolder;

/**
 * An container for Assets that were previously controlled
 */
public class PreviousAssets extends ListOfAsset {

	/** Serialization information */
	private static final long serialVersionUID = 8329278307881688968L;

	/**
	 * To handle override / re-import, allow ATOAssets to be overwritten.
	 * 
	 * @param newInstance - replacement instance
	 * 
	 * @todo - check if this is used anywhere
	 */
	public void resetInstance(PreviousAssets newInstance) {
		this.clear();
		for (Asset ast : newInstance) {
			this.add(ast);
		}
		((Component) SingletonHolder.getInstanceOf(ATOLookupFrame.class)).repaint();
	}

	/**
	 * Clear ATO assets from the ATO Lookup
	 */
	public void zeroize() {
		this.clear();
	}

	@Override
	public void create() {
		// nothing
	}
}
