package adtutilitiestest;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import structures.Airspace;
import structures.AirspaceList;
import structures.Asset;
import structures.KeypadFinder;
import utilities.CGRSKeypadFinder;
import utilities.DebugUtility;

/**
 * Tests to ensure translation was valid
 */
public class CGRSKeypadFinderTest {

	/**
	 * A CGRS Keypad Finder
	 */
	CGRSKeypadFinder finder = new CGRSKeypadFinder();

	/**
	 * Test the ability of assets to detect overlap
	 */
	@Test
	public void testAsset2DOverlap() {
		Asset first = new Asset();
		first.setAirspace("88AM123");
		Asset second = new Asset();
		second.setAirspace("88AM");

		HashSet<String> result = first.sharesAirspaceWith(second);
		Assert.assertTrue(result.size() == 3);

		first.setAirspace("89AM");
		second.setAirspace("88AM");

		result = first.sharesAirspaceWith(second);
		Assert.assertTrue(result.size() == 0);
	}

	/**
	 * Test the ability of the CGRSKeypadFinder to detect overlap
	 */
	@Test
	public void testCGRSFinderOverlap() {
		String all = "88AM";

		HashSet<String> firstResult = this.finder.getKeypads(all);
		HashSet<String> secondResult = this.finder.getKeypads(all);

		HashSet<String> oneKeypad = this.finder.getKeypads("88AM1");

		HashSet<String> allSet = new HashSet<String>(firstResult);
		allSet.retainAll(secondResult);

		Assert.assertTrue(allSet.size() == 9);
		Assert.assertEquals(allSet, firstResult);
		Assert.assertEquals(allSet, secondResult);

		HashSet<String> oneOverlap = new HashSet<String>(firstResult);
		oneOverlap.retainAll(oneKeypad);

		Assert.assertTrue(oneOverlap.size() == 1);
		Assert.assertEquals(oneOverlap.toString(), "[88AM1]");
	}

	/**
	 * 
	 */
	@Test
	public void performanceTest() {
		String representation = "";
		ArrayList<String> representations = new ArrayList<String>();

		for (int x = 1; x < 1001; x += 20) {
			for (int y = 1; y < 26; y++) {
				representation += x + String.valueOf((char) (y + 65)) + " ";
				representations.add(representation);
			}
		}

		ArrayList<String> times = new ArrayList<String>();
		for (String s : representations) {
			long startTime = System.nanoTime();
			this.finder.getKeypads(s);
			long endTime = System.nanoTime();
			times.add("" + Long.valueOf(endTime - startTime));

			for (int x = 1; x < 10; x++) {
				startTime = System.nanoTime();
				this.finder.getKeypads(s);
				endTime = System.nanoTime();
				times.add("" + Long.valueOf(endTime - startTime));
			}

		}
	}

	/**
	 * 
	 */
	@Test
	public void breakCGRSFinder() {
		String representation = "88";
		HashSet<String> result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.size() == 0);

		representation = "AN";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.size() == 0);

		AirspaceList.getInstance().add(new Airspace("TEST", "99AL", "100", "100"));
		AirspaceList.getInstance().add(new Airspace("TEST", "88AL", "100", "100"));
		AirspaceList.getInstance().add(new Airspace("TEST", "67AL", "100", "100"));
		AirspaceList.getInstance().add(new Airspace("TEST", "77AL", "100", "100"));
		// Assert.assertTrue(AirspaceList.getInstance().size() == 1);
		representation = "TEST";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.size() == 9);
	}

	/**
	 * Find some keypads
	 */
	@Test
	public void testCGRSKeypadFinder() {
		String[] keypads = { "1", "2", "3" };
		HashSet<String> result = this.finder.findKeypads(KeypadFinder.SELF, "88AM", keypads);
		Assert.assertTrue(result.toString().equals("[88AM3, 88AM2, 88AM1]"));
	}

	/**
	 * Find some more keypads
	 */
	@Test
	public void testCGRSRepresentationKeypadFinder() {
		String representation = "88AM123";
		HashSet<String> result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[88AM3, 88AM2, 88AM1]"));

		representation = "88AM1-5";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[88AM5, 88AM4, 88AM3, 88AM2, 88AM1]"));

		representation = "88AM7+";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[88AL9, 88AM8, 88AL6, 88AM7, 87AL3, 88AM5, 88AM4, 87AM2, 87AM1]"));

		representation = "88AM5+";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[88AM9, 88AM8, 88AM7, 88AM6, 88AM5, 88AM4, 88AM3, 88AM2, 88AM1]"));

		representation = "94AM1+";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[94AM2, 94AM1, 95AM7, 94AM5, 94AL3, 94AM4, 94AL6, 95AM8, 95AL9]"));

		representation = "93AL3+";
		result = this.finder.getKeypads(representation);
		DebugUtility.error(Object.class, result.toString());
		Assert.assertTrue(result.toString().equals("[93AM1, 93AM4, 93AL3, 93AL2, 94AL9, 94AL8, 93AL6, 94AM7, 93AL5]"));

		representation = "92AO9+";
		result = this.finder.getKeypads(representation);
		DebugUtility.error(Object.class, result.toString());
		Assert.assertTrue(result.toString().equals("[91AP1, 92AO5, 92AP7, 92AO6, 92AP4, 91AO2, 91AO3, 92AO9, 92AO8]"));

		representation = "94AM 93AM";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals(
				"[93AM9, 93AM8, 94AM9, 93AM7, 94AM8, 93AM6, 94AM7, 93AM1, 94AM2, 94AM1, 93AM5, 94AM6, 93AM4, 94AM5, 93AM3, 94AM4, 93AM2, 94AM3]"));

		representation = "94AM123 93AM7+ 99AN9+4c";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.size() == 16);

		representation = "99AN9+4c";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[99AN9, 99AO7, 98AO1, 98AN3]"));

		representation = "99B7+";
		result = this.finder.getKeypads(representation);
		DebugUtility.error(Object.class, result.toString());
		Assert.assertTrue(result.toString().equals("[99A9, 98B1, 98B2, 99B4, 99B5, 98A3, 99B8, 99A6, 99B7]"));
	}
}
