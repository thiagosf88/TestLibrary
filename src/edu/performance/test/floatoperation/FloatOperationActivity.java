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
				level = getIntent().getExtras().getDouble(Library.LEVEL_DOUBLE);
			}
			else{
				Bundle extras = new Bundle();
				extras.putString(Library.ERROR_MESSAGE, "O nível não foi fornecido!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				return;
			}
	}
	else{
		Bundle extras = new Bundle();
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
		
		finishTest(extras);
		finish();
	}
		
		operation = new FloatOperation(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
