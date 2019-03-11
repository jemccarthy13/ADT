package adtunittest;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import structures.Airspace;
import structures.AirspaceList;
import structures.DIR;
import structures.KeypadFinderCGRS;
import swing.SingletonHolder;
import utilities.GridSettings;

/**
 * Tests to ensure translation was valid
 */
public class CGRSKeypadFinderTest {

	/**
	 * A CGRS Keypad Finder
	 */
	KeypadFinderCGRS finder = new KeypadFinderCGRS();

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
	 * Do some nonstandard keypad comparisons
	 */
	@Test
	public void breakCGRSFinder() {
		String representation = "88";
		HashSet<String> result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.size() == 0);

		representation = "AN";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.size() == 0);

		AirspaceList list = (AirspaceList) SingletonHolder.getInstanceOf(AirspaceList.class);
		list.add(new Airspace("TEST", "99AL", "100", "100", Color.RED));
		list.add(new Airspace("TEST", "88AL", "100", "100", Color.BLUE));
		list.add(new Airspace("TEST", "67AL", "100", "100", Color.GREEN));
		list.add(new Airspace("TEST", "77AL", "100", "100", Color.GREEN));
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
		HashSet<String> result = this.finder.findKeypads(DIR.SELF, "88AM", keypads);
		Assert.assertTrue(result.toString().equals("[88AM3, 88AM2, 88AM1]"));
	}

	/**
	 * Sort a HashSet
	 * 
	 * @param set
	 * @return sorted ArrayList
	 */
	public ArrayList<String> sort(HashSet<String> set) {
		ArrayList<String> list = new ArrayList<String>(set);
		Collections.sort(list);
		return list;
	}

	/**
	 * Run the gamit of test cases to validate all shorthand styles
	 */
	@Test
	public void testCGRSRepresentationKeypadFinder() {
		String[] representations = { "88AM123", "88AM1-5", "88AM7+", "88AM5+", "94AM1+", "93AL3+", "92AO9+",
				"94AM 93AM", "94AM123 93AM7+ 99AN9+4c", "99AN9+4c", "99AN1+4c", "99AN3+4c", "99AN7+4c", "99AN2+4c",
				"99AN4+4c", "99AN5+4c", "99AN6+4c", "99AN8+4c", "99B7+", "2B2+", "99B4+", "99B6+", "99B8+", "99B0+",
				"88AM-", "88?" };
		String[] solutions = { "[88AM1, 88AM2, 88AM3]", "[88AM1, 88AM2, 88AM3, 88AM4, 88AM5]",
				"[87AL3, 87AM1, 87AM2, 88AL6, 88AL9, 88AM4, 88AM5, 88AM7, 88AM8]",
				"[88AM1, 88AM2, 88AM3, 88AM4, 88AM5, 88AM6, 88AM7, 88AM8, 88AM9]",
				"[94AL3, 94AL6, 94AM1, 94AM2, 94AM4, 94AM5, 95AL9, 95AM7, 95AM8]",
				"[93AL2, 93AL3, 93AL5, 93AL6, 93AM1, 93AM4, 94AL8, 94AL9, 94AM7]",
				"[91AO2, 91AO3, 91AP1, 92AO5, 92AO6, 92AO8, 92AO9, 92AP4, 92AP7]",
				"[93AM1, 93AM2, 93AM3, 93AM4, 93AM5, 93AM6, 93AM7, 93AM8, 93AM9, 94AM1, 94AM2, 94AM3, 94AM4, 94AM5, 94AM6, 94AM7, 94AM8, 94AM9]",
				"[92AL3, 92AM1, 92AM2, 93AL6, 93AL9, 93AM4, 93AM5, 93AM7, 93AM8, 94AM1, 94AM2, 94AM3, 98AN3, 98AO1, 99AN9, 99AO7]",
				"[98AN3, 98AO1, 99AN9, 99AO7]", "[100AM9, 100AN7, 99AM3, 99AN1]", "[100AN9, 100AO7, 99AN3, 99AO1]",
				"[98AM3, 98AN1, 99AM9, 99AN7]", "[]", "[]", "[]", "[]", "[]",
				"[98A3, 98B1, 98B2, 99A6, 99A9, 99B4, 99B5, 99B7, 99B8]",
				"[2B1, 2B2, 2B3, 2B4, 2B5, 2B6, 3B7, 3B8, 3B9]",
				"[99A3, 99A6, 99A9, 99B1, 99B2, 99B4, 99B5, 99B7, 99B8]",
				"[99B2, 99B3, 99B5, 99B6, 99B8, 99B9, 99C1, 99C4, 99C7]",
				"[98B1, 98B2, 98B3, 99B4, 99B5, 99B6, 99B7, 99B8, 99B9]", "[]", "[]", "[]" };

		ArrayList<String> result;
		for (int x = 0; x < representations.length; x++) {
			result = sort(this.finder.getKeypads(representations[x]));
			Assert.assertTrue(result.toString().equals(solutions[x]));
		}

	}

	/**
	 * Find some more keypads
	 */
	@Test
	public void testCGRSKeypadInCircle() {
		ArrayList<String> result = sort(this.finder.getRepresentationFromCircle("0930S02600E", 1.0));
		Assert.assertTrue(result.toString().equals("[1B3, 1C1, 2B9, 2C7]"));

		result = sort(this.finder.getRepresentationFromCircle("0930N05000E", 15.0));
		Assert.assertTrue(result.toString().equals(
				"[39AX2, 39AX3, 39AX5, 39AX6, 39AY1, 39AY2, 39AY4, 39AY5, 40AX5, 40AX6, 40AX8, 40AX9, 40AY4, 40AY5, 40AY7, 40AY8]"));

		result = sort(this.finder.getRepresentationFromCircle("0930N05000E", 20.0));
		Assert.assertTrue(result.toString().equals(
				"[39AX1, 39AX2, 39AX3, 39AX5, 39AX6, 39AX9, 39AY1, 39AY2, 39AY3, 39AY4, 39AY5, 39AY7, 40AX3, 40AX5, 40AX6, 40AX7, 40AX8, 40AX9, 40AY1, 40AY4, 40AY5, 40AY7, 40AY8, 40AY9]"));

		GridSettings settings = (GridSettings) SingletonHolder.getInstanceOf(GridSettings.class);
		settings.setOrigin("4500N05000W");

		result = sort(this.finder.getRepresentationFromCircle("4530N04000W", 10.0));
		Assert.assertTrue(result.toString().equals("[1T2, 1T3, 1T6, 1U1, 1U2, 1U4, 2T6, 2T8, 2T9, 2U4, 2U7, 2U8]"));

	}

}
