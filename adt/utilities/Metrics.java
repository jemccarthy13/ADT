package utilities;

import java.util.HashMap;
import java.util.HashSet;

import outputframe.OutputFrame;
import structures.Asset;
import structures.PreviousAssets;
import structures.RundownAssets;
import swing.SingletonHolder;

/**
 * Helper class to provide end of mission metrics
 */
public class Metrics {

	/**
	 * Calculate and display the end of mission metrics
	 */
	public static void calculateAndShow() {
		OutputFrame f = (OutputFrame) SingletonHolder.getInstanceOf(OutputFrame.class);

		// combine previous assets and rundown assets with no duplicates
		HashSet<Asset> assets = new HashSet<Asset>();
		assets.addAll(PreviousAssets.getInstance());
		assets.addAll(RundownAssets.getInstance());

		// store the map of type: # controlled
		HashMap<String, Integer> results = new HashMap<String, Integer>();

		// loop through and provide running totals
		int total = 0;
		for (Asset a : assets) {
			String cat = a.getID().getTypeCat();
			if (!cat.equals("")) {
				// if the category was valid, increment the count for that category
				if (results.containsKey(cat)) {
					results.put(cat, results.get(cat) + 1);
				} else {
					results.put(cat, 1);
				}
				total++;
			}
		}

		// now pretty up the results
		String output = "";
		for (String k : results.keySet()) {
			output += k + ": " + results.get(k) + "\r\n";
		}
		output += "Total: " + total;

		// set it in the output frame
		f.setOutput(output);
	}
}
