package edu.performance.test.graphicoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public abstract class ThreeDActivity extends PerformanceTestActivity {

	protected int level;
	
	Operation3d view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT))
			level = getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT);
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				setResult(RESULT_CANCELED);
				finish();
			}
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
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
