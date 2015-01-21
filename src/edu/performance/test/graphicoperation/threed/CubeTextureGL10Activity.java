package edu.performance.test.graphicoperation.threed;

import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.graphicoperation.Operation3d;
import edu.performance.test.graphicoperation.ThreeDActivity;
import android.os.Bundle;

public class CubeTextureGL10Activity extends ThreeDActivity {
	
	Operation3d operation;
	
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
		
		operation = new Operation3d(this);
		
		
	}
	
	protected int getMAX_TIME_MS(){
		return super.getMAX_TIME_MS();
	}

}
