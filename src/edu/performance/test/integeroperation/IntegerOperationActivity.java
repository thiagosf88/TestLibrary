package edu.performance.test.integeroperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class IntegerOperationActivity extends PerformanceTestActivity {
	
	IntegerOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new IntegerOperation(this);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
