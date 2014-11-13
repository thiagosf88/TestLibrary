package edu.performance.test.graphicoperation;

import edu.performance.test.graphicoperation.threed.CubeTextureGL10Activity;
import edu.performance.test.graphicoperation.threed.CubeTextureGL10Renderer;
import edu.performance.test.graphicoperation.threed.TeapotActivity;
import edu.performance.test.graphicoperation.threed.TeapotRenderer;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class Operation3d extends GLSurfaceView{
	
		private  CubeTextureGL10Renderer renderer;
		private TeapotRenderer renderer2;
		
		public Operation3d (Context context) {
			super(context);
			
			if(context instanceof CubeTextureGL10Activity){
			renderer = new CubeTextureGL10Renderer(context);
			setRenderer(renderer);
			}
			
			if(context instanceof TeapotActivity){
				renderer2 = new TeapotRenderer(context);
				setRenderer(renderer2);
				}
		}
	}


