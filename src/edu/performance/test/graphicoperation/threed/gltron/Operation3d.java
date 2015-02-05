package edu.performance.test.graphicoperation.threed.gltron;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class Operation3d extends GLSurfaceView{
	
		private  Renderer renderer;
		
		public Operation3d (Context context) {
			super(context);
			
			
			if(context instanceof GLActivity){
				renderer = new OpenGLRenderer(context);
				setRenderer(renderer);
				}
			
		}
	}


