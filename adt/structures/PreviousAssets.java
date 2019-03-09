package structures;

/**
 * An container for Assets that were previously controlled, so we can retrieve
 * previous approvals and count them for metrics.
 */
public class PreviousAssets extends ListOfAsset {

	/** Serialization information */
	private static final long serialVersionUID = 8329278307881688968L;

	@Override
	public void create() {
		// I just need another ListOfAsset to build and store a list of every asset
		// ever controlled
	}
}
