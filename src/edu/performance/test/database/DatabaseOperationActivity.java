package edu.performance.test.database;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;

public class DatabaseOperationActivity extends PerformanceTestActivity {
	
	DatabaseOperation operation;
	protected int level = 10;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT)){
				level = getIntent().getExtras().getInt(PerformanceTestInterface.LEVEL_INT);
			}
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestInterface.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				setResult(RESULT_CANCELED);
				finish();
			}
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
		}
		
		operation = new DatabaseOperation(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
