package edu.performance.test.floatoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class FloatOperationActivity extends PerformanceTestActivity {
	
	FloatOperation operation;
	protected double level = 89;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_DOUBLE)){
				level = getIntent().getExtras().getInt(Library.LEVEL_DOUBLE);
			}
		}
		
		operation = new FloatOperation(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
