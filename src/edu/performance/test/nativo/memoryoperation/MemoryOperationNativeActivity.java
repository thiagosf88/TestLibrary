package edu.performance.test.nativo.memoryoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class MemoryOperationNativeActivity extends PerformanceTestActivity {
	
	MemoryOperationNative operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new MemoryOperationNative(this);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
