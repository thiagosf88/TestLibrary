package edu.performance.test.integeroperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

/**
 * This class extends PerformanceTest and it executes operations with integer
 * number. Currently these operations are: - Primes calculation by Sieve of
 * Eratosthenes method - Fibonacci calculation - Ackermann method
 * 
 * @author Thiago
 */
public class IntegerOperation extends PerformanceTest<Integer> {

	public IntegerOperation(PerformanceTestActivity activity, int level) {
		super(level, activity);

		activity.executeTest();
	}

	public void execute() {

		testAIntegerOperationSieveJava(this.getLevel());
		testAIntegerOperationFibonacciJava(this.getLevel());
		testAIntegerOperationAckermann(this.getLevel(), this.getLevel());
		
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
	public void testAIntegerOperationSieveJava(int level) {

		int N = level;

		// initially assume all integers are prime
		boolean[] isPrime = new boolean[N + 1];
		for (int i = 2; i <= N; i++) {
			isPrime[i] = true;
		}

		// mark non-primes <= N using Sieve of Eratosthenes
		for (int i = 2; i * i <= N; i++) {

			// if i is prime, then mark multiples of i as nonprime
			// suffices to consider mutiples i, i+1, ..., N/i
			if (isPrime[i]) {
				for (int j = i; i * j <= N; j++) {
					isPrime[i * j] = false;
				}
			}
		}

		// count primes
		int primes = 0;
		for (int i = 2; i <= N; i++) {
			if (isPrime[i])
				primes++;
		}
		System.out.println("The number of primes <= " + N + " is " + primes);
	}

	/**
	 * This method calculates Fibonacci number until the index level. This
	 * method is a call to native c/c++ function.
	 * 
	 * @param level
	 *            Indicates the index of the last number of Fibonacci sequence
	 *            which will be calculated.
	 */
	public void testAIntegerOperationFibonacciJava(int level) {
		int i = 1;
		int j = 0;
		int t;
		for (int k = 1; k <= level; k++) {
			t = i + j;
			i = j;
			j = t;
		}

	}

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
	public void testAIntegerOperationAckermann(long m, long n) {
		m = m > 2 ? 2 : m;
		n = n > 2 ? 2 : n;
		System.out.println(ackermann(m, n));
	}

	private long ackermann(long m, long n) {
		if (m == 0)
			return n + 1;
		if (n == 0)
			return ackermann(m - 1, 1);
		return ackermann(m - 1, ackermann(m, n - 1));
	}

}
