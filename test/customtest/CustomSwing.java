package customtest;

import javax.swing.table.AbstractTableModel;

import org.junit.Assert;
import org.junit.Test;

import datastructures.TestStruct;
import swing.SingletonHolder;
import utilities.BaseTest;

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
		// SingletonHolder<AbstractTableModel> sc = new
		// SingletonHolder<AbstractTableModel>();

		AbstractTableModel l = (AbstractTableModel) SingletonHolder.getInstanceOf(AbstractTableModel.class);
		Assert.assertNull(l);
	}

	/**
	 * I expect an exception to be thrown
	 */
	@Test
	public void instanceOfAccessException() {
		// SingletonHolder<TestStruct> hc = new SingletonHolder<TestStruct>();
		Assert.assertNull(SingletonHolder.getInstanceOf(TestStruct.class));
	}
}
