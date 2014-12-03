package edu.performance.test.memoryoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class MemoryOperationActivity extends PerformanceTestActivity {
	
	MemoryOperation operation;
	
	protected int level = -1;
	
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
			}
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
		}
		operation = new MemoryOperation(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
