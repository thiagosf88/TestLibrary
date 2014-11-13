package edu.performance.test.sortingoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class SortingOperationActivity extends PerformanceTestActivity {
	
	SortOperation operation;
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
				setResult(RESULT_CANCELED);
				finish();
			}
		}
		
		operation = new SortOperation(level, this);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
