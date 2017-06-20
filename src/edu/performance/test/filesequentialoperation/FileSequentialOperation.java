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
import edu.performance.test.PerformanceTest;


/**
 * 
 * @author Thiago
 */
public class FileSequentialOperation extends PerformanceTest<String> {

	private String dirName;
	private String stretch;
	//
	public FileSequentialOperation(PerformanceTestActivity activity, String stretch, string level) {
		super(level, activity);
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
		
		testTJMwriteSequentialFile(this.getLevel(),
				this.getStretch()); //TODO não testado ainda
	
		testTJMreadSequentialAcessFile(this.getLevel());
		
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);

	}


	/**
	 * This method tries to open a existent file determined by path and read it
	 * sequentially. It is missing to use level parameter.

	 * @param level
	 *            Determines the path to the file that will be sequentially
	 *            read.
	 */
	public String testTJMreadSequentialAcessFile(String level) {

		BufferedReader br = null;

		String text = "";
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					level)), 8192);
		} catch (FileNotFoundException e1) {

			System.out.println("Arquivo não encontrado: " + level);
			
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
	 * This method creates a directory structure and a file to write inside it a
	 * part of text determined by strecth.
	 * 
	 * @param level
	 *            Indicates the name of the file that will be created.
	 * @param stretch
	 *            Is the part of the text that will be written inside the file.
	 */
	public void testTJMwriteSequentialFile(String level,
			String stretch) {

		File file = new File(level);
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
