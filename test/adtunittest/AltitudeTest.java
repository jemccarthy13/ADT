package adtunittest;

import org.junit.Assert;
import org.junit.Test;

import structures.Altitude;

/**
 * Test the Altitude structure class
 */
public class AltitudeTest {

	/**
	 * Test string conversion of Altitude
	 */
	@Test
	public void testStrings() {
		Altitude first = new Altitude("100", "200");
		Assert.assertTrue(first.toString().equals("FL 100 to FL 200"));

		first = new Altitude("150", "");
		Assert.assertTrue(first.toString().equals("FL 150 to FL 150"));
	}

	/**
	 * Test the "overlaps" function of Altitude
	 */
	@Test
	public void testOverlaps() {

		Altitude first = new Altitude("", "");
		Altitude second = new Altitude("", "");

		Assert.assertFalse(first.overlaps(second, 4));

		first.setLower("150");
		first.setUpper("200");
		Assert.assertFalse(first.overlaps(second, 4));

		first = new Altitude("", "");
		second.setLower("150");
		second.setUpper("200");
		Assert.assertFalse(first.overlaps(second, 4));

		first.setLower("150");
		first.setUpper("200");
		second.setLower("010");
		second.setUpper("");
		Assert.assertFalse(first.overlaps(second, 4));

		second.setLower("150");
		Assert.assertTrue(first.overlaps(second, 4));

		second.setLower("100");
		second.setUpper("200");
		Assert.assertTrue(first.overlaps(second, 4));

		first.setLower("190");
		first.setUpper("200");
		Assert.assertTrue(first.overlaps(second, 4));

		first.setLower("");
		Assert.assertTrue(first.overlaps(second, 4));

		first.setLower("190");
		first.setUpper("");
		Assert.assertTrue(first.overlaps(second, 4));

		first.setUpper("200");
		second.setLower("");
		Assert.assertTrue(first.overlaps(second, 4));

	}
}
