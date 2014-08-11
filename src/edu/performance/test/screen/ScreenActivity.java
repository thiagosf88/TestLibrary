package edu.performance.test.screen;

import android.os.Bundle;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.DrawActivity;

public class ScreenActivity extends DrawActivity {
	
	ScreenOperation co;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		execute();
		
	}

	@Override
	public void execute() {
		
		setContentView(R.layout.screen);
		
		co = new ScreenOperation(this, null);
		
	}




}
