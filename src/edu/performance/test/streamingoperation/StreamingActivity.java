package edu.performance.test.streamingoperation;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;
import edu.performance.test.R;
import edu.performance.test.util.ActivityThread;

public class StreamingActivity extends InternetPerformanceTestActivity implements
		SurfaceHolder.Callback, OnPreparedListener, OnBufferingUpdateListener,
		OnCompletionListener, OnInfoListener, PerformanceTestInterface {

	private MediaPlayer mediaPlayer;
	private SurfaceHolder vidHolder;
	private MySurfaceView vidSurface;
	ActivityThread mythread;
	public static final String STREAMSIZE = "STREAMSIZE";
	boolean isTheLast = true;
	String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		execute();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.streaming);
		
		if (getIntent().getExtras() != null) {
			if (getIntent().hasExtra(PerformanceTestActivity.LEVEL_URL)
					&& getIntent().hasExtra(PerformanceTestActivity.THELASTTEST)) {

				isTheLast = getIntent().getExtras().getBoolean(
						PerformanceTestActivity.THELASTTEST);
				
				vidAddress = getIntent().getExtras().getString(PerformanceTestActivity.LEVEL_URL);

				
			}

			else {
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				Intent mIntent = new Intent();
				mIntent.putExtra(PerformanceTestActivity.THELASTTEST, isTheLast());
				mIntent.putExtras(extras);
				setResult(RESULT_CANCELED, mIntent);
				finish();
				return;
			}
		} else {
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			Intent mIntent = new Intent();
			mIntent.putExtra(PerformanceTestActivity.THELASTTEST, isTheLast());
			mIntent.putExtras(extras);
			setResult(RESULT_CANCELED, mIntent);
			finish();
			return;
		}
		
		vidSurface = ((MySurfaceView) findViewById(R.id.surfView));
		vidHolder = vidSurface.getHolder();
		vidHolder.addCallback(this);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		System.out.println(percent);
		/*if (percent == 100) { // Finishing when the video is completing buffered
					
			 
			
			Bundle extras = new Bundle();			
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
			finishTest(extras);
			if (mediaPlayer != null) {
				mediaPlayer.release();
				mediaPlayer = null;
			}
			
		}*/

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		finishTest(extras);
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	@Override
	public void execute() {
		
super.execute();
		
		try {
			
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDisplay(vidHolder);
			mediaPlayer.setDataSource(vidAddress);
			mediaPlayer.prepare();
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnBufferingUpdateListener(this);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnInfoListener(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void executeTest() {
		
		mythread = new ActivityThread(this);

		mythread.setRunning(true);
		// status.setText(message);
		mythread.start();
	}
	
	public void onDestroy(){
		super.onDestroy();
	}

	@Override
	public boolean onInfo(MediaPlayer arg0, int arg1, int arg2) {
		System.out.println("What: " + arg1 + " extra: " + arg2);
		return false;
	}

	public boolean isTheLast() {
		return isTheLast;
	}

}
