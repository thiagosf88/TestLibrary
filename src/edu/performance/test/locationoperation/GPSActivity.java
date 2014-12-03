package edu.performance.test.locationoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;

public class GPSActivity extends PerformanceTestActivity {

	GpsOperation operation;

	protected int level = 1;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT)) {
				level = getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT);
			} else {
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "O nível não foi fornecido!");
				finishTest(extras);
				return;
			}
		} else {
			Bundle extras = new Bundle();
			extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Os extras não foram fornecidos!");
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
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
