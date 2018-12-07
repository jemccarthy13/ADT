package test;

import javax.swing.table.AbstractTableModel;

import org.junit.Assert;
import org.junit.Test;

import datastructures.TestStruct;
import swing.SwingContainer;

/**
 * Test the remainder of the custom swing utilities.
 */
public class CustomSwing extends BaseTest {

	/**
	 * test interface
	 */
	interface testInt {
		/**
		 * test function
		 */
		void myFunc();
	}

	/**
	 * I expect an exception to be thrown
	 */
	@Test
	public void instanceOfInstantiateException() {
		SwingContainer<AbstractTableModel> sc = new SwingContainer<AbstractTableModel>();

		AbstractTableModel l = sc.getInstanceOf(AbstractTableModel.class);
		Assert.assertNull(l);
	}

	/**
	 * I expect an exception to be thrown
	 */
	@Test
	public void instanceOfAccessException() {
		SwingContainer<TestStruct> hc = new SwingContainer<TestStruct>();
		Assert.assertNull(hc.getInstanceOf(TestStruct.class));
	}
}
