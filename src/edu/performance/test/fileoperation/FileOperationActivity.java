package edu.performance.test.fileoperation;

import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class FileOperationActivity extends PerformanceTestActivity {
	
	FileOperation operation;
	protected int level;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String filePath = "", stretch = "";
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_FILENAME)
					&& getIntent().hasExtra(Library.STRETCH)
					&& getIntent().hasExtra(Library.LEVEL_INT)){
			filePath = getIntent().getExtras().getString(Library.LEVEL_FILENAME);
			stretch = getIntent().getExtras().getString(Library.STRETCH);
			
			level = getIntent().getExtras().getInt(Library.LEVEL_INT);
		}
			else{
				Bundle extras = new Bundle();
				extras.putString(Library.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level, filename ou stretch!");
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
		
			status.setText(message);
			operation = new FileOperation(this, filePath, stretch, level);
		
		
		
	}
	

	@Override
	public void execute() {
		operation.execute();

	}

}
