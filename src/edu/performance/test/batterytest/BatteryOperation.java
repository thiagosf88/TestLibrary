package edu.performance.test.batterytest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.Library.fileIndexes;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;
import edu.performance.test.R;
import edu.performance.test.database.DatabaseOperationActivity;
import edu.performance.test.fileoperation.FileOperation;
import edu.performance.test.fileoperation.FileOperationActivity;
import edu.performance.test.floatoperation.FloatOperationActivity;
import edu.performance.test.graphoperation.GraphOperationActivity;
import edu.performance.test.integeroperation.IntegerOperationActivity;
import edu.performance.test.mailoperation.MailOperationActivity;
import edu.performance.test.nativo.fileoperation.FileOperationNativeActivity;
import edu.performance.test.nativo.floatoperation.FloatOperationNativeActivity;
import edu.performance.test.nativo.integeroperation.IntegerOperationNativeActivity;
import edu.performance.test.nativo.stringoperation.StringOperationNativeActivity;
import edu.performance.test.screen.MediumTestActivity;
import edu.performance.test.stringoperation.StringOperationActivity;
import edu.performance.test.util.InternetController;
import edu.performance.test.util.WriteNeededFiles;
import edu.performance.test.weboperation.WebServiceActivity;

public class BatteryOperation extends Activity {

	private int countIntent = 0;
	private double MIN_LEVEL_TO_EXIT = 100;
	private double batteryPreviousLevel = 100;
	private double firstVoltage = 0;
	private int VARIATION = 1;
	
	int times = 0;
	Intent batteryIntent = null;
	boolean isTheLast;
	ArrayList<Intent> testsToDo;
	Context appRef;
	// private TextView status;
	PerformanceTestInterface test;
	Intent aTest;
	String[] rawResourceNames, snippets;

	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {

			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

			batteryPreviousLevel = level;

			createInformationFile(intent, "initial");

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(Library.STRING_ARRAY)
					&& getIntent().hasExtra(Library.THELASTTEST)
					&& getIntent().hasExtra(Library.SNIPPETS)) {

				isTheLast = getIntent().getExtras().getBoolean(
						Library.THELASTTEST);
				rawResourceNames = getIntent().getExtras().getStringArray(
						Library.STRING_ARRAY);
				snippets = getIntent().getExtras().getStringArray(
						Library.SNIPPETS);
			}

