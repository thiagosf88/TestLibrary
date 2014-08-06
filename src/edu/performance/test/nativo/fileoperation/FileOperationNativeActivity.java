package edu.performance.test.nativo.fileoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class FileOperationNativeActivity extends PerformanceTestActivity {
	
	FileOperationNative operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String filePath = "", stretch = "";
		if(getIntent().getExtras() != null){
			filePath = getIntent().getExtras().getString(Library.FILEPATH);
			stretch = getIntent().getExtras().getString(Library.STRETCH);
			
		}
		else return;
		
		operation = new FileOperationNative(this, filePath, stretch);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
