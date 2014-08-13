package edu.performance.test.batterytest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;
import edu.performance.test.fileoperation.FileOperation;
import edu.performance.test.graphicoperation.draws.ArcActivity;
import edu.performance.test.util.WriteNeededFiles;

public class BatteryOperation extends Activity {

	private double MIN_LEVEL_TO_EXIT = 100;
	private double batteryPreviousLevel = 100;
	private double firstVoltage = 0;
	int times = 0;
	Intent batteryIntent = null;
	boolean isTheLast;
	//private TextView status;
	PerformanceTestInterface test;
	Intent aTest;

	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent intent) {
			
			int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
			
			batteryPreviousLevel = level;			
			
			createInformationFile(intent);

		}
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getIntent().getExtras() != null) {
			isTheLast = getIntent().getExtras().getBoolean(Library.THELASTTEST);

		}
		// setContentView(R.layout.performance_test);

		// status = (TextView) findViewById(R.id.status);
		// status.setText("Testing battery...");
		// System.out.println(batteryPreviousLevel + " ? " + MIN_LEVEL_TO_EXIT);
		batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		int rawlevel = batteryIntent.getIntExtra("level", -1);
		double scale = batteryIntent.getIntExtra("scale", -1);
		firstVoltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
		if (rawlevel >= 0 && scale > 0) {
			batteryPreviousLevel = rawlevel;// / scale;
			MIN_LEVEL_TO_EXIT = batteryPreviousLevel - 1;
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
		}
		else {

		aTest = new Intent(getApplicationContext(), ArcActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(Library.THELASTTEST, true);
		aTest.putExtra(Library.LEVEL_INT, 5);
		
		
		// aTest.putExtra(Library.BATTERYTEST, true);

		startActivityForResult(aTest, 1);
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

			if (batteryPreviousLevel > MIN_LEVEL_TO_EXIT) {
				startActivityForResult(aTest, 1);
				times++;
			} else {
				
				batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
						new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
				
				createInformationFile(batteryIntent);				
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
	
	public void createInformationFile(Intent intent){
		batteryIntent = this.registerReceiver(this.mBatInfoReceiver,
				new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

		
		String batt = "";
		int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
		int icon_small = intent.getIntExtra(
				BatteryManager.EXTRA_ICON_SMALL, 0);
		int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
		int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		boolean present = intent.getExtras().getBoolean(
				BatteryManager.EXTRA_PRESENT);
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
		int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
		String technology = intent.getExtras().getString(
				BatteryManager.EXTRA_TECHNOLOGY);
		int temperature = intent.getIntExtra(
				BatteryManager.EXTRA_TEMPERATURE, 0);
		int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

		// if(ant > level){
		batt = "Health: " + health + "\n" + "Icon Small:" + icon_small
				+ "\n" + "Level: " + level + "\n" + "Plugged: " + plugged
				+ "\n" + "Present: " + present + "\n" + "Scale: " + scale
				+ "\n" + "Status: " + status + "\n" + "Technology: "
				+ technology + "\n" + "Temperature: " + temperature + "\n"
				+ "Diff Voltage: " + (firstVoltage - voltage) + "\n" + "ant: "
				+ batteryPreviousLevel + " times: " + times;


		FileOperation rw = new FileOperation();

		rw.testTJMwriteSequentialFile(WriteNeededFiles.REPORT_DIRECTORY_NAME + "/batt_" + level
						+ ".txt", batt);
	}

}
