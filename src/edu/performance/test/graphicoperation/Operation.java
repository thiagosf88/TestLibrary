package edu.performance.test.graphicoperation;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class Operation extends SurfaceView implements
	SurfaceHolder.Callback{
	
	protected Context context;
	protected int level = 5;
	protected void setLevel(int level){
		this.level = level;
	}
	
	protected int getLevel(){
		return this.level;
	}
	
	public Operation(Context context) {
		super(context);
		this.context = context;
		this.level = ((DrawActivity) context).getLevel();
		
	}
	
	public abstract void doDraw(Canvas canvas);
	
	public abstract String objectName();
	
	
	
	}
