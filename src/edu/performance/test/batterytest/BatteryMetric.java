package edu.performance.test.batterytest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.SystemClock;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;
import edu.performance.test.R;
import edu.performance.test.TestsManager;
import edu.performance.test.filesequentialoperation.FileSequentialOperation;
import edu.performance.test.util.InternetController;
import edu.performance.test.util.WriteNeededFiles;

public class BatteryMetric extends Activity {

	private int countIntent = 0, BATTERY_VARIATION = 5, times = 0;
	private double MIN_LEVEL_TO_EXIT = 100, batteryPreviousLevel = 100;
	Intent batteryIntent = null;
	boolean isTheLast, isByTime, isTestIncompleted;
	ArrayList<Intent> testsToDo;
	Context appRef;
	PerformanceTestInterface test;
	Intent aTest;
	String[] rawResourceNames, snippets;
	String fileLocation = "";
	private long timeToTest= 0, timeToExit, currentTime = 0;
	
	
	public static final String BY_TIME = "BYTIME";
	public static final String TEST_TIME = "TESTTIME";
	public static final String TEST_VARIATION = "TESTVARIATION";
	
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {

			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

			batteryPreviousLevel = level;
			
			int chargePlug = intent.getIntExtra(
					BatteryManager.EXTRA_PLUGGED, -1);
			boolean isPlugged = (chargePlug == BatteryManager.BATTERY_PLUGGED_USB)
					|| (chargePlug == BatteryManager.BATTERY_PLUGGED_AC);

			// If the device is plugged no battery reduction will be perceived, so
			// this test is invalid and must be finished.

			if (isPlugged) {
				cancelTest("O dispositivo está sendo carregado o que impossibilita o teste de bateria. Desconecte-o e tente novamente");
			} 

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(PerformanceTestInterface.STRING_ARRAY)
					&& getIntent().hasExtra(PerformanceTestInterface.THELASTTEST)
					&& getIntent().hasExtra(PerformanceTestInterface.SNIPPETS)
					&& getIntent().hasExtra(PerformanceTestInterface.FILELOCATION)
					&& getIntent().hasExtra(BY_TIME)) {

				isTheLast = getIntent().getExtras().getBoolean(
						PerformanceTestInterface.THELASTTEST);
				rawResourceNames = getIntent().getExtras().getStringArray(
						PerformanceTestInterface.STRING_ARRAY);
				snippets = getIntent().getExtras().getStringArray(
						PerformanceTestInterface.SNIPPETS);
				fileLocation = getIntent().getExtras().getString(PerformanceTestInterface.FILELOCATION);
				
				isByTime = getIntent().getExtras().getBoolean(BY_TIME);
				if(isByTime){
					if(getIntent().hasExtra(TEST_TIME)){
						timeToTest = getIntent().getExtras().getLong(TEST_TIME);
					}
					else{
						cancelTest("Seria um teste de tempo de bateria mas o tempo não foi fornecido!");					
					}
				}
				else{
					if(getIntent().hasExtra(TEST_VARIATION)){
						BATTERY_VARIATION = getIntent().getExtras().getInt(TEST_VARIATION);						
						
					}
					else{
						cancelTest("Seria um teste de variação de bateria mas a variação não foi fornecida!");					
					}
				}
					
				
			}

