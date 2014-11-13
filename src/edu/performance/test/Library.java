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
import edu.performance.test.database.DatabaseOperationActivity;
import edu.performance.test.downloadoperation.DownloadOperationActivity;
import edu.performance.test.fileoperation.FileOperation;
import edu.performance.test.fileoperation.FileOperationActivity;
import edu.performance.test.floatoperation.FloatOperationActivity;
import edu.performance.test.graphicoperation.threed.CubeTextureGL10Activity;
import edu.performance.test.graphicoperation.threed.CubeColorGLES2Activity;
import edu.performance.test.graphicoperation.twod.ArcActivity;
import edu.performance.test.graphicoperation.twod.CircleActivity;
import edu.performance.test.graphicoperation.twod.ImageActivity;
import edu.performance.test.graphicoperation.twod.RectangleActivity;
import edu.performance.test.graphicoperation.twod.TextActivity;
import edu.performance.test.graphoperation.GraphOperationActivity;
import edu.performance.test.integeroperation.IntegerOperationActivity;
import edu.performance.test.locationoperation.GPSActivity;
import edu.performance.test.mailoperation.MailOperationActivity;
import edu.performance.test.memoryoperation.MemoryOperationActivity;
import edu.performance.test.nativo.fileoperation.FileOperationNativeActivity;
import edu.performance.test.nativo.floatoperation.FloatOperationNativeActivity;
import edu.performance.test.nativo.integeroperation.IntegerOperationNativeActivity;
import edu.performance.test.nativo.memoryoperation.MemoryOperationNativeActivity;
import edu.performance.test.nativo.stringoperation.StringOperationNativeActivity;
import edu.performance.test.screenoperation.HardTestActivity;
import edu.performance.test.screenoperation.LightTestActivity;
import edu.performance.test.screenoperation.MediumTestActivity;
import edu.performance.test.screenoperation.ScreenActivity;
import edu.performance.test.streamingoperation.StreamingActivity;
import edu.performance.test.stringoperation.StringOperationActivity;
import edu.performance.test.util.InternetController;
import edu.performance.test.util.WriteNeededFiles;
import edu.performance.test.weboperation.WebOperationActivity;
import edu.performance.test.weboperation.WebServiceActivity;


/**
 * This class is a Main activity of the Library Performance Test.
 * 
 * @author Thiago
 * 
 */
public class Library extends Activity {

	private int countIntent = 0;
	private List<Intent> testsToDo;
	private String snippets[] = {
			"Copyright laws are changing all over the world",
			"Copyright laws are changing all over the world. Be sure to check the"
					+ "copyright laws for your country before",
			"Copyright laws are changing all over the world. Be sure to check the"
					+ "copyright laws for your country before downloading or redistributing"
					+ "this or any other Project Gutenberg eBook.",
			"No, my brougham is waiting",
			"\"No, my brougham is waiting.\"" + ""
					+ "\"Then that will simplify matters.\"",
			"\"No, my brougham is waiting.\""
					+ ""
					+ "\"Then that will simplify matters.\" We descended and started off once more for Briony Lodge."
					+ "" + "\"Irene Adler is married,\" remarked Holmes.",
			"Sarasate plays at the St. James\'s Hall this afternoon",
			"Sarasate plays at the St. James\'s Hall this afternoon,\" he remarked. \"What do you think, Watson? Could your patients spare you for a few hours?\""
					+ ""
					+ "\"I have nothing to do to-day. My practice is never very absorbing.\"",
			"Sarasate plays at the St. James\'s Hall this afternoon,\" he remarked. \"What do you think, Watson? Could your patients spare you for a few hours?\""
					+ ""
					+ "\"I have nothing to do to-day. My practice is never very absorbing.\""
					+ ""
					+ "\"Then put on your hat and come. I am going through the City first, and we can have some lunch on the way. I observe that there is a good deal of German music on the programme, which is rather more to my taste than Italian or French. It is introspective, and I want to introspect. Come along!\"" };
	private String fileLocation = "";
	public static class fileIndexes {
		public static final int BIG_TXT = 0, MEDIUM_TXT = BIG_TXT + 1,LISTAS_XML = MEDIUM_TXT + 1,
				TAREFAS_XML = LISTAS_XML + 1, SMALL_TXT = TAREFAS_XML + 1,
				TINY_G_TXT = SMALL_TXT + 1, MEDIUM_G_TXT = TINY_G_TXT + 1,
				MEDIUM2_G_TXT = MEDIUM_G_TXT + 1, MEDIUM3_G_TXT = MEDIUM2_G_TXT + 1;
	}

