package edu.performance.test.nativo.integeroperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class IntegerOperationNativeActivity extends PerformanceTestActivity {
	
	IntegerOperationNative operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new IntegerOperationNative(this);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
