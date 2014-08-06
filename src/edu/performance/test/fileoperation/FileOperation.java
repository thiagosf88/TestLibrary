package edu.performance.test.fileoperation;

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

import android.content.res.Resources.NotFoundException;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.ReadAndWrite;

/**
 * This class extends ReadAndWrite because some new attributes are needed beside
 * those inherited from PerformanceTest and it performs file manipulation.
 * Currently the implemented operations are: - Sequentially Access -- read --
 * write - Randomly Access -- read -- write
 * 
 * @author Thiago
 */
public class FileOperation extends ReadAndWrite {

	public FileOperation(PerformanceTestActivity activity, String filePath, String stretch) {
		super(activity);
		this.setFilePath(filePath);
		this.setStretch(stretch);
		activity.executeTest();
	}
	/**
	 * This constructor can not be used to instance objects which will run tests.
	 */
	public FileOperation() {
		super(null);
		
	}

	public void execute() {
		testJreadSequentialAcessFile(this.getFilePath(), this.getLevel());
		testJwriteSequentialFile(this.getDirName(), this.getFilePath(),
				this.getStretch()); // não testado ainda
		testJreadRandomAcessFile(this.getFilePath(), this.getPositions()[0],
				this.getLevel());
		testJwriteRandowAcessFile(this.getFilePath(), this.getPositions()[0],
				this.getStretch());
		
		activity.finishTest(null);

	}

	/*
	 * @Override public void onCreate(Bundle savedInstanceState) {
	 * 
	 * super.onCreate(savedInstanceState);
	 * //setContentView(R.layout.activity_read_and_write); Randomly randomly =
	 * new Randomly();
	 * 
	 * /** Here data array has that schema [0] times [1] offset
	 * 
	 * 
	 * 
	 * 
	 * randomly.writeRandomAccessFile(Environment.getExternalStorageDirectory().
	 * getAbsolutePath() + "/big_escrever.txt", positions[0], stretch);
	 * System.out.println("fim da escrita"); for(int i = 0; i <
	 * Integer.parseInt(data[0]);i++){
	 * randomly.readRandomAccessFile(Environment.
	 * getExternalStorageDirectory().getAbsolutePath() + "/big.txt",
	 * positions[i], Integer.parseInt(data[1]));
	 * 
	 * } System.out.println("terminando"); finish(); }
	 */
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
	public String testJreadSequentialAcessFile(String filePath, int level) {

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
	public void testJreadRandomAcessFile(String filename, int position,
			int level) {

		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(filename, "r");

			// Declare a buffer with the same length as the second line
			randomAccessFile.seek(position);
			byte[] buffer = new byte[level];

			// Read data from the file
			randomAccessFile.read(buffer);

			// Print out the buffer contents
			// System.out.println(new String(buffer));

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
	public void testJwriteRandowAcessFile(String filename, int position,
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
	 * @param dirName
	 *            Determines the name of folder where the file will be created.
	 * @param filename
	 *            Indicates the name of the file that will be created.
	 * @param stretch
	 *            Is the part of the text that will be written inside the file.
	 */
	public void testJwriteSequentialFile(String dirName, String filename,
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
