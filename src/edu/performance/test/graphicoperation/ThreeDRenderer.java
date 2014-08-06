package edu.performance.test.graphicoperation;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import edu.performance.test.PerformanceTest;

public class ThreeDRenderer extends PerformanceTest<Integer> implements Renderer {



	public ThreeDRenderer(Integer level, Context activity) {
		super(level, (ThreeDActivity)activity);
		
	}



	@Override
	public void execute() {
		

	}



	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
	}

}
