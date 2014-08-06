package edu.performance.test.graphicoperation;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class Operation3d extends GLSurfaceView{
	
		private  CubeRenderer renderer;
		private TeapotRenderer renderer2;
		
		public Operation3d (Context context) {
			super(context);
			
			if(context instanceof CubeActivity){
			renderer = new CubeRenderer(context);
			setRenderer(renderer);
			}
			
			if(context instanceof TeapotActivity){
				renderer2 = new TeapotRenderer(context);
				setRenderer(renderer2);
				}
		}
	}


