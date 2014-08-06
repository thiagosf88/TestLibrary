package edu.performance.test.nativo.stringoperation;

import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

/**
 * This class extends PerformanceTest<Integer> and it executes operations with
 * strings using native codes. Currently the implemented operations are: -
 * string (library)
 * 
 * @author Thiago
 */
public class StringOperationNative extends PerformanceTest<Integer> {

	private String searchable;

	String snippets[];

	public StringOperationNative(PerformanceTestActivity activity, String searchable, String [] snippets) {
		super(new Integer(0), activity);
		this.snippets = snippets;
		this.searchable = searchable;
		activity.executeTest();

	}

	@Override
	public void execute() {

		testTNMsearchString(searchable, snippets[this.getLevel()]);
		testTNMsearchString(searchable, snippets[this.getLevel() + 3]);
		testTNMsearchString(searchable, snippets[this.getLevel() + 6]);
		
		activity.finishTest(null);
	}

	/**
	 * This method searches the substring strecth inside another string. This
	 * method is a call to native c/c++ function.
	 * 
	 * @param strecth
	 *            It is the searched substring.
	 */
	private native void testTNMsearchString(String searchable, String strecth);

	public void setSearchable(String searchable) {
		this.searchable = searchable;
	}

	public void setSnippets(String[] snippets) {
		this.snippets = snippets;
	}

}
