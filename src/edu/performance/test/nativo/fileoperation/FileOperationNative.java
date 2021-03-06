package edu.performance.test.nativo.fileoperation;

import android.os.Bundle;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.StorageTests;

/**
 * This class performs file manipulation. Currently the implemented operations
 * are: - Sequentially Access -- read - Randomly Access -- read -- write
 * 
 * @author Thiago
 */
public class FileOperationNative extends StorageTests {

	public FileOperationNative(PerformanceTestActivity activity, String filePath, String stretch, int level) {
		super(activity, level);
		this.setFilePath(filePath);
		this.setStretch(stretch);
		activity.executeTest();
	}

	public void execute() {
		// System.out.println("rsaf");
		testNreadSequentialAcessFile(this.getFilePath(), (this.getLevel() < this.getPositions().length ? this.getLevel() : this.getPositions().length - 1));
		// System.out.println("rraf");
		//TODO fazer a leitura sequencial em c++
		testNreadRandomAcessFile(this.getFilePath(), this.getPositions()[ (this.getLevel() < this.getPositions().length ? this.getLevel() : this.getPositions().length - 1)],
				this.getLevel() * 1024);
		// System.out.println("wraf");
		testNwriteRandomAcessFile(this.getFilePath(), this.getPositions()[ (this.getLevel() < this.getPositions().length ? this.getLevel() : this.getPositions().length - 1)],
				this.getStretch());
		
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);

	}

	/**
	 * This method tries to open a existent file determined by path and read it
	 * sequentially. It is missing to use level parameter. This method is a call
	 * to native c/c++ function.
	 * 
	 * @param path
	 *            Determines the path to the file that will be sequentially
	 *            read.
	 * @param level
	 */
	public native void testNreadSequentialAcessFile(String path, int level);

	/**
	 * This method tries to open a existent file determined by path and read it
	 * randomly starting in "position" and put text inside a string variable.
	 * The read stops when end of file is reached or limited by level. This
	 * method is a call to native c/c++ function.
	 * 
	 * @param path
	 *            Determines the path to the file that will be randomly read.
	 * @param positions
	 *            Determines the position where will be started the read.
	 * @param level
	 *            Determines the size of string variable that will receive the
	 *            read text.
	 */
	public native void testNreadRandomAcessFile(String path, int positions,
			int level);

	/**
	 * This method tries to open a existent file determined by path and writes
	 * the "stretch" in position determined by "positions" parameters. This
	 * method is a call to native c/c++ function.
	 * 
	 * @param path
	 *            Determines the path to the file that will be randomly read.
	 * @param positions
	 *            Indicates the position where the stretch will be written.
	 * @param stretch
	 *            Is the part of text that will be written in the file.
	 */
	public native void testNwriteRandomAcessFile(String path, int positions,
			String stretch);

}
