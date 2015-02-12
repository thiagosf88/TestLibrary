package edu.performance.test.filesequentialoperation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.StorageTests;

/**
 * This class extends ReadAndWrite because some new attributes are needed beside
 * those inherited from PerformanceTest and it performs file manipulation.
 * Currently the implemented operations are: - Sequentially Access -- read --
 * write - Randomly Access -- read -- write
 * 
 * @author Thiago
 */
public class FileSequentialOperation extends StorageTests {

	public FileSequentialOperation(PerformanceTestActivity activity, String filePath, String stretch, int level) {
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
	public FileSequentialOperation() {
		super(null, new Integer(0));
		
	}

	public void execute() {
		
		testTJMwriteSequentialFile(this.getFilePath(),
				this.getStretch()); // não testado ainda
	
		testTJMreadSequentialAcessFile(this.getFilePath());
		
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);

	}


	/**
	 * This method tries to open a existent file determined by path and read it
	 * sequentially. It is missing to use level parameter.
	 * 
	 * @param path
	 *            Determines the path to the file that will be sequentially
	 *            read.
	 * @param level
	 *            ??
	 */
	public String testTJMreadSequentialAcessFile(String filePath) {

		BufferedReader br = null;

		String text = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					filePath)), 8192);
		} catch (FileNotFoundException e1) {

			System.out.println("Arquivo não encontrado: " + filePath);
			
			return null;
		} // 2nd arg is buffer size

		try {
			String test;
			while (true) {
				test = br.readLine();
				// readLine() returns null if no more lines in the file

				if (test == null)
					break;

				text = text.concat(test);
				
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
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
	

	/**
	 * This method creates a directory structure and a file to write inside it a
	 * part of text determined by strecth.
	 * 
	 * @param filename
	 *            Indicates the name of the file that will be created.
	 * @param stretch
	 *            Is the part of the text that will be written inside the file.
	 */
	public void testTJMwriteSequentialFile(String filename,
			String stretch) {

		File file = new File(filename);
		// if file doesnt exists, then create it
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			// System.err.println(file.getAbsolutePath());
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(stretch);
			bw.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
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
