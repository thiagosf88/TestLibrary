package edu.performance.test.nativo.stringoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class StringOperationNativeActivity extends PerformanceTestActivity {

	StringOperationNative operation;
	protected int level = -1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String searchable = "", snippets[] = { "" };

		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT)
					&& getIntent().hasExtra(PerformanceTestActivity.SEARCHABLE)
					&& getIntent().hasExtra(PerformanceTestActivity.SNIPPETS)) {
				searchable = getIntent().getExtras().getString(
						PerformanceTestActivity.SEARCHABLE);
				snippets = getIntent().getExtras().getStringArray(
						PerformanceTestActivity.SNIPPETS);
				level = getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT) < (snippets.length/3) ? getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT) : 0;
			} else {
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level, trechos pesquisáveis ou string base");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
			}
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			finish();
		}

		operation = new StringOperationNative(this, searchable, snippets);

	}

	@Override
	public void execute() {
		operation.execute();

	}

}
