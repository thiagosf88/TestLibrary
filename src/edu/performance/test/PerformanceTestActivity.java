package edu.performance.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.TextView;
import edu.performance.test.util.ActivityThread;

public abstract class PerformanceTestActivity extends Activity implements PerformanceTestInterface{
	
	protected PowerManager powerManager = null;
	protected WakeLock wakeLock = null;
	private boolean isTheLast = true, isBatteryTest = false, isTimeOver;
	protected ActivityThread mythread;
	//public static final String ISTHELASTPAGE = "ISTHELASTPAGE";
	PerformanceTestInterface operation;
	protected String message = "Empty message!!!";
	protected TextView status;
	public static final String MAXTIME = "MAXTIMEMS", RESULT_WAS_OK = "RESULTWASOK";
	protected int MAX_TIME_MS = 17000; //Magic max time to avoid infinite tasks
	
	
	
	protected boolean isBatteryTest() {
		return isBatteryTest;
	}

	public void setTheLast(boolean isTheLast) {
		this.isTheLast = isTheLast;
	}


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.performance_test);
		 
		 status = (TextView)findViewById(R.id.status);
		 
		
		if(getIntent().getExtras() != null && getIntent().hasExtra(PerformanceTestInterface.THELASTTEST)
				&& getIntent().hasExtra(MAXTIME) && getIntent().hasExtra(PerformanceTestInterface.STATUS)
				&& getIntent().hasExtra(PerformanceTestInterface.BATTERYTEST)){
			
			if(getIntent().hasExtra(PerformanceTestInterface.THELASTTEST))
				setTheLast(getIntent().getExtras().getBoolean(PerformanceTestInterface.THELASTTEST));
			
			if(getIntent().hasExtra(PerformanceTestInterface.STATUS))
				message = getIntent().getExtras().getString(PerformanceTestInterface.STATUS);
			
			if(getIntent().hasExtra(MAXTIME))
				setMAX_TIME_MS(getIntent().getExtras().getInt(MAXTIME));
			
			if(getIntent().hasExtra(PerformanceTestInterface.BATTERYTEST))
				isBatteryTest = getIntent().getExtras().getBoolean(PerformanceTestInterface.BATTERYTEST);
			
			
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			extras.putString(PerformanceTestInterface.ERROR_MESSAGE, "Não foi possível obter os parâmetros mínimos necessários. O método getExtras retornou null!");
			finishTest(extras);
			finish();
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
		
		avoidingInfiniteTasks();

		mythread.setRunning(true);
		status.setText(message);
		mythread.start();
		}
/**
 * This method save data from executed test and returns the control to Library Activity.
 * @param extras Some extra information to be passed to the control.
 */
	public void finishTest(Bundle extras){
		
		if(mythread != null) // This is true when the test is performed on a different thread. Tests related on Screen normally will return false here.
		mythread.setRunning(false);

		Intent mIntent = new Intent();		
		
		setResult(RESULT_OK, mIntent); 
		//TODO Talvez seja interessante que todos enviem algum bundle, ou seja, ele nunca seja nulo.
		if(extras != null){
		mIntent.putExtras(extras);
		
		if(extras.containsKey(PerformanceTestActivity.RESULT_WAS_OK))
			if(!extras.getBoolean(PerformanceTestActivity.RESULT_WAS_OK)){
				setResult(RESULT_CANCELED, mIntent);
				if(!extras.containsKey(PerformanceTestInterface.ERROR_MESSAGE))
				mIntent.putExtra(PerformanceTestInterface.ERROR_MESSAGE, "Não foi possível obter os parâmetros necessários. O método getExtras retornou null!");
			}
		}

		
		mIntent.putExtra(PerformanceTestInterface.THELASTTEST, isTheLast);
		 
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
	/**
	 * This method is used to assure that the the tests will have a max time to finish.
	 * If the test is taking a long time it will be canceled.
	 */
	protected void avoidingInfiniteTasks(){
		
		new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(getMAX_TIME_MS());
                    setTimeOver(false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!isBatteryTest()){
                setTimeOver(true);
                Intent mIntent = new Intent();
                mIntent.putExtra(PerformanceTestInterface.THELASTTEST, isTheLast());
				setResult(PerformanceTestActivity.RESULT_CANCELED, mIntent);
				
				finish();
                }
                
                
            }
        }).start();
	}

	public boolean isTimeOver() {
		return isTimeOver;
	}

	public void setTimeOver(boolean isTimeOver) {
		this.isTimeOver = isTimeOver;
	}

}
