package edu.performance.test.graphoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class GraphOperationActivity extends PerformanceTestActivity {
	
	GraphOperation operation;
	
	protected int level;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getExtras() != null){
					
			level = getIntent().getExtras().getInt(Library.LEVEL_INT);
		}
		operation = new GraphOperation(level, this);
		
	}

	@Override
	public void execute() {
		
		operation.execute();
		
	}

}
