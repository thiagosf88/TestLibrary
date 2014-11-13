package edu.performance.test.graphicoperation.threed;

import edu.performance.test.graphicoperation.Operation3d;
import edu.performance.test.graphicoperation.ThreeDActivity;
import android.os.Bundle;

public class CubeTextureGL10Activity extends ThreeDActivity {
	
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
