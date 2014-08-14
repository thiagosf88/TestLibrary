package edu.performance.test.nativo.floatoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

/**
 * This class extends PerformanceTest<Double> and it executes operations with
 * double float point number using native codes. Currently this operations are:
 * - Square root calculation by Newton method - Sine calculation from Math
 * library - Pi calculation by MonteCarlo method - Linpack benchmark - Cubic
 * solve - Degree to Radian - Radian to degree
 * 
 * @author Thiago
 */
class FloatOperationNative extends PerformanceTest<Double> {
	
	public FloatOperationNative(PerformanceTestActivity activity, double level) {
		super(level, activity);

		activity.executeTest();
	}

	@Override
	public void execute() {

		testNFloatOperationLinpack(this.getLevel());
		testNFloatOperationMonteCarlo(this.getLevel());
		testNFloatOperationSqrt(this.getLevel());
		testNFloatOperationSine(this.getLevel());
		testNFloatOperationCubic(this.getLevel());
		testNFloatOperationdeg2rad(this.getLevel());
		testNFloatOperationrad2deg(this.getLevel());
		
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
	private native void testNFloatOperationSine(double levelFloat);

	/**
	 * This method performs square root of level calculation using Newton
	 * methodology. This method is a call to native c/c++ function.
	 * 
	 * @param level
	 *            Is the number whose the root will be calculated.
	 */
	private native void testNFloatOperationSqrt(double level);

	/**
	 * This method use MonteCarlo methodology to calculate a approximation. This
	 * method is a call to native c/c++ function.
	 * 
	 * @param level
	 *            Defines the limit to the loop which is used to calculated.
	 */
	private native void testNFloatOperationMonteCarlo(double level);

	/**
	 * This method executes Linpack benchmark using a version of the
	 * implementation posted in
	 * http://www.jeckle.de/freeStuff/jLinpack/Linpack.java (09/06/2014).
	 * Linpack solves a dense n by n system of linear equations Ax = b. In this
	 * test the n is determinate by level. This method is a call to native c/c++
	 * function.
	 * 
	 * @param level
	 *            Define the dimension of linear equation system. Max.: 5000
	 */
	private native void testNFloatOperationLinpack(double level);

	/**
	 * This method solves a cubic polynomial. From Mibench code. This method is
	 * a call to native c/c++ function.
	 * 
	 * @param level
	 *            It is used to generate the expression coefficients.
	 */
	private native void testNFloatOperationCubic(double level);

	/**
	 * This method converts a degree angle "level" to radians. From Mibench
	 * code. This method is a call to native c/c++ function.
	 * 
	 * @param level
	 *            It is the angle in degrees which will be converted.
	 * @return The radian measure of angle.
	 */
	private native double testNFloatOperationdeg2rad(double level);

	/**
	 * This method converts a radian "level" to degree angle. From Mibench code.
	 * This method is a call to native c/c++ function.
	 * 
	 * @param level
	 *            It is the radian measure which will be converted.
	 * @return The angle in degrees from radian measure.
	 */
	private native double testNFloatOperationrad2deg(double level);

}
