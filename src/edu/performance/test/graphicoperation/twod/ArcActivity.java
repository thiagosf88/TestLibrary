package edu.performance.test.graphicoperation.twod;

import android.os.Bundle;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.TwoDActivity;

public class ArcActivity extends TwoDActivity {
	
	ArcOperation operation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.arc);
		
		operation = new ArcOperation(this, null);
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}




	
	


}
