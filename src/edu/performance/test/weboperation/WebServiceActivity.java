package edu.performance.test.weboperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class WebServiceActivity extends  InternetPerformanceTestActivity{

	String level = "http://mcupdate.tumblr.com/api/read";

	WebServiceOperation operation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {			
			
			if(getIntent().hasExtra(Library.LEVEL_WEBSITE) && getIntent().hasExtra(
					Library.THELASTTEST)){		
				level = getIntent().getExtras().getString(Library.LEVEL_WEBSITE);
				
				setTheLast(getIntent().getExtras().getBoolean(
						Library.THELASTTEST));
			}
				else{
					Bundle extras = new Bundle();
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
