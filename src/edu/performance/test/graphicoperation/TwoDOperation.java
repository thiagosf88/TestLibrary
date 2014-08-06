package edu.performance.test.graphicoperation;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

public class TwoDOperation extends PerformanceTest<Integer> {

	List<Intent> tests;

	public TwoDOperation(PerformanceTestActivity activity) {
		super(new Integer(1), activity);

		tests = new ArrayList<Intent>();
	}

	
	
	@Override
	public void execute() {		

	}



	public List<Intent> getTests() {
		return tests;
	}

}
