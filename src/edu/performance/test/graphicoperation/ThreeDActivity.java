package edu.performance.test.graphicoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public abstract class ThreeDActivity extends PerformanceTestActivity {

	protected int level;
	
	Operation3d view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getExtras() != null){
			
			level = getIntent().getExtras().getInt(Library.LEVEL_INT);
		}
		view = new Operation3d(this);
		setContentView(view);
	}
	@Override
	protected void onPause() {
		super.onPause();
		//view.onPause();
	}
	@Override
	protected void onResume() {
		super.onResume();
		//view.onResume();
	}
	@Override
	public void execute() {
		
		
	}
	
	public int getLevel(){
		return this.level;
	}
	
	protected int getMAX_TIME_MS(){
		return super.getMAX_TIME_MS();
	}
	
	public boolean isTheLast() {
		return super.isTheLast();
		
	}
}
