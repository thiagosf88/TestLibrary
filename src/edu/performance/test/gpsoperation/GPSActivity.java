package edu.performance.test.gpsoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class GPSActivity extends PerformanceTestActivity {
	
	GpsOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		operation = new GpsOperation(this);
		
	}

	@Override
	public void execute() {
		
		operation.execute();
	}
	

}
