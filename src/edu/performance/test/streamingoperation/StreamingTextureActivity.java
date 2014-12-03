package edu.performance.test.streamingoperation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;
import edu.performance.test.util.ActivityThread;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class StreamingTextureActivity extends InternetPerformanceTestActivity implements
		TextureView.SurfaceTextureListener, OnPreparedListener, OnBufferingUpdateListener,
		OnCompletionListener {

	private MediaPlayer mediaPlayer;
	private Surface surf;
	private TextureView vidSurface;
	int count = 0;
	ActivityThread mythread;
	public static final String STREAMSIZE = "STREAMSIZE";
	boolean isTheLast = true;
	String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";



	@Override
	public void onPrepared(MediaPlayer mp) {
		mediaPlayer.start();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.streaming_texture);
		
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
		
		vidSurface = ((TextureView) findViewById(R.id.textureView));
		vidSurface.setSurfaceTextureListener(this);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		//System.out.println(percent);
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
			mediaPlayer.setSurface(surf);
			AssetFileDescriptor afd = getAssets().openFd("big_buck_bunny.mp4");
			mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
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


	public boolean isTheLast() {
		return isTheLast;
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		surf = new Surface(surface);
		execute();
		
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		//System.out.println("vezes: " + surface.getTimestamp());
		
	}

}
