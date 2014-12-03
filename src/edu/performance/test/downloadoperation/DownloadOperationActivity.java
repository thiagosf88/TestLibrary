package edu.performance.test.downloadoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.PerformanceTestActivity;

public class DownloadOperationActivity extends InternetPerformanceTestActivity {

	DownloadOperation operation;
	protected int level = 2;
	private String downloadLocation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(PerformanceTestActivity.LEVEL_INT)
					&& getIntent().hasExtra(PerformanceTestActivity.FILELOCATION)) {
				level = getIntent().getExtras().getInt(PerformanceTestActivity.LEVEL_INT);
				downloadLocation = getIntent().getExtras().getString(PerformanceTestActivity.FILELOCATION);
			}

			else {
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level ou downloadLocation!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				return;
			}
		} else {
			Bundle extras = new Bundle();
			extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos extras!");
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			finish();
		}

		operation = new DownloadOperation(this, level);

	}

	@Override
	public void execute() {
		super.execute();
		operation.execute();

	}

	protected String getDownloadLocation() {
		return downloadLocation;
	}
	
	

}
