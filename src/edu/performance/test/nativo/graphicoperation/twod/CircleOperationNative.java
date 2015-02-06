package edu.performance.test.nativo.graphicoperation.twod;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

class CircleOperationNative extends PerformanceTest<Integer> {
	
	private final double MAX_DIM_Linpack = 1000;
	
	public CircleOperationNative(PerformanceTestActivity activity, Integer level) {
		super(level, activity);

		activity.executeTest();
	}

	@Override
	public void execute() {


		testNCircleOperation(this.getLevel());


		
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);

	}

	/**
	 * This method use the library cmath to calculate sine of degree indicated
	 * by level. This method is a call to native c/c++ function.
	 * 
	 * @param level
	 *            Is the degree whose sine will be calculated.
	 */
	private native void testNCircleOperation(int levelFloat);



}

