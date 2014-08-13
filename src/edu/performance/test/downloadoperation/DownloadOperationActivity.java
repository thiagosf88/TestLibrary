package edu.performance.test.downloadoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.Library;

public class DownloadOperationActivity extends InternetPerformanceTestActivity {
	
	DownloadOperation operation;
	protected int level = 2;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_INT))
				level = getIntent().getExtras().getInt(Library.LEVEL_INT);
		}
		
		operation = new DownloadOperation(this, level);
		
	}
	

	@Override
	public void execute() {
		super.execute();
		operation.execute();
		

	}

}
