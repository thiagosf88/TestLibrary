package edu.performance.test.graphoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class GraphOperationActivity extends PerformanceTestActivity {
	
	GraphOperation operation;
	
	protected String level;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_FILENAME))		
			level = getIntent().getExtras().getString(Library.LEVEL_FILENAME);
			else{
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
			}
		}
		operation = new GraphOperation(this, level);
		
	}

	@Override
	public void execute() {
		
		operation.execute();
		
	}

}
