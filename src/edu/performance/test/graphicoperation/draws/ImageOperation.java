package edu.performance.test.graphicoperation.draws;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.DrawActivity;
import edu.performance.test.graphicoperation.Operation;

public class ImageOperation extends Operation {
	
	private final int COL = getLevel();
    private final int ROW = getLevel() * 2;
	private float position[] = new float[ROW];
    private boolean direction[] = new boolean[ROW];
    private Bitmap mBitmap;
    private Paint bgPaint;

	public ImageOperation(Context ctx, AttributeSet attrs) {
		super(ctx);
		context = ctx;
		bgPaint = new Paint();
        bgPaint.setColor(Color.BLACK);
        bgPaint.setStyle(Paint.Style.FILL);

        for(int i=0; i<ROW; i++) {
            position[i] = 0;
            direction[i] = true;
        }
		// the bitmap we wish to draw

		// mbitmap = BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.logintab_off);

		SurfaceHolder holder = getHolder();

		holder.addCallback(this);
		
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Drawable myDrawable = getResources().getDrawable(R.drawable.icon);
		mBitmap = ((BitmapDrawable) myDrawable).getBitmap();

		((DrawActivity)context).executeTest(holder, this);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

	public void doDraw(Canvas canvas)

	{

		drawImage(canvas);

		if(((DrawActivity)context).isItTimeToFinish())			
		((DrawActivity)context).finishTest();


	}

	
    private void drawImage(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        canvas.drawRect(0,0,w,h,bgPaint);

        for(int x=0; x<ROW; x++) {
            int speed = (x+1) * 2;
            
            for(int j=0; j<COL; j++)
                canvas.drawBitmap(mBitmap, null, new RectF((w/(float)COL)*j, position[x], (w/(float)COL)*(j+1), position[x]+(w/(float)COL)), null);
            
            if(direction[x]) {
                position[x] += speed;
                if (position[x] + (w/(float)COL) >= getHeight())
                    direction[x] = !direction[x];
            } else {
                position[x] -= speed;
                if (position[x] <= 0)
                    direction[x] = !direction[x];
            }

        }
    }
	
    public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}
    

	@Override
	public String objectName() {
		
		return "Image: " + getLevel();
	}

	}
