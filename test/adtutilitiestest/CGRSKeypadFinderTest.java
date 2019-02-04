package adtutilitiestest;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import structures.Airspace;
import structures.AirspaceList;
import structures.Asset;
import structures.KeypadFinder;
import utilities.CGRSKeypadFinder;

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
		HashSet<String> result = this.finder.findKeypads(KeypadFinder.DIR.SELF, "88AM", keypads);
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

		representation = "94AM 93AM";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals(
				"[93AM9, 94AM9, 93AM8, 94AM8, 93AM7, 94AM7, 93AM6, 94AM2, 93AM1, 94AM1, 94AM6, 93AM5, 94AM5, 93AM4, 94AM4, 93AM3, 94AM3, 93AM2]"));

		representation = "94AM123 93AM7+ 99AN9+4c";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.size() == 16);

		representation = "99AN9+4c";
		result = this.finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[99AN9, 99AO7, 98AO1, 98AN3]"));

	}
}
