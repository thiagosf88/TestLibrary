package edu.performance.test.nativo.integeroperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class IntegerOperationNativeActivity extends PerformanceTestActivity {
	
	IntegerOperationNative operation;
	protected int level = -1;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_INT))		
			level = getIntent().getExtras().getInt(Library.LEVEL_INT);
			else{
				Bundle extras = new Bundle();
				extras.putString(Library.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level");
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
		
		operation = new IntegerOperationNative(this, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
