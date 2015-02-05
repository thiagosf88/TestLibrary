/*
 * Copyright © 2012 Iain Churcher
 *
 * Based on GLtron by Andreas Umbach (www.gltron.org)
 *
 * This file is part of GL TRON.
 *
 * GL TRON is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GL TRON is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GL TRON.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


package edu.performance.test.graphicoperation.threed.gltron;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.ThreeDActivity;
import edu.performance.test.graphicoperation.threed.gltron.Camera.CamType;
import edu.performance.test.graphicoperation.threed.gltron.Lighting.LightType;


public class OpenGLRenderer implements GLSurfaceView.Renderer {

	//GLTronGame Game = new GLTronGame();
	
	
	// Define Time data
		public long TimeLastFrame;
		public long TimeCurrent;
		public long TimeDt;
		
		
		// DT smoothing experiment
		private final int MAX_SAMPLES = 20;
		private long DtHist[] = new long[MAX_SAMPLES];
		private int DtHead = 0;
		private int DtElements = 0;
	
	Context context;
	
	private Model LightBike;
	private final float ColourDiffuse[][] = {
			{ 0.0f, 0.1f, 0.900f, 1.000f},      // Blue
			{ 1.00f, 0.550f, 0.140f, 1.000f},   // Yellow
			{ 0.750f, 0.020f, 0.020f, 1.000f},  // Red
			{ 0.800f, 0.800f, 0.800f, 1.000f},  // Grey
			{ 0.120f, 0.750f, 0.0f, 1.000f},    // Green
			{ 0.750f, 0.0f, 0.35f, 1.000f}      // Purple
	};

	private final float ColourSpecular[][] = {
			{ 0.0f, 0.1f, 0.900f, 1.000f},    // Blue
			{0.500f, 0.500f, 0.000f, 1.000f}, // Yellow
			{0.750f, 0.020f, 0.020f, 1.000f}, // Red
			{1.00f, 1.00f, 1.00f, 1.000f},    // Grey
			{0.050f, 0.500f, 0.00f, 1.00f},   // Green
			{0.500f, 0.000f, 0.500f, 1.00f},  // Purple
	};

	
	String Debug;
	StringBuffer sb = new StringBuffer(40);
	WorldGraphics world;
	private int frameCount = 0;
	private float mCurrentGridSize = 720.0f;
	private Video Visual;
	Camera cam;
	private Lighting Lights = new Lighting();
	
	public OpenGLRenderer(Context context)
	{
		this.context = context;
		//initWalls();
		updateScreenSize(720, 360);
		
		//Log.e("GLTRON", "Renderer Constructor: Create Video Object");
		//Debug = sb.append("Screen size = ").append(win_width).append(",").append(win_height).toString();
		//Log.e("GLTRON", Debug);
		//Game.updateScreenSize(win_width, win_height);
	}
	
	public void setUI_Handler(Handler handler)
	{
		//Game.setUI_Handler(handler);
	}
	
	public void onTouch(float x, float y)
	{
		//Game.addTouchEvent(x, y);
	}
	
	public void onPause()
	{
		//Game.pauseGame();
	}
	
	public void onResume()
	{
		//Game.resumeGame();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

	    Log.e("GLTRON", "Renderer: Surface Created Do perspective");
	    /*word = new WorldGraphics(gl, context, 720.0f);
	    gl.glMatrixMode(GL10.GL_MODELVIEW);
	    //Visual = new Video(744, 480);
		//Visual.doPerspective(gl, 720f);
	    gl.glMatrixMode(GL10.GL_MODELVIEW);
	    //Game.initialiseGame(mContext, gl);
	    //Game.drawSplash(mContext, gl);*/
	    new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(((GLActivity)context).getMAX_TIME_MS());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                Intent mIntent = new Intent();
				mIntent.putExtra(PerformanceTestActivity.THELASTTEST, ((GLActivity)context).isTheLast());
				((ThreeDActivity)context).setResult(ThreeDActivity.RESULT_OK, mIntent);
				System.out.println("saindo. Is it the last? " + false );
				((ThreeDActivity)context).finish();
                
                
            }
        }).start();
	}

	
	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		Log.e("GLTRON", "Renderer: Surface changed");
		sb=null;
		sb = new StringBuffer(40);
		Debug = sb.append("Screen size = ").append(w).append(",").append(h).toString();
		Log.e("GLTRON", Debug);
		updateScreenSize(w, h);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		
		//Initialise
		if(frameCount == 0){
		world = new WorldGraphics(gl, context, 720.0f);
		cam = new Camera(CamType.E_CAM_TYPE_CIRCLING);
		LightBike = new Model(context,R.raw.lightcyclehigh);
		frameCount++;
		
		}
		else{
		
		//run -> render
		
		UpdateTime();
		
		cam.doCameraMovement(TimeCurrent, TimeDt);

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		// Load identity
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		Visual.doPerspective(gl, mCurrentGridSize);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, cam.ReturnCamBuffer());

		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT | GL10.GL_STENCIL_BUFFER_BIT);
		gl.glEnable(GL10.GL_BLEND);

		cam.doLookAt(gl);
		
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glDisable(GL10.GL_BLEND);
		gl.glDepthMask(false);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		
		world.drawSkyBox(gl);
		world.drawFloorTextured(gl);
		
		gl.glDepthMask(true);
		gl.glEnable(GL10.GL_DEPTH_TEST);

		//if(mPrefs.DrawRecognizer())
			//mRecognizer.draw(gl, RecognizerModel);
		
		world.drawWalls(gl);
		
		Lights.setupLights(gl, LightType.E_WORLD_LIGHTS);
		
		
		drawingSomeBykes(((ThreeDActivity)context).getLevel(), 3.0f, gl);

		}
		
		

		
		/*
		//initWalls();
		
		word = new WorldGraphics(gl, context, 720f);

		

		// Setup perspective
	    //gl.glMatrixMode(GL10.GL_MODELVIEW);
		//Visual.doPerspective(gl, 720f);
	    //gl.glMatrixMode(GL10.GL_MODELVIEW);
	
	
	
	cam = new Camera(CamType.E_CAM_TYPE_CIRCLING);
	
	cam.doCameraMovement(System.currentTimeMillis(), SystemClock.currentThreadTimeMillis());

	gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	
	// Load identity
	gl.glMatrixMode(GL10.GL_PROJECTION);
	gl.glLoadIdentity();
	//Visual.doPerspective(gl, mCurrentGridSize);
	gl.glMatrixMode(GL10.GL_MODELVIEW);
	gl.glLoadIdentity();
	gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, cam.ReturnCamBuffer());

	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT | GL10.GL_STENCIL_BUFFER_BIT);
	gl.glEnable(GL10.GL_BLEND);

	cam.doLookAt(gl);
	
	gl.glDisable(GL10.GL_LIGHTING);
	gl.glDisable(GL10.GL_BLEND);
	gl.glDepthMask(false);
	gl.glDisable(GL10.GL_DEPTH_TEST);
	
	word.drawSkyBox(gl);
	word.drawFloorTextured(gl);
	
	gl.glDepthMask(true);
	gl.glEnable(GL10.GL_DEPTH_TEST);

	
	word.drawWalls(gl);
	
	Lights.setupLights(gl, LightType.E_WORLD_LIGHTS);
	    
		/*if(frameCount == 1)
		{
			//Game.initialiseGame();
		}
		else if(frameCount > 1)
		{
			//Game.RunGame();
		}

		frameCount++;*/
		
	}
	
	public Segment Walls[] = {
			new Segment(),
			new Segment(),
			new Segment(),
			new Segment()
	};
	
	private void initWalls()
	{
		float raw[][] = {
				{0.0f, 0.0f, 1.0f, 0.0f },
				{ 1.0f, 0.0f, 0.0f, 1.0f },
				{ 1.0f, 1.0f, -1.0f, 0.0f },
				{ 0.0f, 1.0f, 0.0f, -1.0f }
		};
		
		float width = 720f;
		float height = 720f;
		
		int j;
		
		for(j = 0; j < 4; j++)
		{
			Walls[j].vStart.v[0] = raw[j][0] * width;
			Walls[j].vStart.v[1] = raw[j][1] * height;
			Walls[j].vDirection.v[0] = raw[j][2] * width;
			Walls[j].vDirection.v[1] = raw[j][3] * height;
		}
	}

	private void UpdateTime()
	{
		long RealDt;
		int i;
		
		TimeLastFrame = TimeCurrent;
		TimeCurrent = SystemClock.uptimeMillis();
		RealDt = TimeCurrent - TimeLastFrame;
//		TimeDt = RealDt;
		
		DtHist[DtHead] = RealDt;
		
		DtHead++;
		
		if(DtHead >= MAX_SAMPLES)
		{
			DtHead = 0;
		}
		
		if(DtElements == MAX_SAMPLES)
		{
			// Average the last MAX_SAMPLE DT's
			TimeDt = 0;
			for(i = 0; i < MAX_SAMPLES; i++)
			{
				TimeDt += DtHist[i];
			}
			TimeDt /= MAX_SAMPLES;
		}
		else
		{
			TimeDt = RealDt;
			DtElements++;
		}
	}
	
	public void updateScreenSize(int width, int height)
	{
		if(Visual == null)
		{
			Visual = new Video(width, height);
		}
		else
		{
			Visual.SetWidthHeight(width, height);
		}
		

	}
	
	private void drawingSomeBykes(int level, float angle, GL10 gl){
		long time = SystemClock.currentThreadTimeMillis();
		for(int i = 0; i < level; i++)
		drawCycle(gl, time, time, i);
	}
	
	public void drawCycle(GL10 gl, long curr_time, long time_dt, int qnt)
	{
		gl.glPushMatrix();
		gl.glTranslatef(curr_time % 360, (qnt * 60) + 30.0f, 0.0f);

		doCycleRotation(gl,curr_time);

		//Lights.setupLights(gl, LightType.E_CYCLE_LIGHTS);
		
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthMask(true);
		//if(exp_radius == 0.0f)
		//{
			gl.glEnable(GL10.GL_NORMALIZE);
			gl.glTranslatef(0.0f, 0.0f, LightBike.GetBBoxSize().v[2] / 2.0f);
			gl.glEnable(GL10.GL_CULL_FACE);
			//gl.glTranslatef((GridSize/2.0f), (GridSize/2.0f), 0.0f);
			//gl.glTranslatef(_Player._PlayerXpos, _Player._PlayerYpos, 0.0f);
			LightBike.Draw(gl,ColourSpecular[qnt],ColourDiffuse[qnt]);
			gl.glDisable(GL10.GL_CULL_FACE);
		//}
		/*else if(exp_radius < EXP_RADIUS_MAX)
		{
			// Draw Crash if crashed..
			if(getExplode() != null)
			{
				if(getExplode().runExplode())
				{
					gl.glEnable(GL10.GL_BLEND);
	
					Explode.Draw(gl, time_dt, ExplodeTex);
				
					gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
					gl.glTranslatef(0.0f, 0.0f, Cycle.GetBBoxSize().v[2] / 2.0f);
					//LightBike.Explode(gl, _Player.getExplode().getRadius());
				}
			}
		}*/
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

	}
	
	private void doCycleRotation(GL10 gl, long CurrentTime)
	{
		  long time = CurrentTime;
		  float dirAngle;
		  float axis = 1.0f;
		  float Angle;
		  
		  //dirAngle = getDirAngle(time);
		  
		  gl.glRotatef(30, 0.0f, 0.0f, 1.0f);
	}
	
	
	
}
