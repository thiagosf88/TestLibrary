package edu.performance.test.matrixoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class MatrixOperationActivity extends PerformanceTestActivity {
	
	MatrixOperation operation;
	protected double level = 100;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_DOUBLE)){
				level = getIntent().getExtras().getDouble(PerformanceTestActivity.LEVEL_DOUBLE);
			}
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "O nível não foi fornecido!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				return;
			}
	}
	else{
		Bundle extras = new Bundle();
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
		extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Parâmetros mínimos não fornecidos!");
		finishTest(extras);
		finish();
	}
		
		operation = new MatrixOperation(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
