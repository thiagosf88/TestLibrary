package edu.performance.test.floatoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class FloatOperationActivity extends PerformanceTestActivity {
	
	FloatOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new FloatOperation(this);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
