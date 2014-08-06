package edu.performance.test.memoryoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class MemoryOperationActivity extends PerformanceTestActivity {
	
	MemoryOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new MemoryOperation(this);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
