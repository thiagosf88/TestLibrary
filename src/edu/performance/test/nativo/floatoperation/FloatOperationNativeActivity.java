package edu.performance.test.nativo.floatoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class FloatOperationNativeActivity extends PerformanceTestActivity {
	
	FloatOperationNative operation;
	protected double level;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_DOUBLE))		
			level = getIntent().getExtras().getInt(Library.LEVEL_DOUBLE);
			else{
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
			}
		}
		
		operation = new FloatOperationNative(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
