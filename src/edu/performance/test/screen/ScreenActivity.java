package edu.performance.test.screen;

import android.os.Bundle;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.DrawActivity;

public class ScreenActivity extends DrawActivity {
	
	ScreenOperation co;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen);
		
		co = new ScreenOperation(this, null);
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}




}
