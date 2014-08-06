package edu.performance.test.graphoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class GraphActivity extends PerformanceTestActivity {
	
	GraphOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new GraphOperation(1000, this);
		
	}

	@Override
	public void execute() {
		
		operation.execute();
		
	}

}
