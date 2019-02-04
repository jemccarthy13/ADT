package adtutilitiestest;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import structures.KeypadFinder;
import utilities.CGRSKeypadFinder;

/**
 * Tests to ensure translation was valid
 */
public class CGRSKeypadFinderTest {

	/**
	 * Find some keypads
	 */
	@Test
	public void testCGRSKeypadFinder() {
		CGRSKeypadFinder finder = new CGRSKeypadFinder();
		String[] keypads = { "1", "2", "3" };
		HashSet<String> result = finder.findKeypads(KeypadFinder.DIR.SELF, "88AM", keypads);
		Assert.assertTrue(result.toString().equals("[88AM3, 88AM2, 88AM1]"));
	}

	/**
	 * Find some more keypads
	 */
	@Test
	public void testCGRSRepresentationKeypadFinder() {
		CGRSKeypadFinder finder = new CGRSKeypadFinder();
		String representation = "88AM123";
		HashSet<String> result = finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[88AM3, 88AM2, 88AM1]"));

		representation = "88AM1-5";
		result = finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[88AM5, 88AM4, 88AM3, 88AM2, 88AM1]"));

		representation = "88AM7+";
		result = finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[88AL9, 88AM8, 88AL6, 88AM7, 87AL3, 88AM5, 88AM4, 87AM2, 87AM1]"));

		representation = "88AM5+";
		result = finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[88AM9, 88AM8, 88AM7, 88AM6, 88AM5, 88AM4, 88AM3, 88AM2, 88AM1]"));

		representation = "94AM1+";
		result = finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[94AM2, 94AM1, 95AM7, 94AM5, 94AL3, 94AM4, 94AL6, 95AM8, 95AL9]"));

		representation = "94AM 93AM";
		result = finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals(
				"[93AM9, 94AM9, 93AM8, 94AM8, 93AM7, 94AM7, 93AM6, 94AM2, 93AM1, 94AM1, 94AM6, 93AM5, 94AM5, 93AM4, 94AM4, 93AM3, 94AM3, 93AM2]"));

		representation = "94AM123 93AM7+ 99AN9+4c";
		result = finder.getKeypads(representation);
		Assert.assertTrue(result.size() == 16);

		representation = "99AN9+4c";
		result = finder.getKeypads(representation);
		Assert.assertTrue(result.toString().equals("[99AN9, 99AO7, 98AO1, 98AN3]"));

	}
}
