package edu.performance.test.stringoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

/**
 * This class extends PerformanceTest<Integer> and it executes operations with
 * strings. Currently the implemented operations are: - string (library)
 * 
 * @author Thiago
 */
public class StringOperation extends PerformanceTest<Integer> {

	private String searchable;

	String snippets[];

	public StringOperation(PerformanceTestActivity activity, String searchable,
			String[] snippets, Integer level) {
		super(level, activity);
		this.searchable = searchable;
		this.snippets = snippets;
		activity.executeTest();

	}

	@Override
	public void execute() {

		//TODO FIXME criar operações de manipulacao de strings concat, replace,
		

		testTJMsearchString(snippets[this.getLevel()]);
		testTJMsearchString(snippets[this.getLevel() + 3]);
		testTJMsearchString(snippets[this.getLevel() + 6]);

		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
	}

	/**
	 * This method searches the substring strecth inside another string.
	 * 
	 * @param strecth
	 *            It is the searched substring.
	 */
	private void testTJMsearchString(String strecth) {

		System.out.println(searchable.indexOf(strecth) + " " + strecth);

	}

	public void setSearchable(String searchable) {
		this.searchable = searchable;
	}

	public void setSnippets(String[] snippets) {
		this.snippets = snippets;
	}
}
