package edu.performance.test.stringoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.fileoperation.FileOperation;

public class StringOperationActivity extends PerformanceTestActivity {
	
	StringOperation operation;
	protected int level; //Deve ser 1 2 ou 3
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String searchable = null;
		String  snippets[] = {""};
		
		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT)
					&& getIntent().hasExtra(PerformanceTestActivity.SEARCHABLE)
					&& getIntent().hasExtra(PerformanceTestActivity.SNIPPETS)) {
				searchable = new FileOperation().testTJMreadSequentialAcessFile(getIntent().getExtras().getString(
						PerformanceTestActivity.SEARCHABLE));
				snippets = getIntent().getExtras().getStringArray(
						PerformanceTestActivity.SNIPPETS);
				level = getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT) < (snippets.length/3) ? getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT) : 2;
			} else {
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level, trechos pesquisáveis ou string base!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
			}
		}
		else{
			Bundle extras = new Bundle();			
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
		}
		
		operation = new StringOperation(this, searchable, snippets, level);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
