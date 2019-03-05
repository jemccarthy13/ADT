package utilities;

import java.util.HashMap;
import java.util.HashSet;

import outputframe.OutputFrame;
import structures.Asset;
import structures.PreviousAssets;
import structures.RundownAssets;
import swing.SingletonHolder;

/**
 * Provide end of mission metrics
 */
public class Metrics {

	/**
	 * Calculate and display the end of mission metrics
	 */
	public static void calculateAndShow() {
		OutputFrame f = (OutputFrame) SingletonHolder.getInstanceOf(OutputFrame.class);

		HashSet<Asset> assets = new HashSet<Asset>();

		assets.addAll(PreviousAssets.getInstance());
		assets.addAll(RundownAssets.getInstance());

		HashMap<String, Integer> results = new HashMap<String, Integer>();

		int total = 0;
		for (Asset a : assets) {
			String cat = a.getID().getTypeCat();
			if (!cat.equals("")) {
				if (results.containsKey(cat)) {
					results.put(cat, results.get(cat) + 1);
				} else {
					results.put(cat, 1);
				}
				total++;
			}
		}

		String output = "";
		for (String k : results.keySet()) {
			output += k + ": " + results.get(k) + "\r\n";
		}
		output += "Total: " + total;
		f.setOutput(output);
	}
}
