package edu.performance.test.nativo.floatoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class FloatOperationNativeActivity extends PerformanceTestActivity {
	
	FloatOperationNative operation;
	protected double level;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_DOUBLE))		
			level = getIntent().getExtras().getDouble(PerformanceTestActivity.LEVEL_DOUBLE);
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				finish();
			}
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			finish();
		}
		
		operation = new FloatOperationNative(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
