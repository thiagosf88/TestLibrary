package edu.performance.test.graphicoperation.draws;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import edu.performance.test.graphicoperation.DrawActivity;
import edu.performance.test.graphicoperation.Operation;

public class RectangleOperation extends Operation {

	private ArrayList<ColoredRect> rectengleList = new ArrayList<ColoredRect>();

	class ColoredRect {
		public Rect mRect;
		public int mColor;

		ColoredRect(int color, int left, int top, int right, int bottom) {
			this.mRect = new Rect(left, top, right, bottom);
			this.mColor = color;
		}
	}

	public RectangleOperation(Context ctx, AttributeSet attrs) {
		super(ctx);

		context = ctx;

		SurfaceHolder holder = getHolder();

		holder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//generateNewRect(1000);
		((DrawActivity) context).executeTest(holder, this);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		((DrawActivity) context).finishTest();

	}

	public void doDraw(Canvas canvas)

	{
		generateNewRect();
		drawAll(canvas);

		if (((DrawActivity) context).isItTimeToFinish())
			((DrawActivity) context).finishTest();

	}
	/**
	 * This method create a new rectangle and put it inside a list of drawable rectangle.
	 * The level determines the max size of this list.
	 */
	private void generateNewRect() {
		
		if(rectengleList.size() < getLevel()){
		Random mRandom = new Random();
		int height = getHeight();
		int width = getWidth();

		int cx = (int) ((mRandom.nextInt() % (width * 0.8)) + (width * 0.1));
		int cy = (int) ((mRandom.nextInt() % (height * 0.8)) + (height * 0.1));
		int hw = (int) (mRandom.nextInt() % (width * 0.4) + width * 0.2) / 2;
		int hh = (int) (mRandom.nextInt() % (height * 0.4) + height * 0.2) / 2;

		int color = (0x00252525 | mRandom.nextInt()) & 0x00FFFFFF | 0x77000000;
		
		
		rectengleList.add(new ColoredRect(color, cx - hw, cy - hh, cx + hw, cy
				+ hh));
		}
		else{
			rectengleList.clear();			
		}
	}

	private void drawAll(Canvas canvas) {
		//TODO Esse método foi comentado quando mudei a versão do android para 2.3
		//canvas.isHardwareAccelerated();
		for (ColoredRect cr : rectengleList) {
			Paint p = new Paint();
			p.setAntiAlias(false);
			p.setStyle(Paint.Style.FILL);
			p.setColor(cr.mColor);

			canvas.drawRect(cr.mRect, p);
		}
		
	}
	
	@Override
	public String objectName() {
		
		return "Rectangles: " + getLevel();
	}

}
