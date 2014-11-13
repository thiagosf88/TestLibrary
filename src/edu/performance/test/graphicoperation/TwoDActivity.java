package edu.performance.test.graphicoperation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.SurfaceHolder;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.util.ActivityThread;
import edu.performance.test.util.TestThread;

public abstract class TwoDActivity extends PerformanceTestActivity{
	
	TestThread mythreadT;
	boolean isTheLast;
	int frames;
	protected int times = 1000;
	PowerManager powerManager = null;
	WakeLock wakeLock = null;
	boolean isTimeOver = false;
	protected int level;
	
	public int getLevel(){
		return this.level;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(getIntent().getExtras() != null){
			if(getIntent().hasExtra(Library.LEVEL_INT))		
			level = getIntent().getExtras().getInt(Library.LEVEL_INT);
			else{
				Bundle extras = new Bundle();
				extras.putString(Library.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				setResult(RESULT_CANCELED);
				finish();
			}
		}
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
		}
		
		// It is necessary to prevent device
		// sleeping!--------------------------------
		// status.setText("That is keeping the device awake...");
		// status.setTextColor(Color.MAGENTA);
		powerManager = (PowerManager) this
				.getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK,
				this.getLocalClassName());
			wakeLock.setReferenceCounted(false);
			wakeLock.acquire();
			
		// --------------------------------------------------------------------------
	}
	
	protected void onResume() {
		wakeLock.acquire();
		super.onResume();

	}

	protected void onPause() {
		//wakeLock.release(); Tirando por estava dando erro de liberar algo que estava sendo usado ainda.
		super.onPause();

	}

	public void executeTest(SurfaceHolder holder, Operation gt){
		mythreadT = new TestThread(holder, this, gt);

		mythreadT.setRunning(true);

		mythreadT.start();
		avoidingInfiniteTasks();
		}

	public void finishTest(){
		
mythreadT.setRunning(false);
		/*boolean retry = true;

		while (retry)

		{

			try

			{

				mythread.join();
				retry = false;
				
				

			}

			catch (Exception e)

			{

				Log.v("Exception Occured", e.getMessage());

			}

		}*/
		Intent mIntent = new Intent();
		mIntent.putExtra(Library.THELASTTEST, isTheLast);
		setResult(RESULT_OK, mIntent);
		System.out.println("saindo. Is it the last? " + isTheLast );
		wakeLock.release(); // olhar comentário no onPause()
		finish();

	}

	public void orderStop(){
		mythreadT.setRunning(false);
	}
	

	public boolean isItTimeToFinish() {
		
		return isTimeOver;
		/*if (times-- > 0)
			return false;

		return true;*/
	}
	
	protected int getMAX_TIME_MS(){
		return super.getMAX_TIME_MS();
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
				setResult(ThreeDActivity.RESULT_OK, mIntent);
				System.out.println("saindo. Is it the last? " + isTheLast);
				finish();
                }
                
                
            }
        }).start();
	}
	
	

	public void executeTest(){
		mythread = new ActivityThread(this);

		mythread.setRunning(true);

		mythread.start();
		avoidingInfiniteTasks();
		}

	}

