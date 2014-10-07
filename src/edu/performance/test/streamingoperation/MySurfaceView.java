package edu.performance.test.streamingoperation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView {

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setWillNotDraw(false);
	}
	
	 @Override
	    protected void onDraw(Canvas canvas){
	        super.onDraw(canvas);
	        System.out.println( "On Draw Called");
	    }

}
