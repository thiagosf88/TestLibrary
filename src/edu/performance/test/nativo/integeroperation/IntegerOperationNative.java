package edu.performance.test.nativo.integeroperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

/**
 * This class extends PerformanceTest<Integer> and it executes operations with
 * integer number using native codes. Currently this operations are - Primes
 * calculation by Sieve of Eratosthenes method (error!!!) - Fibonacci
 * calculation - Ackermann method
 * 
 * @author Thiago
 */
public class IntegerOperationNative extends PerformanceTest<Integer> {

	public IntegerOperationNative(PerformanceTestActivity activity) {
		super(new Integer(100), activity);
		
		activity.executeTest();
	}

	public void execute() {

		testNIntegerOperationAckermann(this.getLevel());

		testNIntegerOperationFibonacci(this.getLevel());

		System.out.println(testNIntegerOperationSieve(this.getLevel()));

		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
	}

	/**
	 * This method calculates the quantity of prime number lower than level.
	 * This method is a call to native c/c++ function.
	 * 
	 * @param level
	 *            Defines the number used as ceiling.
	 */
	public native int testNIntegerOperationSieve(int level);

	/**
	 * This method calculates Fibonacci number until the index level.
	 * 
	 * @param level
	 *            Indicates the index of the last number of Fibonacci sequence
	 *            which will be calculated. This method is a call to native
	 *            c/c++ function.
	 */
	public native void testNIntegerOperationFibonacci(int level);

	/**
	 * This method implements the Ackermann-Peter function. This a
	 * fully-computable whose result increases quickly. Because of that the
	 * parameter values must be really small. Currently this implementation is
	 * limiting m in 4 and n in 3. This method is a call to native c/c++
	 * function.
	 * 
	 * @param m
	 * @param n
	 */
	public native void testNIntegerOperationAckermann(int level);

}
