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

		testTJMsearchString(snippets[this.getLevel()]);
		testTJMsearchString(snippets[this.getLevel() + 3]);
		testTJMsearchString(snippets[this.getLevel() + 6]);
		testTJMconcatString(searchable, snippets[this.getLevel()]);
		testTJMconcatString( snippets[this.getLevel()], searchable);
		testTJMreplaceString(searchable, snippets[this.getLevel()], snippets[this.getLevel() + 6]);
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
	}

	/**
	 * This method searches the substring strecth inside another string,
	 * using String library.
	 * 
	 * @param strecth
	 *            It is the searched substring.
	 */
	private void testTJMsearchString(String strecth) {

		searchable.indexOf(strecth);

	}
	/**
	 * This method concatenates the second parameter after the first one, using
	 * String library.
	 * @param first The begin of concatenated string.
	 * @param second The end of concatenated string.
	 */
	private void testTJMconcatString(String first, String second){
		first.concat(second);
	}
	
	private void testTJMreplaceString(String base, String replaceable, String replace){
		base.replace(replaceable, replace);
	}
	

	public void setSearchable(String searchable) {
		this.searchable = searchable;
	}

	public void setSnippets(String[] snippets) {
		this.snippets = snippets;
	}
	
	
}
