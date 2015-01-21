package edu.performance.test.graphoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;
import edu.performance.test.fileoperation.FileOperation;
import edu.performance.test.graphoperation.legacy.DijkstraSP;
import edu.performance.test.graphoperation.legacy.DirectedEdge;
import edu.performance.test.graphoperation.legacy.EdgeWeightedDigraph;
import edu.performance.test.graphoperation.legacy.In;

public class GraphOperation extends PerformanceTest<String> implements
		PerformanceTestInterface {
	
	DijkstraSP d;
	public GraphOperation(PerformanceTestActivity activity, String level) {
		super(level, activity);
		if(activity != null)
			activity.executeTest();
		
		
	}

	@Override
	public void execute() {
		In in = null;
		try{
		 in = new In(this.getLevel());
		}
		catch (Exception e) {
			Bundle extras = new Bundle();	
			
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			activity.finishTest(extras);
		}
		EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(in);
		
		
		
		
        
		//d = new DijkstraSP(eg);
		testTJMShorterPathDijstra(ewd, this.getLevel());
		
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
		
	}
	
	@SuppressWarnings("unused")
	private void testTJMShorterPathDijstra(EdgeWeightedDigraph d, String level){
		//tiny_g
		int source = 0;
	
		
		DijkstraSP sp = new DijkstraSP(d, source);
		
		for (int t = 0; t < d.V(); t++) {
            if (sp.hasPathTo(t)) {
                //System.out.printf("%d to %d (%.2f)  ", source, t, sp.distTo(t));
                if (sp.hasPathTo(t)) {
                    for (DirectedEdge e : sp.pathTo(t)) {
                    	//System.out.print(e + "   ");
                    }
                }
                //System.out.println();
            }
            else {
            	System.out.printf("%d to %d         no path\n", source, t);
            }
		
	}
	}

}
