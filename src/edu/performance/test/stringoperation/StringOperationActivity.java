package edu.performance.test.stringoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class StringOperationActivity extends PerformanceTestActivity {
	
	StringOperation operation;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String searchable = "",  snippets[] = {""};
		
		if(getIntent().getExtras() != null){
			
			searchable = getIntent().getExtras().getString(Library.SEARCHABLE);
			snippets = getIntent().getExtras().getStringArray(Library.SNIPPETS);
			
		}
		
		operation = new StringOperation(this, searchable, snippets);
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
