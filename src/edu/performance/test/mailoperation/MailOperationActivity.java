package edu.performance.test.mailoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class MailOperationActivity extends InternetPerformanceTestActivity {
	
	MailOperation operation;
	protected String level;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_FILENAME))		
			level = getIntent().getExtras().getString(Library.LEVEL_FILENAME);
			else{
				Bundle extras = new Bundle();
				extras.putString(Library.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: arquivoAnexo!");
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
		
		operation = new MailOperation(this, level);
		
	}
	

	@Override
	public void execute() {
		super.execute();
		operation.execute();
		
	}

}
