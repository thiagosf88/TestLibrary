package edu.performance.test.stringoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class StringOperationActivity extends PerformanceTestActivity {
	
	StringOperation operation;
	protected int level;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String searchable = "",  snippets[] = {""};
		
		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(Library.LEVEL_INT)
					&& getIntent().hasExtra(Library.SEARCHABLE)
					&& getIntent().hasExtra(Library.SNIPPETS)) {
				searchable = getIntent().getExtras().getString(
						Library.SEARCHABLE);
				snippets = getIntent().getExtras().getStringArray(
						Library.SNIPPETS);
				level = getIntent().getExtras().getInt(Library.LEVEL_INT) < (snippets.length/3) ? getIntent().getExtras().getInt(Library.LEVEL_INT) : 0;
			} else {
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
			}
		}
		
		operation = new StringOperation(this, searchable, snippets);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
