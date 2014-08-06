package edu.performance.test.fileoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class FileOperationActivity extends PerformanceTestActivity {
	
	FileOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String filePath = "", stretch = "";
		if(getIntent().getExtras() != null){
			filePath = getIntent().getExtras().getString(Library.FILEPATH);
			stretch = getIntent().getExtras().getString(Library.STRETCH);
			
		}
		else return;
		operation = new FileOperation(this, filePath, stretch);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
