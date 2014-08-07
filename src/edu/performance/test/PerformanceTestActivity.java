package edu.performance.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.TextView;
import edu.performance.test.graphicoperation.ThreeDActivity;
import edu.performance.test.util.ActivityThread;

public abstract class PerformanceTestActivity extends Activity implements PerformanceTestInterface{
	
	protected PowerManager powerManager = null;
	protected WakeLock wakeLock = null;
	private boolean isTheLast, isBatteryTest = false, isTimeOver;
	protected ActivityThread mythread;
	//public static final String ISTHELASTPAGE = "ISTHELASTPAGE";
	PerformanceTestInterface operation;
	protected String message;
	protected TextView status;
	public static final String MAXTIMEMS = "MAXTIMEMS";
	
	
	protected boolean isBatteryTest() {
		return isBatteryTest;
	}

	public void setTheLast(boolean isTheLast) {
		this.isTheLast = isTheLast;
	}

	protected int MAX_TIME_MS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.performance_test);
		 
		 status = (TextView)findViewById(R.id.status);
		
		if(getIntent().getExtras() != null){
			isTheLast = getIntent().getExtras().getBoolean(Library.THELASTTEST);
			message = getIntent().getExtras().getString(Library.STATUS);
			setMAX_TIME_MS(getIntent().getExtras().getInt(MAXTIMEMS));
			isBatteryTest = getIntent().getExtras().getBoolean(Library.BATTERYTEST);
			
		}
		
		powerManager = (PowerManager) this
				.getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
				this.getLocalClassName());
		
		if ((wakeLock != null) &&           
			    (wakeLock.isHeld() == false)) { 
			wakeLock.acquire();
			}
		
		
		
	
	}
	
	protected void onResume() {		
		super.onResume();
		if ((wakeLock != null) &&           
			    (wakeLock.isHeld() == false)) {  
			wakeLock.acquire();
			}

	}
	
	protected void onPause() {
		super.onPause();
		wakeLock.release();
	}
	
	
	
	protected int getMAX_TIME_MS() {
		return MAX_TIME_MS;
	}

	protected void setMAX_TIME_MS(int mAX_TIME_MS) {
		MAX_TIME_MS = mAX_TIME_MS;
	}

	public abstract void execute();
	/**
	 * This method starts a new thread to execute the performance tests. 
	 */
	public void executeTest(){
		mythread = new ActivityThread(this);

		mythread.setRunning(true);
		status.setText(message);
		mythread.start();
		}
/**
 * This method release the wakelock and calls the onDestroy method.
 * @param extras Some extra information to be passed to the control.
 */
	public void finishTest(Bundle extras){
		
		if(mythread != null) // This is true when the test is performed on a different thread.
		mythread.setRunning(false);

		Intent mIntent = new Intent();
		if(extras != null)
		mIntent.putExtras(extras);
		mIntent.putExtra(Library.THELASTTEST, isTheLast);
		setResult(RESULT_OK, mIntent);
		
		 
		finish();

	}

	protected boolean isTheLast() {
	return isTheLast;
}

	public void orderStop(){
		mythread.setRunning(false);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	
	protected void avoidingInfiniteTasks(){
		
		new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(getMAX_TIME_MS());
                    isTimeOver = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isBatteryTest()){
                isTimeOver = true;
                Intent mIntent = new Intent();
                mIntent.putExtra(Library.THELASTTEST, isTheLast);
				setResult(ThreeDActivity.RESULT_CANCELED, mIntent);
				
				finish();
                }
                
                
            }
        }).start();
	}

}
