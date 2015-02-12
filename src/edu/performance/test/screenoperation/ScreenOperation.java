package edu.performance.test.screenoperation;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import edu.performance.test.graphicoperation.TwoDActivity;
import edu.performance.test.graphicoperation.Operation;

public class ScreenOperation extends Operation {
	
	Context context;
	int times = 300;

	public ScreenOperation(Context ctx, AttributeSet attrs) {
		super(ctx);
		context = ctx;
		execute();


		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		((TwoDActivity)context).executeTest(holder, this);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

	public void doDraw(Canvas canvas)

	{

		drawRGB(canvas);
		
		if(((TwoDActivity)context).isItTimeToFinish())			
		((TwoDActivity)context).finishTest();

	}

	
	private void drawRGB(Canvas canvas) {

        Random mRandom = new Random();

        int height = getHeight();
        int width  = getWidth();

        int r = (int)((mRandom.nextInt() % (width*0.8) ) + (width*0.1));
        int g = (int)((mRandom.nextInt() % (height*0.8) ) + (height*0.1));
        int  b = (int)((mRandom.nextInt() % (width*0.3) ) + (width*0.2));

       
            canvas.drawRGB(r, g, b);
        
    }
	
	  public void execute(){
	    	SurfaceHolder holder = getHolder();

			holder.addCallback(this);
	    }
	
	@Override
	public String objectName() {
		
		return "Screen " + getLevel();
	}

}
