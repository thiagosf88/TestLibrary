package edu.performance.test.graphicoperation.twod;

import android.os.Bundle;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.TwoDActivity;

public class RectangleActivity extends TwoDActivity {
	
	RectangleOperation operation;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rect);
		
		operation = new RectangleOperation(this, null);
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}




}