	PowerManager powerManager = null;
	WakeLock wakeLock = null;
	
	int[] rawResourceIds = { R.raw.big,R.raw.medium, R.raw.listas, R.raw.tarefas,
			R.raw.small, R.raw.tiny_g, R.raw.medium_g, R.raw.medium2_g, R.raw.medium3_g};
	String[] rawResourceNames = { "big.txt", "medium.txt", "listas.xml", "tarefas.xml",
			"small.txt", "tiny_g.txt", "medium_g.txt", "medium2_g.txt", "medium3_g.txt" };
	HashMap<String, String> dirNames = null;
	Library appRef = null;
	public final static String DATA_FILE_NAME = WriteNeededFiles.REPORT_DIRECTORY_NAME + "/data_" + System.currentTimeMillis() + "_" + Build.MODEL + ".xml";

	Intent aTest;
	Button btStartTest;
	
	TextView status;
	//Needed to change logcat file
	@SuppressWarnings("unused")
	private BufferedReader bufferedReader;
	
	// Constants used on bundle 
	public static final String THELASTTEST = "THELASTTEST";
	public static final String STATUS = "STATUS";
	public static final String FILEPATH = "FILEPATH";
	public static final String FILENAME = "FILENAME";
	public static final String FILELOCATION = "FILELOCATION";
	public static final String STRETCH = "STRETCH";
	public static final String SEARCHABLE = "SEARCHABLE";
	public static final String SNIPPETS = "SNIPPETS";
	public static final String BATTERYTEST = "BATTERYTEST";
	public static final String LEVEL_INT = "LEVEL_INT";
	public static final String LEVEL_DOUBLE = "LEVEL_DOUBLE";
	public static final String LEVEL_FILENAME = "LEVEL_FILENAME";
	public static final String LEVEL_WEBSITE = "LEVEL_WEBSITE";
	public static final String LEVEL_URL = "LEVEL_URL";
	public static final String NETWORK_TEST = "NETWORKTEST";
	public static final String STRING_ARRAY = "STRINGARRAY";
	public static final String ERROR_MESSAGE = "ERRORMESSAGE";
	public static final String PARAMETERS_INT = "PARAMETERSINT";
	public static final String PARAMETERS_WEB_ARRAY = "PARAMETERSWEBARRAY";
	
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
			 //TODO Esse método foi comentado quando mudei a versão do android para 2.3
			 // btStartTest.setActivated(false);
			 // E esse método foi adicionado
			 btStartTest.setVisibility(View.INVISIBLE);
			 
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
				
			
			testsToDo = TestsManager.getTestList(this);
			System.out.println(testsToDo.size());
			btStartTest.setOnClickListener(new OnClickListener()
		    {

		        public void onClick(View v)
		        {   
		             doTests();
		        } 

		    });
			
