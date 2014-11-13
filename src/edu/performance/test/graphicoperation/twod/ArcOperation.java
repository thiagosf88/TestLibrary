package edu.performance.test.graphicoperation.twod;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import edu.performance.test.graphicoperation.TwoDActivity;
import edu.performance.test.graphicoperation.Operation;

public class ArcOperation extends Operation {
	
	 private int angle = 0;
	 private int step = 5;
	 

	public ArcOperation(Context ctx, AttributeSet attrs) {
		super(ctx);
		
		
		
		
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

		drawArc(canvas);
		if(((TwoDActivity)context).isItTimeToFinish())			
		((TwoDActivity)context).finishTest();
		//canvas.drawColor(Color.GREEN);

		//canvas.drawBitmap(mbitmap, 50, 50, null);
	}

	/**
	 * This method draws arcs on screen. The level will determinate the number of
	 * arc which will be draw impacting on frame per second. The recommended level is between
	 * 1 and 5. 
	 * @param canvas
	 */
    private void drawArc(Canvas canvas) {
        if (angle > 360) angle = 0;
        //Define a quantidade de arcos desenhados e assim afeta os fps
        int jota = 3 * getLevel(), xis = 4 * getLevel(), ypslon = 4 * getLevel();
        int color = (0x00252525 | new Random().nextInt() ) | Color.BLACK;
        Paint p = new Paint();
        p.setAntiAlias(false);
        p.setStyle(Paint.Style.FILL);
        p.setColor(color);

        canvas.drawArc(new RectF(0,0, getWidth(), getHeight()), 0, angle, true, p);

        for(int j=0; j<jota; j++) 
        	for(int x=0; x<xis; x++) 
        		for(int y=0; y<ypslon; y++) {
            color = (0x88252525 | new Random().nextInt() );
            p = new Paint();
            p.setAntiAlias(false);
            p.setStyle(Paint.Style.FILL);
            p.setColor(color);

            if(x%2==0)
                canvas.drawArc(new RectF( x*getWidth()/xis, y*getHeight()/ypslon, (1+x)*getWidth()/xis, (1+y)*getHeight()/ypslon), 0, angle, (x+y)%2 == 0, p);
            else
                canvas.drawArc(new RectF( x*getWidth()/xis, y*getHeight()/ypslon, (1+x)*getWidth()/xis, (1+y)*getHeight()/ypslon), 0, -angle, (x+y)%2 == 0, p);
        }
        angle += step;
    }
    
    
    public void execute(){
    	SurfaceHolder holder = getHolder();

		holder.addCallback(this);
    }
    
	@Override
	public String objectName() {
		
		return "Arcs: " +  getLevel();
	}
	
}
