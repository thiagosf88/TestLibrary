package edu.performance.test.graphicoperation.twod;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import edu.performance.test.graphicoperation.TwoDActivity;
import edu.performance.test.graphicoperation.Operation;

public class TextOperation extends Operation {
	
	public final String TEXT1 = "Master Degree";
    public final String TEXT2 = "LSE_2015";
    public final int TIMES = 10;

    private Paint bgPaint;
    class PaintText {
        public int x;
        public int y;
        public Paint paint;
        public boolean text;
        PaintText(Paint paint, int x, int y, boolean text) {
            this.paint = paint;
            this.x = x;
            this.y = y;
            this.text = text;
        }
    }
//TODO FIXME Passar array de textos a serem desenhados
	public TextOperation(Context ctx, AttributeSet attrs) {
		super(ctx);
		context = ctx;

		SurfaceHolder holder = getHolder();

		holder.addCallback(this);
		bgPaint = new Paint();
        bgPaint.setColor(Color.BLACK);
        bgPaint.setStyle(Paint.Style.FILL);
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

		generateNewText(canvas);

		if(((TwoDActivity)context).isItTimeToFinish())			
		((TwoDActivity)context).finishTest();

	}

	
	private void generateNewText(Canvas canvas) {
        Random mRandom = new Random();
        int height = getHeight();
        int width  = getWidth();
        canvas.drawRect(0,0,width,height,bgPaint);
       
        int cx;
        int cy;
        int color; 
        for(int i=0; i<TIMES; i++) {
            cx = (int)((mRandom.nextInt() % (width*0.8) ) + (width*0.1));
            cy = (int)((mRandom.nextInt() % (height*0.8) ) + (height*0.1));

            color = (0x00555555 | mRandom.nextInt() ) | Color.BLACK; 
            Paint p = new Paint();
            p.setAntiAlias(true);
            p.setStyle(Paint.Style.FILL);
            p.setTextAlign(Paint.Align.CENTER);

            if(mRandom.nextInt()%2 == 0)
                p.setFakeBoldText(true);

            if(mRandom.nextInt()%2 == 0)
                p.setTextSkewX((float)-0.45);

            p.setColor(color);
            p.setTextSize(42 + (mRandom.nextInt()%28));

            if(mRandom.nextInt()%2 == 0)
                canvas.drawText(TEXT1, cx, cy, p);
            else
                canvas.drawText(TEXT2, cx, cy, p);
        }
    }
	
	@Override
	public String objectName() {
		
		return "Text: " + getLevel();
	}
	
}
