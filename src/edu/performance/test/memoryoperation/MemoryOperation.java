package edu.performance.test.memoryoperation;

import java.util.ArrayList;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

/**
 * This class Java code to manipulate heap spaces. The operations performed are:
 * - Array of Object creation - ArrayList of Objects creation - Array of Object
 * copy - ArrayList of Objects copy
 * 
 * @author Thiago
 */
public class MemoryOperation extends PerformanceTest<Integer> {

	private ArrayList<Object> arrayOfThings = null;
	private Object[] arrayOfObjects = null;
	private Object content = null;

	public MemoryOperation(PerformanceTestActivity activity, int level) {
		super(level, activity);
		activity.executeTest();
	}

	@Override
	public void execute() {
		
		testTJMcreateObjectArray(this.getContent(), this.getLevel());
		
		testTJMcreateObjectArrayList(this.getContent(), this.getLevel());
		
		testTJMcopyObjectArray();
		
		testTJMcopyObjectArrayList();
		
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
	}

	/**
	 * This method performs a creation of Object ArrayList with size equal to
	 * level and put "content" inside each arraylist index.
	 * 
	 * @param content
	 *            Object that will be put in the arrayList.
	 * @param level Determines the size of Array
	 */
	private void testTJMcreateObjectArrayList(Object content, int level) {

		arrayOfThings = new ArrayList<Object>();

		for (int i = 0; i < level; i++) {
			arrayOfThings.add(content);
		}

	}

	/**
	 * This method performs a creation of Object array with size equal to level
	 * and put "content" inside each arraylist index.
	 * 
	 * @param content
	 *            Object that will be put in the array.
	 * @param level Determines the size of Array
	 */
	private void testTJMcreateObjectArray(Object content, int level) {

		arrayOfObjects = new Object[level];

		for (int i = 0; i < level; i++) {
			arrayOfObjects[i] = new Object();
			arrayOfObjects[i] = content;
		}

	}

	/**
	 * This method performs a copy of Object array to a new Object array.
	 * 
	 * @param content
	 *            Object that will be put in the array.
	 */
	private void testTJMcopyObjectArray() {
		Object[] arrayT = new Object[arrayOfObjects.length];

		for (int i = 0; i < arrayT.length; i++) {
			arrayT[i] = arrayOfObjects[i];
		}

	}

	/**
	 * This method performs a copy of Object ArrayList to a new Object
	 * ArrayList.
	 * 
	 * @param content
	 *            Object that will be put in the ArrayList.
	 */
	private void testTJMcopyObjectArrayList() {
		ArrayList<Object> arrayT = new ArrayList<Object>(this.getLevel());

		for (int i = 0; i < arrayT.size(); i++) {
			arrayT.add(arrayT.get(i));
		}
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Object getContent() {
		return content;
	}
}
