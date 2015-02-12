package edu.performance.test.filerandomoperation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.StorageTests;

/**
 * This class extends ReadAndWrite because some new attributes are needed beside
 * those inherited from PerformanceTest and it performs file manipulation.
 * Currently the implemented operations are: Randomly Access -- read -- write
 * 
 * @author Thiago
 */
public class FileRandomOperation extends StorageTests {

	public FileRandomOperation(PerformanceTestActivity activity, String filePath, String stretch, int level) {
		super(activity, level);
		this.setFilePath(filePath);
		this.setStretch(stretch);
		if(activity != null)
			activity.executeTest();
		else
			System.err.println("File Activity is null!!!");
	}
	/**
	 * This constructor can not be used to instance objects which will run tests.
	 */
	public FileRandomOperation() {
		super(null, new Integer(0));
		
	}

	public void execute() {
		
		
		testTJMreadRandomAcessFile(this.getFilePath(), this.getPositions()[ (this.getLevel() < this.getPositions().length ? this.getLevel() : this.getPositions().length - 1)],
				 (this.getLevel() < this.getPositions().length ? this.getLevel() : this.getPositions().length - 1));
		testTJMwriteRandowAcessFile(this.getFilePath(), this.getPositions()[ (this.getLevel() < this.getPositions().length ? this.getLevel() : this.getPositions().length - 1)],
				this.getStretch());

		
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);

	}


	/**
	 * This method tries to open a existent file determined by path and read it
	 * randomly starting in "position" and put text inside a byte array whose
	 * length is level.
	 * 
	 * @param path
	 *            Determines the path to the file that will be randomly read.
	 * @param positions
	 *            Determines the position where will be started the read.
	 * @param level
	 *            Determines the size of byte array that will receive the read
	 *            text.
	 */
	public void testTJMreadRandomAcessFile(String filename, int position,
			int level) {

		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(filename, "r");

			// Declare a buffer with the same length as the second line
			randomAccessFile.seek(position);
			byte[] buffer = new byte[level * 1024];

			// Read data from the file
			randomAccessFile.read(buffer);

			// Print out the buffer contents
			 System.out.println(new String(buffer));

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (randomAccessFile != null)
					randomAccessFile.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * This method tries to open a existent file determined by path and writes
	 * the "stretch" in position determined by "positions" parameters.
	 * 
	 * @param path
	 *            Determines the path to the file that will be randomly read.
	 * @param positions
	 *            Indicates the position where the stretch will be written.
	 * @param stretch
	 *            Is the part of text that will be written in the file.
	 */
	public void testTJMwriteRandowAcessFile(String filename, int position,
			String stretch) {

		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(filename, "rw");
			randomAccessFile.seek(position);

			randomAccessFile.writeBytes(stretch);

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (randomAccessFile != null)
					randomAccessFile.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	
/**
 * This is a auxiliary method to create some files which will be necessary in some tests.
 * @param appRef Activity reference to get raw resources
 * @param rawResourceId Unique Identifier to file resources
 * @param resourceName Name given to the file
 * @param dirName Directory name where the file will be created
 * @return Return true if the file was successfully created.
 */
	public static boolean testTJMWriteFile(Library appRef, int rawResourceId,
			String resourceName, String dirName) {

		InputStream in = null;
		FileOutputStream out = null;
		// File file = new File(performanceDirectory, filename);
		byte[] buff = new byte[1024];
		// long file_size = -1;
		int read = 0;

		try {
			in = appRef.getResources().openRawResource(rawResourceId);
			System.out.println("Creating: " + dirName + "/" + resourceName);
			out = new FileOutputStream(dirName + "/" + resourceName);

			while ((read = in.read(buff)) > 0) {
				out.write(buff, 0, read);

			}

			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

}
