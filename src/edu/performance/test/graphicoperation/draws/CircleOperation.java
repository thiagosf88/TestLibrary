package edu.performance.test.graphicoperation.draws;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import edu.performance.test.graphicoperation.DrawActivity;
import edu.performance.test.graphicoperation.Operation;

public class CircleOperation extends Operation {
	
	public CircleOperation(Context ctx, AttributeSet attrs) {
		super(ctx);
		
	System.out.println(ctx.getClass().getName());
		// the bitmap we wish to draw

		// mbitmap = BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.logintab_off);

		SurfaceHolder Holder = getHolder();

		Holder.addCallback(this);
		
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		((DrawActivity)context).executeTest(holder, this);
		

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
		drawCircle(canvas);
		
		if(((DrawActivity)context).isItTimeToFinish())			
		((DrawActivity)context).finishTest();

	}

	/**
	 * This method draws circles on screen with R and center point randomly defined.
	 * The level determines the quantity of circles will be drown with the same center point.
	 * There is no limit to level but it must to be positive integer. 
	 * @param canvas
	 */
	private void drawCircle(Canvas canvas) {

        Random mRandom = new Random();

        int height = getHeight();
        int width  = getWidth();

        int cx = (int)((mRandom.nextInt() % (width*0.8) ) + (width*0.1));
        int cy = (int)((mRandom.nextInt() % (height*0.8) ) + (height*0.1));
        int  r = (int)((mRandom.nextInt() % (width*0.3) ) + (width*0.2));

        int color;
        Paint p;
        for(int i=getLevel(); i>=0; i--) {
            color = (0x33252525 | mRandom.nextInt()); 
            p = new Paint();
            p.setAntiAlias(true);
            p.setStyle(Paint.Style.FILL);
            p.setColor(color);
            canvas.drawCircle(cx, cy, (int)(r*(1 + i/10.0)), p);
        }
    }
	
	@Override
	public String objectName() {
		
		return "Circles: " + getLevel();
	}

}
