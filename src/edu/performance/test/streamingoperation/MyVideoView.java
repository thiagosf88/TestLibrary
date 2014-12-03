package edu.performance.test.streamingoperation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.VideoView;

public class MyVideoView extends VideoView {

	public MyVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setWillNotDraw(false);
		
	}
	
	public void draw(Canvas canvas){
		System.out.println("c1");
		super.draw(canvas);
		System.out.println("c2");
	}
	
	public void onDraw(Canvas canvas){
		System.out.println("c3");
		super.onDraw(canvas);
		System.out.println("c4");
	}

}
