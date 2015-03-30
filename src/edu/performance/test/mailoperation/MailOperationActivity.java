package edu.performance.test.mailoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.PerformanceTestActivity;

public class MailOperationActivity extends InternetPerformanceTestActivity {
	
	MailOperation operation;
	protected String levelAttach, destination = "", messageText = "";
	public static final String DESTINATION = "DESTINATION";
	public static final String NOTIFY = "NOTIFY";
	/**
	 * Indica se será mostrada notificação enquando envia e-mail.
	 */
	protected boolean notify = false;
protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(DESTINATION) && !getIntent().getExtras().getString(DESTINATION).trim().isEmpty()){
				
			levelAttach = getIntent().hasExtra(PerformanceTestActivity.LEVEL_FILENAME) ? getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_FILENAME) : null;
			messageText = getIntent().hasExtra(PerformanceTestActivity.LEVEL_STR) ? getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_STR) : "Email super curto. :)";
			notify = getIntent().hasExtra(NOTIFY) ? getIntent().getExtras().getBoolean(NOTIFY) : false;
			destination = getIntent().getExtras().getString(DESTINATION);
			}
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: destino!");
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
		
		operation = new MailOperation(this, levelAttach, destination, messageText);
	}
	
	

	public boolean isNotify() {
		return notify;
	}



	@Override
	public void execute() {
		super.execute();
		operation.execute();
		
	}

}
