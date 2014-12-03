package edu.performance.test.nativo.memoryoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class MemoryOperationNativeActivity extends PerformanceTestActivity {
	
	MemoryOperationNative operation;
	protected int level;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT))		
			level = getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT);
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
		
		operation = new MemoryOperationNative(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
