package edu.performance.test.nativo.memoryoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class MemoryOperationNativeActivity extends PerformanceTestActivity {
	
	MemoryOperationNative operation;
	protected int level;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_INT))		
			level = getIntent().getExtras().getInt(Library.LEVEL_INT);
			else{
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
			}
		}
		
		operation = new MemoryOperationNative(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