			btStartTest.setVisibility(View.VISIBLE);

	}



	@SuppressWarnings("unused")
	private void allTests(){	
		
		//____________________ em ordem		
		
		/*aTest = new Intent(appRef, BatteryOperation.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(FILELOCATION, fileLocation);
		aTest.putExtra(BATTERYTEST, true);
		aTest.putExtra(STATUS, "Testing battery skills...");
		testsToDo.add(aTest);*/
		
		
		
		
		
		
				
				
		aTest = new Intent(appRef, GPSActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 170000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, true);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_INT, 3);
		aTest.putExtra(STATUS, "Testing GPS skills..");
		testsToDo.add(aTest);
		
		
		//Graphic Operation 3D ---------------------------------------------------------------
		
			//----------
		/*aTest = new Intent(appRef, CubeTextureGL10Activity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_INT, 10);
		aTest.putExtra(STATUS, "Testing 3D skills..");
		testsToDo.add(aTest);*/

		aTest = new Intent(appRef, CubeTextureGL10Activity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_INT, 500);
		aTest.putExtra(STATUS, "Testing 3D skills..");
		testsToDo.add(aTest);
		
		/*aTest = new Intent(appRef, CubeTextureGL10Activity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_INT, 500);
		aTest.putExtra(STATUS, "Testing 3D skills..");
		testsToDo.add(aTest);*/
		
			//--------------
		
		/*aTest = new Intent(appRef, CubeColorGLES2Activity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(LEVEL_INT, 0);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);*/
			
		aTest = new Intent(appRef, CubeColorGLES2Activity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 100);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);
		
		/*aTest = new Intent(appRef, CubeColorGLES2Activity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(LEVEL_INT, 100);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);*/
		
		
		
		//------------------------------------------------------------------------------------
		
		
		//GraphicOperation 2D------------------------------------------------------------------
		
			//----------
		
		/*aTest = new Intent(appRef, ArcActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(LEVEL_INT, 1);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);*/
		
		aTest = new Intent(appRef, ArcActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 5);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);
		
		/*aTest = new Intent(appRef, ArcActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(LEVEL_INT, 5);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);*/
		
			//---------
		
		aTest = new Intent(appRef, CircleActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 10);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);
		
			//---------
		
		/*aTest = new Intent(appRef, ImageActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(LEVEL_INT, 3);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);*/
		
		aTest = new Intent(appRef, ImageActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 50);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);
		
		/*aTest = new Intent(appRef, ImageActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(LEVEL_INT, 10);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);*/
		
			//--------
		
		/*aTest = new Intent(appRef, RectangleActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(LEVEL_INT, 10);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);*/
		
		aTest = new Intent(appRef, RectangleActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 1000);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);
		
		/*aTest = new Intent(appRef, RectangleActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(LEVEL_INT, 100);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);*/
		
			//---------
		
		aTest = new Intent(appRef, TextActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 10);
		aTest.putExtra(BATTERYTEST, false);
		testsToDo.add(aTest);
			
			//----------
		// Screen Activity --------------------------------------------------------
		//TODO Falta definir level
		aTest = new Intent(appRef, ScreenActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);		
		aTest.putExtra(LEVEL_INT, 10);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, HardTestActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(STATUS, "Testing Screen skills..");
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, MediumTestActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(STATUS, "Testing Screen skills..");
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, LightTestActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(STATUS, "Testing Screen skills..");
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		testsToDo.add(aTest);
		
		
		
		
		
		//--------------------------------------------------------------------------------------------
		
		//Graph Operation -------------------------------------------------------------------
		
		/*aTest = new Intent(appRef, GraphOperationActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);		
		aTest.putExtra(LEVEL_FILENAME, fileLocation + "/" + rawResourceNames[fileIndexes.TINY_G_TXT]);
		aTest.putExtra(STATUS, "Testing Graph skills...");
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, GraphOperationActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_FILENAME, fileLocation + "/" + rawResourceNames[fileIndexes.MEDIUM_G_TXT]);
		aTest.putExtra(STATUS, "Testing Graph skills...");
		testsToDo.add(aTest);*/
		
		
		
		/*aTest = new Intent(appRef, GraphOperationActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_FILENAME, fileLocation + "/" + rawResourceNames[fileIndexes.MEDIUM3_G_TXT]);
		aTest.putExtra(STATUS, "Testing Graph skills...");
		testsToDo.add(aTest); */
		
		//------------------------------------------------------------------------------------------------------------------
		
	
		aTest = new Intent(appRef, MailOperationActivity.class); 
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 80000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, true);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_FILENAME, fileLocation + "/" +rawResourceNames[fileIndexes.MEDIUM_G_TXT]);
		aTest.putExtra(STATUS, "Testing Mail skills..");
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, MemoryOperationActivity.class); 
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 1000);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(STATUS, "Testing Memory skills..");
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, MemoryOperationNativeActivity.class); 
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 1000);
		aTest.putExtra(STATUS, "Testing Native Memory skills..");
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, FileOperationActivity.class); 
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_INT, 1);
		aTest.putExtra(LEVEL_FILENAME, fileLocation + "/"
				+ rawResourceNames[fileIndexes.SMALL_TXT]);
		aTest.putExtra(STRETCH, appRef.getResources().getString(R.string.stretch));
		aTest.putExtra(STATUS, "Testing File skills..");
		aTest.putExtra(NETWORK_TEST, false);
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, DatabaseOperationActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 170000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_INT, 300);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(STATUS, "Testing Database skills..");
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, FileOperationNativeActivity.class); 
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_INT, 1);
		aTest.putExtra(LEVEL_FILENAME, fileLocation + "/"
				+ rawResourceNames[fileIndexes.SMALL_TXT]);
		aTest.putExtra(STRETCH, appRef.getResources().getString(R.string.stretch));
		aTest.putExtra(STATUS, "Testing Native File skills..");
		testsToDo.add(aTest);
		
		//TODO Falta definir level
				aTest = new Intent(appRef, StreamingActivity.class); 
				aTest.putExtra(THELASTTEST, false);
				aTest.putExtra(BATTERYTEST, false);
				aTest.putExtra(NETWORK_TEST, true);
				aTest.putExtra(LEVEL_INT, 10);
				aTest.putExtra(PerformanceTestActivity.MAXTIME, 90000);
				aTest.putExtra(STATUS, "Testing Streaming skills..");
				testsToDo.add(aTest);
				
				aTest = new Intent(appRef, StringOperationActivity.class); 
				aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
				aTest.putExtra(THELASTTEST, false);
				aTest.putExtra(BATTERYTEST, false);
				aTest.putExtra(NETWORK_TEST, false);
				aTest.putExtra(LEVEL_INT, 10);
				aTest.putExtra(SEARCHABLE, new FileOperation()
				.testTJMreadSequentialAcessFile(
						fileLocation + "/"
								+ rawResourceNames[fileIndexes.SMALL_TXT]));
				aTest.putExtra(SNIPPETS, snippets);	
				aTest.putExtra(STATUS, "Testing String skills..");
				testsToDo.add(aTest);
				

				aTest = new Intent(appRef, WebOperationActivity.class);
				aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
				aTest.putExtra(THELASTTEST, false);
				aTest.putExtra(BATTERYTEST, false);
				aTest.putExtra(NETWORK_TEST, true);
				aTest.putExtra(LEVEL_INT, 3);
				aTest.putExtra(STATUS, "Testing Web skills..");
				testsToDo.add(aTest);
				
				aTest = new Intent(appRef, DownloadOperationActivity.class); 
				aTest.putExtra(PerformanceTestActivity.MAXTIME, 600000);
				aTest.putExtra(THELASTTEST, false);
				aTest.putExtra(BATTERYTEST, false);
				aTest.putExtra(FILELOCATION, fileLocation);
				aTest.putExtra(LEVEL_INT, 1);
				aTest.putExtra(STATUS, "Testing Download skills..");
				aTest.putExtra(NETWORK_TEST, true);
				testsToDo.add(aTest);
				
				
				aTest = new Intent(appRef, WebServiceActivity.class);
				aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
				aTest.putExtra(THELASTTEST, false);
				aTest.putExtra(BATTERYTEST, false);
				aTest.putExtra(NETWORK_TEST, true);
				aTest.putExtra(LEVEL_WEBSITE, "http://mcupdate.tumblr.com/api/read");
				aTest.putExtra(STATUS, "Testing WebService skills..");
				testsToDo.add(aTest);
		
		aTest = new Intent(appRef, GraphOperationActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_FILENAME, fileLocation + "/" + rawResourceNames[fileIndexes.MEDIUM2_G_TXT]);
		aTest.putExtra(STATUS, "Testing Graph skills...");
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, IntegerOperationActivity.class); 
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(LEVEL_INT, 1000);
		aTest.putExtra(STATUS, "Testing Integer skills..");
		testsToDo.add(aTest);
		
		aTest = new Intent(appRef, FloatOperationActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 700000);
		aTest.putExtra(THELASTTEST, true);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_DOUBLE, 999983);
		aTest.putExtra(STATUS, "Testing Float skills..");
		testsToDo.add(aTest);
		
		
		aTest = new Intent(appRef, IntegerOperationNativeActivity.class); 
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 170000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 1000);
		aTest.putExtra(STATUS, "Testing Native Integer skills..");
		testsToDo.add(aTest);
		
		
		
		aTest = new Intent(appRef, StringOperationNativeActivity.class); 
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 170000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(NETWORK_TEST, false);
		aTest.putExtra(LEVEL_INT, 10);
		aTest.putExtra(SEARCHABLE, new FileOperation()
		.testTJMreadSequentialAcessFile(
				fileLocation + "/"
						+ rawResourceNames[fileIndexes.SMALL_TXT]));
		aTest.putExtra(SNIPPETS, snippets);	
		aTest.putExtra(STATUS, "Testing String skills..");
		testsToDo.add(aTest);
		
		
		
		aTest = new Intent(appRef, FloatOperationNativeActivity.class);
		aTest.putExtra(PerformanceTestActivity.MAXTIME, 240000);
		aTest.putExtra(THELASTTEST, false);
		aTest.putExtra(BATTERYTEST, false);
		aTest.putExtra(STATUS, "Testing Native Float skills..");
		aTest.putExtra(LEVEL_DOUBLE, 9979);
		aTest.putExtra(NETWORK_TEST, false);
		testsToDo.add(aTest);
		

		//--------------------------------------------------------------------------------------
		
		
		
		
		
		
		btStartTest.setOnClickListener(new OnClickListener()
	    {

	        public void onClick(View v)
	        {   
	             doTests();
	        } 

	    });
		
		//TODO Esse método foi comentado quando mudei a versão do android para 2.3
		 // btStartTest.setActivated(true);
		 // E esse método foi adicionado
		 btStartTest.setVisibility(View.VISIBLE);
		
		
	}
		
	
	
	private void doTests(){
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
			System.out.println(testsToDo.get(countIntent).getStringExtra(STATUS) + " executou corretamente!");
		else if (results.containsKey(ERROR_MESSAGE))
			System.out.println(results.getString(ERROR_MESSAGE));
		else
			System.out.println(testsToDo.get(countIntent).getStringExtra(STATUS) + " falhou!");
		
		if(resultCode == RESULT_CANCELED){
			//TODO This need a specific treatment when some test fails. To create list with error messages of each test for example.
			//System.out.println(testsToDo.get(countIntent).getStringExtra(STATUS) + " failed!!!! :(");
			(new FileOperation()).testTJMwriteSequentialFile(fileLocation + "/" + testsToDo.get(countIntent).getStringExtra(STATUS)+ ".txt", "falhou");
		}
		
		if (requestCode == 1 && !results.getBoolean(THELASTTEST)) {
				
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
			
			appRef.finish();
		}
		
		
		
	}

	public void onResume() {
		wakeLock.acquire();
		super.onResume();

	}

	public void onPause() {
		//System.out.println("Has PerformanceTestDir folder been deleted? "
				//+ WriteNeededFiles.deleteFiles(appRef, rawResourceNames));
		wakeLock.release();
		super.onPause();

	}
	
	
	
	
	void executeTest(Intent test){
		//Close Internet connection before execute tests.
		
		if(test.getExtras().getBoolean(NETWORK_TEST)){
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
			//wakeLock.release();T
			//unregisterReceiver(mBatInfoReceiver);
			//Shows error file if a exception is throw
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

}
