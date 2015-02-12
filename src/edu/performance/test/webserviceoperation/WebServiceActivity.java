package edu.performance.test.webserviceoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.PerformanceTestActivity;

public class WebServiceActivity extends  InternetPerformanceTestActivity{

	String level = "http://mcupdate.tumblr.com/api/read";

	WebServiceOperation operation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {			
			
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_WEBSITE) && getIntent().hasExtra(
					PerformanceTestActivity.THELASTTEST)){		
				level = getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_WEBSITE);
				
				setTheLast(getIntent().getExtras().getBoolean(
						PerformanceTestActivity.THELASTTEST));
			}
				else{
					Bundle extras = new Bundle();
					extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: endereço web service!");
					extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
					finishTest(extras);
					setResult(RESULT_CANCELED);
					finish();
					return;
				}
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
			return;
		}


		operation = new WebServiceOperation(this, level);
	}
	
	
	public void execute(){
		super.execute();
		operation.execute();
			
		
		 
	}


	
	protected int getMAX_TIME_MS() {
		return super.getMAX_TIME_MS();
	}
	
	

}
