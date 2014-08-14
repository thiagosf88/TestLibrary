package edu.performance.test.nativo.fileoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class FileOperationNativeActivity extends PerformanceTestActivity {
	
	FileOperationNative operation;
	protected String level;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String stretch = "";
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_FILENAME)
					&& getIntent().hasExtra(Library.STRETCH)){
			
				stretch = getIntent().getExtras().getString(Library.STRETCH);					
				level = getIntent().getExtras().getString(Library.LEVEL_FILENAME);
			}
				else{
					Bundle extras = new Bundle();
					extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
					finishTest(extras);
				}
			
			
		}
		else return;
		
		operation = new FileOperationNative(this, level, stretch);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
