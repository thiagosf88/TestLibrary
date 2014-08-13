package edu.performance.test;

/**
 * This class extends PerformanceTest<Integer> to provide some other attributes
 * needed to manipulate files.
 * 
 * @author thiago
 * 
 */
public abstract class StorageTests extends PerformanceTest<Integer> {

	private String filePath;
	private String dirName;
	private String stretch;
	private int positions[];
	private int offset;
	
	public StorageTests(PerformanceTestActivity activity) {
		super(new Integer(1), activity);
		filePath = "";
		offset = 10000;
		positions = new int[] { 20000, 40000, 100000 };
		
	}

	public StorageTests(PerformanceTestActivity activity, int level, String filePath, String stretch,
			int[] positions, int offset) {
		super(level, activity);
		this.filePath = filePath;
		this.stretch = stretch;
		this.positions = positions;
		this.offset = offset;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getStretch() {
		return stretch;
	}

	public void setStretch(String stretch) {
		this.stretch = stretch;
	}

	public int[] getPositions() {
		return positions;
	}

	public void setPositions(int[] positions) {
		this.positions = positions;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
