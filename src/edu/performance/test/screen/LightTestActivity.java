package edu.performance.test.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.performance.test.Library;
import edu.performance.test.R;

public class LightTestActivity extends Activity {
	
	TextView l1, l2, l3;
	Button b1;
	EditText e1, e2, e3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.light_test);
		
		l1 = (TextView)findViewById(R.id.textView1);
		l2 = (TextView)findViewById(R.id.textView2);
		l3 = (TextView)findViewById(R.id.textView3);
		
		e1 = (EditText)findViewById(R.id.editText1);
		e2 = (EditText)findViewById(R.id.editText2);
		e3 = (EditText)findViewById(R.id.editText3);
		
		b1 = (Button) findViewById(R.id.bt_start_test);
		
		Intent mIntent = new Intent();
		mIntent.putExtra(Library.THELASTTEST, false);
		setResult(RESULT_OK, mIntent);
		System.out.println("saindo. Is it the last? " + false);
		finish();
		
	}

}
