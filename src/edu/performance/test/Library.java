package edu.performance.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import edu.performance.test.batterytest.BatteryMetric;
import edu.performance.test.fileoperation.FileOperation;
import edu.performance.test.util.InternetController;
import edu.performance.test.util.WriteNeededFiles;


/**
 * This class is a Main activity of the Library Performance Test.
 * 
 * @author Thiago
 * 
 */
public class Library extends Activity {

	private int countIntent = 0;
	private List<Intent> testsToDo;
	
	private static String fileLocation = "";
	public static class fileIndexes {
		public static final int BIG_TXT = 0, MEDIUM_TXT = BIG_TXT + 1,LISTAS_XML = MEDIUM_TXT + 1,
				TAREFAS_XML = LISTAS_XML + 1, SMALL_TXT = TAREFAS_XML + 1,
				TINY_G_TXT = SMALL_TXT + 1, MEDIUM_G_TXT = TINY_G_TXT + 1,
				MEDIUM2_G_TXT = MEDIUM_G_TXT + 1, MEDIUM3_G_TXT = MEDIUM2_G_TXT + 1;
	}

	PowerManager powerManager = null;
	WakeLock wakeLock = null;
	
	int[] rawResourceIds = { R.raw.big,R.raw.medium, R.raw.listas, R.raw.tarefas,
			R.raw.small, R.raw.tiny_g, R.raw.medium_g, R.raw.medium2_g, R.raw.medium3_g, R.raw.listadetestes};
	String[] rawResourceNames = { "big.txt", "medium.txt", "listas.xml", "tarefas.xml",
			"small.txt", "tiny_g.txt", "medium_g.txt", "medium2_g.txt", "medium3_g.txt", "listadetestes.xml" };
	HashMap<String, String> dirNames = null;
	Library appRef = null;
	public final static String DATA_FILE_NAME = WriteNeededFiles.REPORT_DIRECTORY_NAME + "/data_" + System.currentTimeMillis() + "_" + Build.MODEL + ".xml";

	Intent aTest;
	Button btStartTest;
	ToggleButton btBatteryTest;
	TextView status;
	//Needed to change logcat file
	@SuppressWarnings("unused")
	private BufferedReader bufferedReader;
	
	
	
	//It is necessary to load native code.
	static {
		System.loadLibrary("testLibrary");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

			// Sets the initial layout and binds the start button
			 setContentView(R.layout.initial);
			 btStartTest = (Button) findViewById(R.id.bt_start_test);
			 btBatteryTest = (ToggleButton) findViewById(R.id.bt_battery_test);
			 
			 btStartTest.setActivated(false);
			 btBatteryTest.setActivated(false);
			 
			 // Initializes the list of tests which will be performed. 
			 testsToDo = new ArrayList<Intent>();
			 
			System.out.println("Has PerformanceTestDir folder been deleted? "
					+ WriteNeededFiles.deleteFiles(appRef, rawResourceNames));

			// Putting all files in storage to be accessed.
			dirNames = WriteNeededFiles.makeDirectories(this);
			if (dirNames == null || dirNames.isEmpty())
				return;
			
			if(dirNames.get("PDE") != null && !dirNames.get("PDE").trim().isEmpty())
				fileLocation = dirNames.get("PDE");
			else if (dirNames.get("PDI") != null && !dirNames.get("PDI").trim().isEmpty())
				fileLocation = dirNames.get("PDI");
			else
				finish();

			for (int i = 0; i < rawResourceIds.length; i++) {
				if(dirNames.get("PDI") != null && !dirNames.get("PDI").trim().isEmpty())
				FileOperation.testTJMWriteFile(this, rawResourceIds[i],
						rawResourceNames[i], dirNames.get("PDI"));
				
				if(dirNames.get("PDE") != null && !dirNames.get("PDE").trim().isEmpty())
				FileOperation.testTJMWriteFile(this, rawResourceIds[i],
						rawResourceNames[i], dirNames.get("PDE"));
			}

			// WriteNeededFiles.putFilesOnStorage(this, rawResourceIds,
			// rawResourceNames, dirNames.get("PDI") );
			
			// It is responsible to change logcat file (only works in emulator)
			Process process;
			try {
				process = Runtime.getRuntime().exec(
						"logcat > " + fileLocation 
								+ "/logcatTodoBatt.txt");
				
				bufferedReader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			

			// It is necessary to prevent device sleeping!--------------------------------
			powerManager = (PowerManager) this
					.getSystemService(Context.POWER_SERVICE);
			wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
					"Keeping awake!!");
			
				
				wakeLock.acquire();
				
			// --------------------------------------------------------------------------

			
			appRef = this;
				
			
			
			//System.out.println(testsToDo.size());
			btStartTest.setOnClickListener(new OnClickListener()
		    {

		        public void onClick(View v)
		        {   
		             doTests();
		        } 

		    });
			
			 btStartTest.setActivated(true);
			 btBatteryTest.setActivated(true);

	}



	
		
	
	
