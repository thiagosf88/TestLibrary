package edu.performance.test.downloadoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;

public class DownloadOperationActivity extends InternetPerformanceTestActivity {
	
	DownloadOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new DownloadOperation(this);
		
	}
	

	@Override
	public void execute() {
		super.execute();
		operation.execute();
		

	}

}
