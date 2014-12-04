package edu.performance.test.mailoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.PerformanceTestActivity;

public class MailOperationActivity extends InternetPerformanceTestActivity {
	
	MailOperation operation;
	protected String level, destination;
	public static String DESTINATION = "DESTINATION";
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_FILENAME) && getIntent().hasExtra(DESTINATION)){		
			level = getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_FILENAME);
			destination = getIntent().getExtras().getString(DESTINATION);
			}
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: arquivoAnexo ou destino!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				finish();
			}
		}
		else{
			Bundle extras = new Bundle();
			
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			finish();
		}
		
		operation = new MailOperation(this, level, destination);
		
	}
	

	@Override
	public void execute() {
		super.execute();
		operation.execute();
		
	}

}