			else {
				cancelTest("Algum dos parametros necessários não foram fornecidos.");
			}
		} else {
			cancelTest("Não foi possível obter os parâmetros necessários. O método getExtras retornou null!");
		}

		appRef = getApplicationContext();

		batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		if (!isByTime) {
			int rawlevel = batteryIntent.getIntExtra("level", -1);
			double scale = batteryIntent.getIntExtra("scale", -1);
			
			if (rawlevel >= 0 && scale > 0) {
				batteryPreviousLevel = rawlevel;
				MIN_LEVEL_TO_EXIT = batteryPreviousLevel - BATTERY_VARIATION;
				
				if(MIN_LEVEL_TO_EXIT < 0)
					cancelTest("A variação fornecida excede a carga existente na bateria");
			}
		}
		
		else {
			timeToExit = SystemClock.uptimeMillis() + timeToTest;
		}
		
		createInformationFile(batteryIntent, "start_" + appRef.getResources().getString(R.string.batteryFileName));		

			// Initializes the list of tests which will be performed.
			testsToDo = TestsManager.getTestList(appRef);
			

			executeTest(testsToDo.get(countIntent));
		

	}

	

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data == null) {
			unregisterReceiver(mBatInfoReceiver);
			Intent mIntent = new Intent();
			mIntent.putExtra(PerformanceTestInterface.THELASTTEST, isTheLast());
			setResult(RESULT_CANCELED, mIntent);
			finish();
		} else {
			Bundle results = data.getExtras();

			if (resultCode == RESULT_CANCELED) {
				// TODO This need a specific treatment when some test fails. To
				// create list with error messages of each test for example.
				System.out.println(testsToDo.get(countIntent).getStringExtra(
						PerformanceTestInterface.STATUS)
						+ " failed!!!! :(");
			}
			// Se o teste é por tempo verifico se o tempo já foi cumprido, se não verifico se a variação de bateria
			//esperada já foi cumprida.
			
			
			isTestIncompleted = isByTime ? (currentTime = SystemClock.uptimeMillis()) < timeToExit :  batteryPreviousLevel > MIN_LEVEL_TO_EXIT;
			
			if (isTestIncompleted) {
				//cada activity separadamente
				//executeTest(testsToDo.get(countIntent));
				if(results.getBoolean(PerformanceTestInterface.THELASTTEST)){
					countIntent = -1;
					times++;
				}
					
				executeTest(testsToDo.get(++countIntent));
			}
			else{
				
				if(!results.getBoolean(PerformanceTestInterface.THELASTTEST)){
					executeTest(testsToDo.get(++countIntent));
				}
				else{
				
				batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
						new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

				createInformationFile(
						batteryIntent,"end_" + appRef.getResources().getString(R.string.batteryFileName ));
				
				unregisterReceiver(mBatInfoReceiver);
				Intent mIntent = new Intent();
				mIntent.putExtra(PerformanceTestInterface.THELASTTEST, isTheLast());
				setResult(RESULT_OK, mIntent);
				finish();
				}
			}
			
				//cada activity separadamente
				//times++;
			/*} else if (requestCode == 1
					&& !results.getBoolean(PerformanceTestInterface.THELASTTEST)) {


				batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
						new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

				createInformationFile(
						batteryIntent,
						((testsToDo.get(countIntent).getStringExtra(
								PerformanceTestInterface.STATUS).substring(7)).replace(
								" skills..", "")).trim() + "test");
				
				MIN_LEVEL_TO_EXIT = batteryPreviousLevel - VARIATION;
				times = 0;
				executeTest(testsToDo.get(++countIntent));
			}
			if (results.getBoolean(PerformanceTestInterface.THELASTTEST)) {

				unregisterReceiver(mBatInfoReceiver);
				Intent mIntent = new Intent();
				mIntent.putExtra(PerformanceTestInterface.THELASTTEST, isTheLast());
				setResult(RESULT_OK, mIntent);
				finish();
			}*/
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
		batt = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n"
				+ "<batteryData \n\t\t\t xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n\t\t\t xsi:noNamespaceSchemaLocation=\"model.xsd\">\n"
				+ "\t<batteryVariation> \n"
				+ "\t\t<health>" + health + "</health>\n"
				+ "\t\t<iconSmall>" + icon_small + "</iconSmall>\n"
				+ "\t\t<level>" + level + "</level>\n"
				+ "\t\t<plugged>" + plugged + "</plugged>\n"
				+ "\t\t<present>" + present + "</present>\n"
				+ "\t\t<scale>" + scale + "</scale>\n"
				+ "\t\t<status>" + status + "</status>\n"
				+ "\t\t<technology>" + technology + "</technology>\n"
				+ "\t\t<temperature>" + temperature + "</temperature>\n"
				+ "\t\t<voltage>" + voltage + "</voltage>\n"
				+ "\t\t<previousLevel>"	+ batteryPreviousLevel + "</previousLevel>\n"
				+ (isByTime ? "\t\t<deltaTime>" + (currentTime - timeToExit) + "</deltaTime>\n" : "\t\t<executions>" + times + "</executions>\n")
				+ "\t</batteryVariation> \n"
				+ "</batteryData> \n";

		FileSequentialOperation rw = new FileSequentialOperation();

		rw.testTJMwriteSequentialFile(WriteNeededFiles.REPORT_DIRECTORY_NAME
				+ "/" + activity_name , batt);

		batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		batteryPreviousLevel = batteryIntent.getIntExtra(
				BatteryManager.EXTRA_LEVEL, 0);// / scale;

		
	}

	void executeTest(Intent test) {
		// Close Internet connection before execute tests.

		if (test.getExtras().getBoolean(PerformanceTestInterface.NETWORK_TEST)) {
			InternetController.setWifiAvailability(true, this);
		} else {
			InternetController.setWifiAvailability(false, this);
		}
		try {
			startActivityForResult(test, 1);
		} catch (Exception e) {
			FileSequentialOperation rw = new FileSequentialOperation();
			String message = (e.getMessage() != null)
					&& (!e.getMessage().trim().isEmpty()) ? e.getMessage()
					: "Exception without message!!!";
			rw.testTJMwriteSequentialFile(
					WriteNeededFiles.REPORT_DIRECTORY_NAME
							+ "/battTest_error_in"
							+ ((test.getStringExtra(PerformanceTestInterface.STATUS)
									.substring(7)).replace(" skills..", ""))
									.trim(), message);
			System.err.println(message);
		}
	}
	
	private void cancelTest(String errorMessage){
		Bundle extras = new Bundle();
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
		if(batteryIntent != null)
		unregisterReceiver(mBatInfoReceiver);
		Intent mIntent = new Intent();
		mIntent.putExtra(PerformanceTestInterface.ERROR_MESSAGE, errorMessage);
		mIntent.putExtra(PerformanceTestInterface.THELASTTEST, isTheLast());
		mIntent.putExtras(extras);
		setResult(RESULT_CANCELED, mIntent);
		finish();
		return;
	}

	

}
