package edu.performance.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Debug.MemoryInfo;

public class Benchmark extends Activity {
	private long time;
	private int pid;
	protected IntentFilter mPackagesFilter;
	protected Intent startIntent = null;
	protected String data[] = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mPackagesFilter = new IntentFilter();
		mPackagesFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
		mPackagesFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
		mPackagesFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
		mPackagesFilter.addCategory(Intent.CATEGORY_DEFAULT);
		mPackagesFilter.addDataScheme("package");

		registerReceiver(null, mPackagesFilter);

		startIntent = getIntent();
		try {
			data = getData(startIntent.getDataString());
		} catch (Exception e) {
			System.err.println("Running with default level");
			data = new String[1];
			data[0] = "100";

		} finally {

		}

		if (data == null)
			finish();
		super.onCreate(savedInstanceState);
		time = System.currentTimeMillis();
		pid = android.os.Process.myPid();
		/*
		 * try{
		 * Debug.dumpHprofData(Environment.getExternalStorageDirectory().getPath
		 * () +"/"+ String.valueOf(android.os.Process.myPid())+ "dump.hprof");
		 * 
		 * } catch(Exception e){ System.out.println("DEU BOSTA no inicio DE: " +
		 * String.valueOf(android.os.Process.myPid())); };
		 */

		// icount = new InstructionCount();
		// icount.resetAndStart();
		// System.out.println(Debug.getThreadAllocSize() + " - allocsize");
		// System.out.println(Debug.getNativeHeapAllocatedSize() + " - heap ");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		time = System.currentTimeMillis() - time;
		// get Activity manager
		ActivityManager activity_manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		long tempoCpu = android.os.Process.getElapsedCpuTime();
		// long ttcpu = Debug.threadCpuTimeNanos();
		// Get memory Info
		int process_pid[] = new int[] { pid };
		MemoryInfo[] memoryInfo = activity_manager
				.getProcessMemoryInfo(process_pid);
		/*
		 * long numInstrucoes = 0, numMethInvoc = 0; if (icount.collect()) {
		 * numInstrucoes = icount.globalTotal(); numMethInvoc =
		 * icount.globalMethodInvocations(); }
		 */

		System.out.println("Benchmark finished;" + pid + ";" + time + /*
																	 * ";size:"
																	 * + Debug.
																	 * getThreadAllocSize
																	 * () +
																	 * ";heap:"
																	 * + Debug.
																	 * getNativeHeapAllocatedSize
																	 * () +
																	 */
		";" + memoryInfo[0].getTotalPrivateDirty()/*
												 * + ";NI:" + numInstrucoes +
												 * ";NMI:" + numMethInvoc +
												 */+ ";" + tempoCpu);

		// System.out.println("Memoria: " + Debug.getPss());

	}

	public static String[] getData(String data) {

		String[] split = null;
		// System.out.println("antes de separar: " + urls);
		split = data.split("-");
		// System.out.println("tam" + separadas.length);
		return split;

	}

}
