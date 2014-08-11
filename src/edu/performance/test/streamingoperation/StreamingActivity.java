package edu.performance.test.streamingoperation;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;
import edu.performance.test.R;
import edu.performance.test.util.ActivityThread;

public class StreamingActivity extends InternetPerformanceTestActivity implements
		SurfaceHolder.Callback, OnPreparedListener, OnBufferingUpdateListener,
		OnCompletionListener, PerformanceTestInterface {

	private MediaPlayer mediaPlayer;
	private SurfaceHolder vidHolder;
	private SurfaceView vidSurface;
	ActivityThread mythread;
	public static final String STREAMSIZE = "STREAMSIZE";

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
		vidSurface = (SurfaceView) findViewById(R.id.surfView);
		vidHolder = vidSurface.getHolder();
		vidHolder.addCallback(this);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		System.out.println(percent);
		if (percent == 100) { // Finishing when the video is completing buffered
					
			 
			
			finishTest(null);
			if (mediaPlayer != null) {
				mediaPlayer.release();
				mediaPlayer = null;
			}
			
		}

	}

	@Override
	public void onCompletion(MediaPlayer mp) {

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

}
