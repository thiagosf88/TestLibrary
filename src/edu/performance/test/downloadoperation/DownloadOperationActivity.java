package edu.performance.test.downloadoperation;

import android.os.Bundle;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class DownloadOperationActivity extends InternetPerformanceTestActivity {

	DownloadOperation operation;
	protected int level = 2;
	private String downloadLocation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(Library.LEVEL_INT)) {
				level = getIntent().getExtras().getInt(Library.LEVEL_INT);
				downloadLocation = getIntent().getExtras().getString(Library.FILELOCATION);
			}

			else {
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				return;
			}
		} else {
			Bundle extras = new Bundle();
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
