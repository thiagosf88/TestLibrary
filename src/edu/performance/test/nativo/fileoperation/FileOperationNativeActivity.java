package edu.performance.test.nativo.fileoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class FileOperationNativeActivity extends PerformanceTestActivity {
	
	FileOperationNative operation;
	protected String levelString;
	protected int level;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String stretch = "";
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_FILENAME)
					&& getIntent().hasExtra(PerformanceTestActivity.STRETCH)
					&& getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT)){
			
				stretch = getIntent().getExtras().getString(PerformanceTestActivity.STRETCH);					
				levelString = getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_FILENAME);
				level = getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT);
			}
				else{
					Bundle extras = new Bundle();
					extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level, filename ou stretch!");
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
		
		operation = new FileOperationNative(this, levelString, stretch, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
