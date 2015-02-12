package edu.performance.test.filesequentialoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class FileSequetialOperationActivity extends PerformanceTestActivity {
	
	FileSequentialOperation operation;
	protected int level;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String filePath = "", stretch = "";
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_FILENAME)
					&& getIntent().hasExtra(PerformanceTestActivity.STRETCH)
					&& getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT)){
			filePath = getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_FILENAME);
			stretch = getIntent().getExtras().getString(PerformanceTestActivity.STRETCH);
			
			level = getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT);
		}
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level, filename ou stretch!");
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
			operation = new FileSequentialOperation(this, filePath, stretch, level);
		
		
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
