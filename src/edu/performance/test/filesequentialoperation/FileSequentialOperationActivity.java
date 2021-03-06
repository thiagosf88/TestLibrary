package edu.performance.test.filesequentialoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class FileSequentialOperationActivity extends PerformanceTestActivity {
	
	FileSequentialOperation operation;
	protected int level;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String filePath = "", stretch = "";
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_FILENAME)
					&& getIntent().hasExtra(PerformanceTestActivity.STRETCH)
					){
			level = getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_FILENAME);
			stretch = getIntent().getExtras().getString(PerformanceTestActivity.STRETCH);
			
			
		}
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level ou stretch!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				finish();
				return;
			}
		
		
	}
	else{
		Bundle extras = new Bundle();
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
		finishTest(extras);
		finish();
	}
		
			status.setText(message);
			operation = new FileSequentialOperation(this, stretch, level);
		
		
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
