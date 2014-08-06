package edu.performance.test.graphicoperation;

import android.os.Bundle;
import android.view.Menu;

public class TeapotActivity extends ThreeDActivity {
	
	Operation3d operation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		
		operation = new Operation3d(this);
		
		
	}
	
	public static final int ACCEL_ID = Menu.FIRST;
	
	protected int getMAX_TIME_MS(){
		return super.getMAX_TIME_MS();
	}

}
