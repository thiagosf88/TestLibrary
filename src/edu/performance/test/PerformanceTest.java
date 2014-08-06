package edu.performance.test;

/**
 * This abstract class contains a attribute that will be needed in almost of the
 * executed tests in this application and her childs should implement the
 * execute method.
 * 
 * @author Thiago
 * 
 * @param <T>
 *            Determines the type of level.
 */
public abstract class PerformanceTest<T> {

	private T level;
	
	protected PerformanceTestActivity activity;

	public PerformanceTest(T level, PerformanceTestActivity activity) {
		super();
		this.level = level;
		this.activity = activity;
	}

	public T getLevel() {
		return level;
	}

	public void setLevel(T level) {
		this.level = level;
	}

	public abstract void execute();

}
