package edu.performance.test.graphicoperation.twod;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.TwoDActivity;

public class TextActivity extends TwoDActivity {
	
	TextOperation operation;
	String [] texts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(PerformanceTestActivity.LEVEL_STR_ARR))		
			texts = getIntent().getExtras().getStringArray(PerformanceTestActivity.LEVEL_STR_ARR);
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: textos a serem desenhados!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				setResult(RESULT_CANCELED);
				finish();
			}
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
		}
		System.out.println("texts: " + texts);
		setContentView(R.layout.text);
		operation = new TextOperation(this, null);
		
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	public String[] getTexts() {
		return texts;
	}


}
