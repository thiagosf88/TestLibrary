package edu.performance.test.graphicoperation.threed;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import edu.performance.test.Library;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.ThreeDActivity;

public class CubeTextureGL10Renderer implements Renderer {
		private static final String TAG = "GLRenderer";
		private final Context context;
		//private int level = 100; //1000 já é lento no s3
		private final MyCube cube3 = new MyCube();
		private long startTime;
		private long fpsStartTime;
		private long numFrames;
		
		public CubeTextureGL10Renderer(Context context) {
			//super(context);
			this.context = context;
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) 
		{
			boolean SEE_THRU = true;
//Migrar esse controle para forma mais geral
			new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    Thread.sleep(((CubeTextureGL10Activity)context).getMAX_TIME_MS());
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                
	                Intent mIntent = new Intent();
					mIntent.putExtra(Library.THELASTTEST, ((CubeTextureGL10Activity)context).isTheLast());
					((ThreeDActivity)context).setResult(ThreeDActivity.RESULT_OK, mIntent);
					System.out.println("saindo. Is it the last? " + false );
					((ThreeDActivity)context).finish();
	                
	                
	            }
	        }).start();
			startTime = System.currentTimeMillis();
			fpsStartTime = startTime;
			numFrames = 0;
			
			
			
			// Define the lighting
			float lightAmbient[] = new float[] { 0.2f, 0.2f, 0.2f, 1 };
			float lightDiffuse[] = new float[] { 1, 1, 1, 1 };
			float[] lightPos = new float[] { 1, 1, 1, 1 };
			gl.glEnable(GL10.GL_LIGHTING);
			gl.glEnable(GL10.GL_LIGHT0);
			gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
			gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, lightDiffuse, 0);
			gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, lightPos, 0);
			// What is the cube made of?
			float matAmbient[] = new float[] { 1, 1, 1, 1 };
			float matDiffuse[] = new float[] { 1, 1, 1, 1 };
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
					matAmbient, 0);
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
					matDiffuse, 0);
			// Set up any OpenGL options we need
			gl.glEnable(GL10.GL_DEPTH_TEST); 
			gl.glDepthFunc(GL10.GL_LEQUAL);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

			if (SEE_THRU) {
				gl.glDisable(GL10.GL_DEPTH_TEST);
				gl.glEnable(GL10.GL_BLEND);
				gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
			}


			// Enable textures
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glEnable(GL10.GL_TEXTURE_2D);

			// Load the cube's texture from a bitmap
			MyCube.loadTexture(gl, context, R.drawable.icon);
		}

		public void onSurfaceChanged(GL10 gl, int width, int height)
		{

			// Define the view frustum
			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			float ratio = (float) width / height;
			GLU.gluPerspective(gl, 45.0f, ratio, 1, 100f); 

		}
		public void onDrawFrame(GL10 gl) {
			// Clear the screen to black
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT
					| GL10.GL_DEPTH_BUFFER_BIT);
			long elapsed = System.currentTimeMillis() - startTime;
			
			/*// Position model so we can see it
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glTranslatef(0, 0, -10.0f);

			// Set rotation angle based on the time
			long elapsed = System.currentTimeMillis() - startTime;
			gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
			gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);

			// Draw the model
			cube.draw(gl);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glTranslatef(3f, 3f, -20.0f);
			
			elapsed = System.currentTimeMillis() - startTime;
			gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
			gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);
			cube2.draw(gl);
			
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			gl.glTranslatef(1f, -1f, -5.0f);
			
			elapsed = System.currentTimeMillis() - startTime;
			gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
			gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);
			cube3.draw(gl);
			*/
			
			for(int i = 0; i < ((ThreeDActivity)context).getLevel(); i++){
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glTranslatef(-0.5f , -0.5f , -(i%((ThreeDActivity)context).getLevel()));
				
				elapsed = System.currentTimeMillis() - startTime;
				gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
				gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				cube3.draw(gl);
			}
			for(int i = 0; i < ((ThreeDActivity)context).getLevel(); i++){
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glTranslatef(0.5f , 0.5f , -(i%((ThreeDActivity)context).getLevel()));
				
				elapsed = System.currentTimeMillis() - startTime;
				gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
				gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				cube3.draw(gl);
			}
			for(int i = 0; i < ((ThreeDActivity)context).getLevel(); i++){
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glTranslatef(-0.5f , 0.5f , -(i%((ThreeDActivity)context).getLevel()));
				
				elapsed = System.currentTimeMillis() - startTime;
				gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
				gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				cube3.draw(gl);
			}
			for(int i = 0; i < ((ThreeDActivity)context).getLevel(); i++){
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glTranslatef(0.5f , -0.5f , -(i%((ThreeDActivity)context).getLevel()));
				
				elapsed = System.currentTimeMillis() - startTime;
				gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
				gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				cube3.draw(gl);
			}
			for(int i = 0; i < ((ThreeDActivity)context).getLevel(); i++){
				gl.glMatrixMode(GL10.GL_MODELVIEW);
				gl.glLoadIdentity();
				gl.glTranslatef(0 , i*(((ThreeDActivity)context).getLevel()/2)/((ThreeDActivity)context).getLevel() , -((i%((ThreeDActivity)context).getLevel()) + ((ThreeDActivity)context).getLevel()));
				
				elapsed = System.currentTimeMillis() - startTime;
				gl.glRotatef(elapsed * (30f / 1000f), 0, 1, 0);
				gl.glRotatef(elapsed * (15f / 1000f), 1, 0, 0);
				gl.glScalef(0.5f, 0.5f, 0.5f);
				cube3.draw(gl);
			}
			
			// Keep track of number of frames drawn
			numFrames++;
			long fpsElapsed = System.currentTimeMillis() - fpsStartTime;
			if (fpsElapsed > 5 * 1000) { // every 5 seconds
				float fps = (numFrames * 1000.0F) / fpsElapsed;
				Log.d(TAG, "Frames per second: " + fps + " (" + numFrames
						+ " frames in " + fpsElapsed + " ms)");
				fpsStartTime = System.currentTimeMillis();
				numFrames = 0;
			}

		}	
		
	}


