package edu.performance.test.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import edu.performance.test.Library;
import edu.performance.test.R;

public class MediumTestActivity extends Activity{
	TextView l1, l2, l3, l4, l5, l6;
	Button b1, b2, b3;
	EditText e1, e2, e3, e4;
	MultiAutoCompleteTextView m1;
	DatePicker dp1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	
	setContentView(R.layout.medium_test);
	
	l1 = (TextView)findViewById(R.id.textView1);
	l2 = (TextView)findViewById(R.id.textView2);
	l3 = (TextView)findViewById(R.id.textView3);
	l4 = (TextView)findViewById(R.id.textView4);
	l5 = (TextView)findViewById(R.id.textView5);
	l6 = (TextView)findViewById(R.id.textView6);
	
	e1 = (EditText)findViewById(R.id.editText1);
	e2 = (EditText)findViewById(R.id.editText2);
	e3 = (EditText)findViewById(R.id.editText3);
	e4 = (EditText)findViewById(R.id.editText4);
	
	b1 = (Button) findViewById(R.id.bt_start_test);
	b2 = (Button) findViewById(R.id.button2);
	b3 = (Button) findViewById(R.id.button3);
	
	dp1 = (DatePicker) findViewById(R.id.datePicker1);
	m1 = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
	
	Intent mIntent = new Intent();
	mIntent.putExtra(Library.THELASTTEST, false);
	setResult(RESULT_OK, mIntent);
	System.out.println("saindo. Is it the last? " + false);
	finish();
	}

}
