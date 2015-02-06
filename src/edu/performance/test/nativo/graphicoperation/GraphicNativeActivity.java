package edu.performance.test.nativo.graphicoperation;

import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;


public class GraphicNativeActivity extends PerformanceTestActivity implements SurfaceHolder.Callback
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.main);
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
        surfaceView.getHolder().addCallback(this);
        surfaceView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    Toast toast = Toast.makeText(GraphicNativeActivity.this,
                                                 "This demo combines Java UI and native EGL + OpenGL renderer",
                                                 Toast.LENGTH_LONG);
                    toast.show();
                }});
    }

    @Override
    protected void onStart() {
        super.onStart();
        nativeOnStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        nativeOnResume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        nativeOnPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        nativeOnStop();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        nativeSetSurface(holder.getSurface());
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        nativeSetSurface(null);
    }


    public static native void nativeOnStart();
    public static native void nativeOnResume();
    public static native void nativeOnPause();
    public static native void nativeOnStop();
    public static native void nativeSetSurface(Surface surface);

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}



}
