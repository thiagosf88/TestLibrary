package edu.performance.test.database;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class DatabaseOperationActivity extends PerformanceTestActivity {
	
	DatabaseOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new DatabaseOperation(this);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
