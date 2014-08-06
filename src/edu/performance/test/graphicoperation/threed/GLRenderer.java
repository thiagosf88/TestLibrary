/**
 * Copyright 2013 Per-Erik Bergman (per-erik.bergman@jayway.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.performance.test.graphicoperation.threed;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView.Renderer;
import edu.performance.test.Library;
import edu.performance.test.graphicoperation.ThreeDActivity;




public abstract class GLRenderer implements Renderer {

    private boolean mFirstDraw;
    private boolean mSurfaceCreated;
    private int mWidth;
    private int mHeight;
    private long mLastTime;
    private int mFPS;
    Context context;

    public GLRenderer(Context context) {
        mFirstDraw = true;
        mSurfaceCreated = false;
        mWidth = -1;
        mHeight = -1;
        mLastTime = System.currentTimeMillis();
        mFPS = 0;
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 notUsed,
            EGLConfig config) {
    	new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(((GLES20Activity)context).getMAX_TIME_MS());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                Intent mIntent = new Intent();
				mIntent.putExtra(Library.THELASTTEST, ((ThreeDActivity)context).isTheLast() );
				((ThreeDActivity)context).setResult(ThreeDActivity.RESULT_OK, mIntent);
				System.out.println("saindo. Is it the last? " + ((ThreeDActivity)context).isTheLast() );
				((ThreeDActivity)context).finish();
                
                
            }
        }).start();
        mSurfaceCreated = true;
        mWidth = -1;
        mHeight = -1;
    }

    @Override
    public void onSurfaceChanged(GL10 notUsed, int width,
            int height) {
        if (!mSurfaceCreated && width == mWidth && height == mHeight) {
            
            return;
        }
        

        mWidth = width;
        mHeight = height;

        onCreate(mWidth, mHeight, mSurfaceCreated);
        mSurfaceCreated = false;
    }

    @Override
    public void onDrawFrame(GL10 notUsed) {
        onDrawFrame(mFirstDraw);

        
            mFPS++;
            long currentTime = System.currentTimeMillis();
            if (currentTime - mLastTime >= 1000) {
            	System.out.println("nada de FPS: " + mFPS / ((currentTime - mLastTime) / 1000f));
                mFPS = 0;
                mLastTime = currentTime;
            }
        

        if (mFirstDraw) {
            mFirstDraw = false;
        }
    }

    public int getFPS() {
        return mFPS;
    }

    public abstract void onCreate(int width, int height,
            boolean contextLost);

    public abstract void onDrawFrame(boolean firstDraw);
}