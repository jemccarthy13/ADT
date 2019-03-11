package adtunittest;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import structures.Airspace;

/**
 * Test the Airspace structure
 */
public class AirspaceTest {

	/**
	 * Test the isBlank airspace function
	 */
	@Test
	public void testBlank() {
		Airspace aspace = new Airspace();
		Assert.assertTrue(aspace.isBlank());

		aspace.setName("A");
		Assert.assertFalse(aspace.isBlank());

		aspace.setName("");
		aspace.setColor(Color.RED);
		Assert.assertFalse(aspace.isBlank());

		aspace = new Airspace();
		aspace.setAirspace("88AM");
		Assert.assertFalse(aspace.isBlank());
	}
}
