package edu.performance.test.nativo.floatoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class FloatOperationNativeActivity extends PerformanceTestActivity {
	
	FloatOperationNative operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new FloatOperationNative(this);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
