package edu.performance.test.mailoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;

public class MailOperationActivity extends InternetPerformanceTestActivity {
	
	MailOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		operation = new MailOperation(this);
		
	}
	

	@Override
	public void execute() {
		super.execute();
		operation.execute();
		
	}

}