	private void doTests(){
		
		//TODO temporário até fazer a interface gráfica que gere o XML de testes
		if(btBatteryTest.isChecked()){
			aTest = new Intent(appRef, BatteryMetric.class);
			aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000000);
			aTest.putExtra(PerformanceTestInterface.THELASTTEST, true);
			aTest.putExtra(PerformanceTestInterface.BATTERYTEST, true);
			aTest.putExtra(BatteryMetric.BY_TIME, true);
			aTest.putExtra(BatteryMetric.TEST_TIME, new Long(1000));
			aTest.putExtra(PerformanceTestInterface.FILELOCATION, fileLocation);
			aTest.putExtra(PerformanceTestInterface.STRING_ARRAY, rawResourceNames);
			aTest.putExtra(PerformanceTestInterface.SNIPPETS, this.getResources().getStringArray(R.array.snippets));	
			aTest.putExtra(PerformanceTestInterface.STATUS, "Testing battery skills...");
			aTest.putExtra(PerformanceTestInterface.NETWORK_TEST, true);
			aTest.putExtra(PerformanceTestInterface.FILELOCATION, fileLocation);
			testsToDo.add(aTest);
		}
		else	
		testsToDo = TestsManager.getTestList(this);
		
		setContentView(R.layout.performance_test);
		status = (TextView)findViewById(R.id.status);
		status.setText("That is keeping the device awake...");
		executeTest(testsToDo.get(countIntent));
		//tdo.execute();

		
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (data == null)
			return;
		Bundle results = data.getExtras();
		if(resultCode == RESULT_OK)
			System.out.println(testsToDo.get(countIntent).getStringExtra(PerformanceTestInterface.STATUS) + " executou corretamente!");
		else if (results.containsKey(PerformanceTestInterface.ERROR_MESSAGE))
			System.out.println(results.getString(PerformanceTestInterface.ERROR_MESSAGE));
		else
			System.out.println(testsToDo.get(countIntent).getStringExtra(PerformanceTestInterface.STATUS) + " falhou!");
		
		if(resultCode == RESULT_CANCELED){
			//TODO This need a specific treatment when some test fails. To create list with error messages of each test for example.
			//System.out.println(testsToDo.get(countIntent).getStringExtra(STATUS) + " failed!!!! :(");
			(new FileOperation()).testTJMwriteSequentialFile(fileLocation + "/" + testsToDo.get(countIntent).getStringExtra(PerformanceTestInterface.STATUS)+ ".txt",
					data.getStringExtra(PerformanceTestInterface.ERROR_MESSAGE));
		}
		
		if (requestCode == 1 && !results.getBoolean(PerformanceTestInterface.THELASTTEST)) {
				
			executeTest(testsToDo.get(++countIntent));
		}
		else{
			
			
			File textFile2Read = new File(DATA_FILE_NAME);
			
			if(textFile2Read.canRead()){
				try{
					Intent i2 = new Intent();
					i2.setAction(android.content.Intent.ACTION_VIEW);
					i2.setDataAndType(Uri.fromFile(textFile2Read), "text/xml");
					startActivity(i2);
				}
				catch(Exception e){
					e.getStackTrace();
				}
			}
			System.out.println("Has PerformanceTestDir folder been deleted? "
					+ WriteNeededFiles.deleteFiles(appRef, rawResourceNames));
			appRef.finish();
		}
		
		
		
	}

	public void onResume() {
		wakeLock.acquire();
		super.onResume();

	}

	public void onPause() {
	
		wakeLock.release();
		super.onPause();

	}
	
	
	
	
	void executeTest(Intent test){
		//Close Internet connection before execute tests.
		
		if(test.getExtras().getBoolean(PerformanceTestInterface.NETWORK_TEST)){
			InternetController.setWifiAvailability(true, appRef);
		}
		else
			InternetController.setWifiAvailability(false, appRef);
		try{
		startActivityForResult(test, 1);
		} catch (Exception e) {
			FileOperation rw = new FileOperation();
			String message = (e.getMessage() != null)
					&& (!e.getMessage().trim().isEmpty()) ? e.getMessage()
					: "Exception without message!!!";
			rw.testTJMwriteSequentialFile(
					WriteNeededFiles.REPORT_DIRECTORY_NAME + "/ErrorsMainActivity.txt",
					message);
			System.err.println(message);
			File textFile2Read = new File(WriteNeededFiles.REPORT_DIRECTORY_NAME + "/ErrorsMainActivity.txt");
			if(textFile2Read.canRead()){
				Intent i2 = new Intent();
				i2.setAction(android.content.Intent.ACTION_VIEW);
				i2.setDataAndType(Uri.fromFile(textFile2Read), "text/xml");
				startActivity(i2);
			}
			
			finish();

		}
	}
	
	public void onDestroy(){
		super.onDestroy();
		
	}



	public static String getFileLocation() {
		return fileLocation;
	}

}
