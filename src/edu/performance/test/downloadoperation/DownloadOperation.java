package edu.performance.test.downloadoperation;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.util.WriteNeededFiles;

/**
 * This class extends AsyncTask and it executes downloading files from oi
 * website. This Class do not get features from PerformanceTest and it has a
 * attribute named level. Operations like that must be implemented using
 * background tasks.
 * 
 * @author Thiago
 */
class DownloadOperation extends PerformanceTest<Integer>{

	public DownloadOperation(PerformanceTestActivity activity, int level) {
		super(level, activity);

		activity.executeTest();
	}

	String[] urls = { "M1.zip", "M10.zip", "M50.zip", "M500.zip" };

	public void execute() {
		
		testAdownloadOperation(this.getLevel());
		
	}
	/**
	 * This method performs downloading files from the website
	 * arquivos.oi.com.br
	 * 
	 * @param level
	 *            Determines the size of the file will be downloaded
	 */
	public void testAdownloadOperation(int level) {
		int count;
		level = level < urls.length - 1 ? level : urls.length - 1;
		try {
			URL url = new URL("http://arquivos.oi.com.br/" + urls[level]);
			URLConnection conection = url.openConnection();
			conection.connect();

			// getting file length
			// int lenghtOfFile = conection.getContentLength();

			// input stream to read file - with 8k buffer
			InputStream input = new BufferedInputStream(url.openStream(), 8192);

			// Output stream to write file
			OutputStream output = new FileOutputStream(
					WriteNeededFiles.DIRECTORY_NAME + "/downloadedfile.zip");

			byte data[] = new byte[1024];

			// long total = 0;

			while ((count = input.read(data)) != -1) {
				// total += count;
				// publishing the progress....
				// After this onProgressUpdate will be called

				// System.out.println(((total*100)/lenghtOfFile));
				// writing data to file
				output.write(data, 0, count);
			}

			// flushing output
			output.flush();

			// closing streams
			output.close();
			input.close();
			
			Bundle extras = new Bundle();			
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
			activity.finishTest(extras);
		} catch (IOException ioe) {
			
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			
			activity.finishTest(extras);
			System.err.println("Error in download operation: " + ioe.getMessage());
		}
	}

}
