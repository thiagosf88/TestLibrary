package edu.performance.test.screenoperation;

import android.os.Bundle;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.TwoDActivity;

public class ScreenActivity extends TwoDActivity {
	
	ScreenOperation operation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.screen);
		
		operation = new ScreenOperation(this, null);
		
	}

	@Override
	public void execute() {
		
		
		
	}




}
