package edu.performance.test.gpsoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class GPSActivity extends PerformanceTestActivity {
	
	GpsOperation operation;
	
	protected int level = 1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
				
			level = getIntent().getExtras().getInt(Library.LEVEL_INT);
		}
		
		operation = new GpsOperation(level, this);
		
	}

	@Override
	public void execute() {
		
		operation.execute();
	}
	

}
