package edu.performance.test.locationoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class GPSActivity extends PerformanceTestActivity {

	GpsOperation operation;

	protected int level = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(Library.LEVEL_INT)) {
				level = getIntent().getExtras().getInt(Library.LEVEL_INT);
			} else {
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				extras.putString(Library.ERROR_MESSAGE, "O nível não foi fornecido!");
				finishTest(extras);
				return;
			}
		} else {
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			extras.putString(Library.ERROR_MESSAGE, "Não foi possível obter os parâmetros necessários. O método getExtras retornou null!");
			finishTest(extras);
			finish();
		}

		operation = new GpsOperation(level, this);

	}

	@Override
	public void execute() {

		operation.execute();
	}

}
