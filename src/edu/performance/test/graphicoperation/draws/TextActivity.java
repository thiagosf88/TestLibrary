package edu.performance.test.graphicoperation.draws;

import android.os.Bundle;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.DrawActivity;

public class TextActivity extends DrawActivity {
	
	TextOperation operation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text);
		operation = new TextOperation(this, null);
		
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}


}
