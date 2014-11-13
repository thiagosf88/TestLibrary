package edu.performance.test.graphicoperation.threed;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;

public class CubeTextureGLES2Activity extends PerformanceTestActivity 
{
	/** Hold a reference to our GLSurfaceView */
	private GLSurfaceView mGLSurfaceView;
	
	protected int level;
	protected int idTextureResource;

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		if(getIntent().getExtras() != null){
			
			if(getIntent().hasExtra(Library.LEVEL_INT) && getIntent().hasExtra(Library.PARAMETERS_INT)){
				level = getIntent().getExtras().getInt(Library.LEVEL_INT);
				idTextureResource = getIntent().getExtras().getInt(Library.PARAMETERS_INT);
			}
			else{
				Bundle extras = new Bundle();
				extras.putString(Library.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: level ou idTextureResource!");
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
		

		mGLSurfaceView = new GLSurfaceView(this);

		// Check if the system supports OpenGL ES 2.0.
		final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
		final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

		if (supportsEs2) 
		{
			// Request an OpenGL ES 2.0 compatible context.
			mGLSurfaceView.setEGLContextClientVersion(2);

			// Set the renderer to our demo renderer, defined below.
			mGLSurfaceView.setRenderer(new CubeTextureGLES2Renderer(this));
		} 
		else 
		{
			// This is where you could create an OpenGL ES 1.x compatible
			// renderer if you wanted to support both ES 1 and ES 2.
			return;
		}

		setContentView(mGLSurfaceView);
	}

	@Override
	protected void onResume()
	{
		// The activity must call the GL surface view's onResume() on activity onResume().
		super.onResume();
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() 
	{
		// The activity must call the GL surface view's onPause() on activity onPause().
		super.onPause();
		mGLSurfaceView.onPause();
	}

	@Override
	public void execute() {
		
		
	}
	
	public int getLevel(){
		return this.level;
	}
	
	protected int getMAX_TIME_MS(){
		return super.getMAX_TIME_MS();
	}
	
	public boolean isTheLast() {
		return super.isTheLast();
		
	}

	public int getIdTextureResource() {
		return idTextureResource;
	}	
}
