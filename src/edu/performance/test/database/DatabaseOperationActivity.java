package edu.performance.test.database;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class DatabaseOperationActivity extends PerformanceTestActivity {
	
	DatabaseOperation operation;
	protected int level = 10;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_INT)){
				level = getIntent().getExtras().getInt(Library.LEVEL_INT);
			}
			else{
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
			}
		}
		
		operation = new DatabaseOperation(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
