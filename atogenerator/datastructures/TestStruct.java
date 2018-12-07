package datastructures;

/**
 */
public class TestStruct {

	private class HiddenStruct {
		HiddenStruct() {
		}

	}

	/**
	 * 
	 */
	TestStruct() {
		HiddenStruct hs = new HiddenStruct();
		hs.notify();
	}
}
