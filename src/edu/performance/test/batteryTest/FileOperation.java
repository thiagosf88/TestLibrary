package com.fsck.k9.controller;

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
import java.io.RandomAccessFile;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;


/**
 * This class extends ReadAndWrite because some new attributes are needed beside
 * those inherited from PerformanceTest and it performs file manipulation.
 * Currently the implemented operations are: - Sequentially Access -- read --
 * write - Randomly Access -- read -- write
 * 
 * @author Thiago
 */
public class FileOperation {

	


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

			e1.printStackTrace();
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
	public static boolean testTJMWriteFile(Activity appRef, int rawResourceId,
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