			else {
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				unregisterReceiver(mBatInfoReceiver);
				Intent mIntent = new Intent();
				mIntent.putExtra(Library.THELASTTEST, isTheLast());
				mIntent.putExtras(extras);
				setResult(RESULT_CANCELED, mIntent);
				finish();
				return;
			}
		} else {
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			unregisterReceiver(mBatInfoReceiver);
			Intent mIntent = new Intent();
			mIntent.putExtra(Library.THELASTTEST, isTheLast());
			mIntent.putExtras(extras);
			setResult(RESULT_CANCELED, mIntent);
			finish();
			return;
		}

		appRef = getApplicationContext();

		batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		int rawlevel = batteryIntent.getIntExtra("level", -1);
		double scale = batteryIntent.getIntExtra("scale", -1);
		firstVoltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,
				0);
		if (rawlevel >= 0 && scale > 0) {
			batteryPreviousLevel = rawlevel;// / scale;
			MIN_LEVEL_TO_EXIT = batteryPreviousLevel - VARIATION;
			// System.out.println(batteryPreviousLevel + " > " +
			// MIN_LEVEL_TO_EXIT);
		}
		int chargePlug = batteryIntent.getIntExtra(
				BatteryManager.EXTRA_PLUGGED, -1);
		boolean isPlugged = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB)
				|| (chargePlug == BatteryManager.BATTERY_PLUGGED_AC);

		// bt = new BatteryTest();
		// bt.execute(null, null, null);
		// liberar

		// If the device is plugged no battery reduction will be perceived, so
		// this test is
		// invalid and must be finished.
		
		if (isPlugged) {
			System.out
					.println("Test was cancelled because the device is plugged!");
			unregisterReceiver(mBatInfoReceiver);
			Intent errorIntent = new Intent();
			errorIntent.putExtra(Library.THELASTTEST, isTheLast());
			setResult(Activity.RESULT_CANCELED, errorIntent);
			finish();
		} else {

			// Initializes the list of tests which will be performed.
			testsToDo = new ArrayList<Intent>();
/* todo
			aTest = new Intent(appRef, WebServiceActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_WEBSITE,
					"http://mcupdate.tumblr.com/api/read?num=50");
			aTest.putExtra(Library.STATUS, "Testing WebService skills..");
			aTest.putExtra(Library.NETWORK_TEST, true);
			testsToDo.add(aTest);

			aTest = new Intent(appRef, DatabaseOperationActivity.class); 
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 200);
			aTest.putExtra(Library.STATUS, "Testing Database skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);

			aTest = new Intent(appRef, LightTestActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.STATUS, "Testing Screen skills..");
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);

			aTest = new Intent(appRef, MemoryOperationActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 500);
			aTest.putExtra(Library.STATUS, "Testing Memory skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);

			aTest = new Intent(appRef, WebOperationActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 0);
			aTest.putExtra(Library.STATUS, "Testing Web skills..");
			aTest.putExtra(Library.NETWORK_TEST, true);
			testsToDo.add(aTest);

			aTest = new Intent(appRef, IntegerOperationActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 100);
			aTest.putExtra(Library.STATUS, "Testing Integer skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);

			aTest = new Intent(appRef, StringOperationActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 10);
			aTest.putExtra(Library.NETWORK_TEST, false);
			aTest.putExtra(Library.
					SEARCHABLE,
					new FileOperation()
							.testTJMreadSequentialAcessFile(WriteNeededFiles.DIRECTORY_NAME
									+ "/"
									+ rawResourceNames[fileIndexes.SMALL_TXT]));
			aTest.putExtra(Library.SNIPPETS, snippets);
			aTest.putExtra(Library.STATUS, "Testing String skills..");
			testsToDo.add(aTest);

			aTest = new Intent(appRef, MailOperationActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_FILENAME,
					WriteNeededFiles.DIRECTORY_NAME + "/"
							+ rawResourceNames[fileIndexes.TINY_G_TXT]);
			aTest.putExtra(Library.STATUS, "Testing Mail skills..");
			aTest.putExtra(Library.NETWORK_TEST, true);
			testsToDo.add(aTest);

			aTest = new Intent(appRef, FileOperationActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, true);
			aTest.putExtra(Library.LEVEL_INT, 1);
			aTest.putExtra(Library.LEVEL_FILENAME,
					WriteNeededFiles.DIRECTORY_NAME + "/"
							+ rawResourceNames[fileIndexes.SMALL_TXT]);
			aTest.putExtra(Library.STRETCH,
					appRef.getResources().getString(R.string.stretch));
			aTest.putExtra(Library.STATUS, "Testing File skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);*/
			
			/* browser
			aTest = new Intent(appRef, DownloadOperationActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 240000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 2);
			aTest.putExtra(Library.STATUS, "Testing Download skills..\n Max time (4 min)");
			aTest.putExtra(Library.NETWORK_TEST, true);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, WebServiceActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_WEBSITE, "http://mcupdate.tumblr.com/api/read");
			aTest.putExtra(Library.STATUS, "Testing WebService skills..");
			aTest.putExtra(Library.NETWORK_TEST, true);
			testsToDo.add(aTest);
			
			
			aTest = new Intent(appRef, DatabaseOperationActivity.class); 
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 50);
			aTest.putExtra(Library.STATUS, "Testing Database skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, WebOperationActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 3);
			aTest.putExtra(Library.STATUS, "Testing Web skills..");
			aTest.putExtra(Library.NETWORK_TEST, true);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, MemoryOperationActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 1500);
			aTest.putExtra(Library.STATUS, "Testing Memory skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, MemoryOperationNativeActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 1500);
			aTest.putExtra(Library.STATUS, "Testing Native Memory skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
				
			aTest = new Intent(appRef, MediumTestActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.STATUS, "Testing Screen skills..");
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);				
			
			
			//------------------------CPU------------------------------------
			aTest = new Intent(appRef, IntegerOperationActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 555);
			aTest.putExtra(Library.STATUS, "Testing Integer skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, IntegerOperationNativeActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 555);
			aTest.putExtra(Library.STATUS, "Testing Integer Native skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, FloatOperationActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_DOUBLE, 9979);
			aTest.putExtra(Library.STATUS, "Testing Float skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, FloatOperationNativeActivity.class); 
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.STATUS, "Testing Native Float skills..");
			aTest.putExtra(Library.LEVEL_DOUBLE, 9979);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, GraphOperationActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_FILENAME, WriteNeededFiles.DIRECTORY_NAME + "/" + rawResourceNames[fileIndexes.MEDIUM2_G_TXT]);
			aTest.putExtra(Library.STATUS, "Testing Graph skills...");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, StringOperationActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 10);
			aTest.putExtra(Library.SEARCHABLE, new FileOperation()
			.testTJMreadSequentialAcessFile(
					WriteNeededFiles.DIRECTORY_NAME + "/"
							+ rawResourceNames[fileIndexes.SMALL_TXT]));
			aTest.putExtra(Library.SNIPPETS, snippets);	
			aTest.putExtra(Library.STATUS, "Testing String skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, StringOperationNativeActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 10);
			aTest.putExtra(Library.SEARCHABLE, new FileOperation()
			.testTJMreadSequentialAcessFile(
					WriteNeededFiles.DIRECTORY_NAME + "/"
							+ rawResourceNames[fileIndexes.SMALL_TXT]));
			aTest.putExtra(Library.SNIPPETS, snippets);	
			aTest.putExtra(Library.STATUS, "Testing String skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			//-----------------------------------------------------------------------------------------------------------------------
			//-------------------------GPU-------------------------------------------------------------------------------------------
			
			aTest = new Intent(appRef, CubeActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 10);
			aTest.putExtra(Library.STATUS, "Testing 3D skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, LessonThreeActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 0);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);		

			aTest = new Intent(appRef, ArcActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 1);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, CircleActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 10);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, ImageActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 3);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, RectangleActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 10);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, TextActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 10);
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			//--------------------------------------------------------------------------------------------------------------
			
			
			
			aTest = new Intent(appRef, MailOperationActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_FILENAME, WriteNeededFiles.DIRECTORY_NAME + "/" +rawResourceNames[fileIndexes.MEDIUM2_G_TXT]);
			aTest.putExtra(Library.STATUS, "Testing Mail skills..");
			aTest.putExtra(Library.NETWORK_TEST, true);
			testsToDo.add(aTest);	
			
			aTest = new Intent(appRef, GPSActivity.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 3);
			aTest.putExtra(Library.STATUS, "Testing GPS skills..");
			aTest.putExtra(Library.NETWORK_TEST, true);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, FileOperationActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 3);
			aTest.putExtra(Library.LEVEL_FILENAME, WriteNeededFiles.DIRECTORY_NAME + "/"
					+ rawResourceNames[fileIndexes.BIG_TXT]);
			aTest.putExtra(Library.STRETCH, appRef.getResources().getString(R.string.stretch));
			aTest.putExtra(Library.STATUS, "Testing File skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			
			aTest = new Intent(appRef, FileOperationNativeActivity.class); 
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
			aTest.putExtra(Library.THELASTTEST, true);
			aTest.putExtra(Library.LEVEL_INT, 3);
			aTest.putExtra(Library.LEVEL_FILENAME, WriteNeededFiles.DIRECTORY_NAME + "/"
					+ rawResourceNames[fileIndexes.BIG_TXT]);
			aTest.putExtra(Library.STRETCH, appRef.getResources().getString(R.string.stretch));
			aTest.putExtra(Library.STATUS, "Testing Native File skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);

			// aTest.putExtra(Library.BATTERYTEST, true);*/
/* Sheets*/
			aTest = new Intent(appRef, DatabaseOperationActivity.class); 
			aTest.putExtra(Library.THELASTTEST, false);
			aTest.putExtra(Library.LEVEL_INT, 1000);
			aTest.putExtra(Library.STATUS, "Testing Database skills..");
			aTest.putExtra(Library.NETWORK_TEST, false);
			testsToDo.add(aTest);
			//------------------------CPU------------------------------------
					aTest = new Intent(appRef, IntegerOperationActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_INT, 555);
					aTest.putExtra(Library.STATUS, "Testing Integer skills..");
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, IntegerOperationNativeActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_INT, 555);
					aTest.putExtra(Library.STATUS, "Testing Integer Native skills..");
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, FloatOperationActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_DOUBLE, 9979);
					aTest.putExtra(Library.STATUS, "Testing Float skills..");
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, FloatOperationNativeActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.STATUS, "Testing Native Float skills..");
					aTest.putExtra(Library.LEVEL_DOUBLE, 9979);
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, GraphOperationActivity.class);
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_FILENAME, WriteNeededFiles.DIRECTORY_NAME + "/" + rawResourceNames[fileIndexes.MEDIUM3_G_TXT]);
					aTest.putExtra(Library.STATUS, "Testing Graph skills...");
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, StringOperationActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_INT, 10);
					aTest.putExtra(Library.SEARCHABLE, new FileOperation()
					.testTJMreadSequentialAcessFile(
							WriteNeededFiles.DIRECTORY_NAME + "/"
									+ rawResourceNames[fileIndexes.MEDIUM_TXT]));
					aTest.putExtra(Library.SNIPPETS, snippets);	
					aTest.putExtra(Library.STATUS, "Testing String skills..");
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, StringOperationNativeActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_INT, 10);
					aTest.putExtra(Library.SEARCHABLE, new FileOperation()
					.testTJMreadSequentialAcessFile(
							WriteNeededFiles.DIRECTORY_NAME + "/"
									+ rawResourceNames[fileIndexes.MEDIUM_TXT]));
					aTest.putExtra(Library.SNIPPETS, snippets);	
					aTest.putExtra(Library.STATUS, "Testing String skills..");
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					//-----------------------------------------------------------------------------------------------------------------------
					
					aTest = new Intent(appRef, MediumTestActivity.class);
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.STATUS, "Testing Screen skills..");
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, FileOperationActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_INT, 3);
					aTest.putExtra(Library.LEVEL_FILENAME, WriteNeededFiles.DIRECTORY_NAME + "/"
							+ rawResourceNames[fileIndexes.MEDIUM_TXT]);
					aTest.putExtra(Library.STRETCH, appRef.getResources().getString(R.string.stretch));
					aTest.putExtra(Library.STATUS, "Testing File skills..");
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, FileOperationNativeActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_INT, 3);
					aTest.putExtra(Library.LEVEL_FILENAME, WriteNeededFiles.DIRECTORY_NAME + "/"
							+ rawResourceNames[fileIndexes.MEDIUM_TXT]);
					aTest.putExtra(Library.STRETCH, appRef.getResources().getString(R.string.stretch));
					aTest.putExtra(Library.STATUS, "Testing Native File skills..");
					aTest.putExtra(Library.NETWORK_TEST, false);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, MailOperationActivity.class); 
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, false);
					aTest.putExtra(Library.LEVEL_FILENAME, WriteNeededFiles.DIRECTORY_NAME + "/" +rawResourceNames[fileIndexes.TINY_G_TXT]);
					aTest.putExtra(Library.STATUS, "Testing Mail skills..");
					aTest.putExtra(Library.NETWORK_TEST, true);
					testsToDo.add(aTest);
					
					aTest = new Intent(appRef, WebServiceActivity.class);
					aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
					aTest.putExtra(Library.THELASTTEST, true);
					aTest.putExtra(Library.LEVEL_WEBSITE, "http://mcupdate.tumblr.com/api/read?num=10");
					aTest.putExtra(Library.STATUS, "Testing WebService skills..");
					aTest.putExtra(Library.NETWORK_TEST, true);
					testsToDo.add(aTest);
			
			executeTest(testsToDo.get(countIntent));
		}

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data == null) {
			unregisterReceiver(mBatInfoReceiver);
			Intent mIntent = new Intent();
			mIntent.putExtra(Library.THELASTTEST, isTheLast());
			setResult(RESULT_CANCELED, mIntent);
			finish();
		} else {
			Bundle results = data.getExtras();

			if (resultCode == RESULT_CANCELED) {
				// TODO This need a specific treatment when some test fails. To
				// create list with error messages of each test for example.
				System.out.println(testsToDo.get(countIntent).getStringExtra(
						Library.STATUS)
						+ " failed!!!! :(");
			}

			if (batteryPreviousLevel > MIN_LEVEL_TO_EXIT) {
				executeTest(testsToDo.get(countIntent));
				times++;
			} else if (requestCode == 1
					&& !results.getBoolean(Library.THELASTTEST)) {
				batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
						new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

				createInformationFile(batteryIntent, (testsToDo.get(countIntent).getStringExtra(Library.STATUS).substring(7)).replace(" skills..", ""));
				executeTest(testsToDo.get(++countIntent));
			} else {

				unregisterReceiver(mBatInfoReceiver);
				Intent mIntent = new Intent();
				mIntent.putExtra(Library.THELASTTEST, isTheLast());
				setResult(RESULT_OK, mIntent);
				finish();
			}
		}

	}

	public PerformanceTestInterface getTest() {
		return test;
	}

	public void setTest(PerformanceTestInterface test) {
		this.test = test;
	}

	public boolean isTheLast() {
		return isTheLast;
	}

	public void createInformationFile(Intent intent, String activity_name) {
		

		String batt = "";
		int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
		int icon_small = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean present = intent.getExtras().getBoolean(
				BatteryManager.EXTRA_PRESENT);
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
		int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
		String technology = intent.getExtras().getString(
				BatteryManager.EXTRA_TECHNOLOGY);
		int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,
				0);
		int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

		// if(ant > level){
		batt = "Health: " + health + "\n" + "Icon Small:" + icon_small + "\n"
				+ "Level: " + level + "\n" + "Plugged: " + plugged + "\n"
				+ "Present: " + present + "\n" + "Scale: " + scale + "\n"
				+ "Status: " + status + "\n" + "Technology: " + technology
				+ "\n" + "Temperature: " + temperature + "\n"
				+ "Diff Voltage: " + (firstVoltage - voltage) + "\n" + "ant: "
				+ batteryPreviousLevel + " times: " + times;

		FileOperation rw = new FileOperation();

		rw.testTJMwriteSequentialFile(WriteNeededFiles.REPORT_DIRECTORY_NAME
				+ "/batt_" + activity_name + ".txt",
				batt);
		
		batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		
		batteryPreviousLevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);// / scale;
		MIN_LEVEL_TO_EXIT = batteryPreviousLevel -  VARIATION;
		firstVoltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
	}

	void executeTest(Intent test) {
		// Close Internet connection before execute tests.

		if (test.getExtras().getBoolean(Library.NETWORK_TEST)) {
			InternetController.setWifiAvailability(true, this);
		} else{
			InternetController.setWifiAvailability(false, this);
		}
		startActivityForResult(test, 1);
	}

}
