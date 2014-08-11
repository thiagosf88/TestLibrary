package edu.performance.test.graphoperation;

import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.PerformanceTestInterface;
import edu.performance.test.graphoperation.legacy.DijkstraSP;
import edu.performance.test.graphoperation.legacy.DirectedEdge;
import edu.performance.test.graphoperation.legacy.EdgeWeightedDigraph;
import edu.performance.test.graphoperation.legacy.In;
import edu.performance.test.util.WriteNeededFiles;

public class GraphOperation extends PerformanceTest<Integer> implements
		PerformanceTestInterface {
	
	DijkstraSP d;
	public GraphOperation(Integer level, PerformanceTestActivity activity) {
		super(level, activity);
		if(activity != null)
			activity.executeTest();
		
		
	}

	@Override
	public void execute() {
		
		In in = new In(WriteNeededFiles.DIRECTORY_NAME + "/medium_g.txt");
		EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(in);
		System.out.println(ewd);
		activity.finishTest(null);
		
        
		//d = new DijkstraSP(eg);
		testTJMShorterPathDijstra(ewd);
		
	}
	
	private void testTJMShorterPathDijstra(EdgeWeightedDigraph d){
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
