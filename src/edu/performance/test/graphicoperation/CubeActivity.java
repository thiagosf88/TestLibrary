package edu.performance.test.graphicoperation;

import android.os.Bundle;

public class CubeActivity extends ThreeDActivity {
	
	Operation3d operation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		
		operation = new Operation3d(this);
		
		
	}
	
	protected int getMAX_TIME_MS(){
		return super.getMAX_TIME_MS();
	}

}
