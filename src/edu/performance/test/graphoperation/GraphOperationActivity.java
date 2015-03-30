package edu.performance.test.graphoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class GraphOperationActivity extends PerformanceTestActivity {
	
	GraphOperation operation;
	
	protected String level;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_FILENAME))		
			level = getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_FILENAME);
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: filename!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
			}
		}
		else{
			Bundle extras = new Bundle();	
			extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "O método getIntent retornou Null!");
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
		}
		operation = new GraphOperation(this, level);
		
	}

	@Override
	public void execute() {
		
		operation.execute();
		
	}

}
