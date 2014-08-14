package edu.performance.test.nativo.memoryoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

/**
 * This class use native code to manipulate memory spaces. The operations
 * performed are: - Allocation - Copy of arrays - Desallocation
 * 
 * @author Thiago
 */
public class MemoryOperationNative extends PerformanceTest<Integer> {

	public MemoryOperationNative(PerformanceTestActivity activity, int level) {
		super(level, activity);
		activity.executeTest();
	}

	public void execute() {

		testNallocMemory(this.getLevel());
		/* System.err.println( */testNcopyMemory();// );
		testNfreeMemory();

		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);

	}

	/**
	 * This method use malloc c++ function to allocate a char pointer to a array
	 * with level length and put the letter t in each array position. This
	 * method is a call to native c/c++ function.
	 * 
	 * @param level
	 *            Determines the length of char array.
	 */
	public native void testNallocMemory(int level);

	/**
	 * This method use memcpy c++ function to copy the array created earlier to
	 * another char array. This method is a call to native c/c++ function.
	 * 
	 * @return Returns the new char array.
	 */
	public native String testNcopyMemory();

	/**
	 * This method use free c++ function to desallocate a char pointer alloctade
	 * by alloc function. This method is a call to native c/c++ function.
	 */
	public native void testNfreeMemory();

}
