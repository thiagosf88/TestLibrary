package edu.performance.test.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Environment;
import edu.performance.test.Library;

public class WriteNeededFiles {

	public static String DIRECTORY_NAME = Environment
			.getExternalStorageDirectory() + "/" + "performanceTestDir";
	public static String DIRECTORY_NAME_INT = "";

	public static String REPORT_DIRECTORY_NAME = Environment
			.getExternalStorageDirectory() + "/" + "performanceTestReports";
	public static String REPORT_DIRECTORY_NAME_INT = "/data/data/edu.performance.test/app_performanceDir/";

	public static boolean putFilesOnStorage(Library appRef,
			int[] rawResourceIds, String[] rawResourceNames, String dirName) {

		InputStream in = null;
		FileOutputStream out = null;

		// File file = new File(performanceDirectory, filename);
		byte[] buff = new byte[1024];
		int read = 0, id = 0;
		for (int i = 0; i < rawResourceIds.length; i++) {
			id = rawResourceIds[i];
			try {
				in = appRef.getResources().openRawResource(id);
				System.out.println("Creating: " + dirName + "/"
						+ rawResourceNames[i]);
				out = new FileOutputStream(dirName + "/" + rawResourceNames[i]);

				while ((read = in.read(buff)) > 0) {
					out.write(buff, 0, read);
				}
				in.close();
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return true;
	}

	public static HashMap<String, String> makeDirectories(Library appRef) {
		HashMap<String, String> dirs = new HashMap<String, String>();
		File reportDirectory = new File(REPORT_DIRECTORY_NAME);

		if (reportDirectory.mkdirs())
			dirs.put("RPD", reportDirectory.getAbsolutePath());

		File performanceDirectory = new File(DIRECTORY_NAME);
		if (performanceDirectory.mkdirs())
			dirs.put("PDE", performanceDirectory.getAbsolutePath());

		File performanceDirectoryInternal = appRef.getDir("performanceDir",
				Context.MODE_PRIVATE);

		if (performanceDirectoryInternal.exists()) {
			dirs.put("PDI", performanceDirectoryInternal.getAbsolutePath());
			System.err.println("nome: " + dirs.get("PDI"));
			DIRECTORY_NAME_INT = dirs.get("PDI");
		} else
			System.err.println("Não criou");

		return dirs;
	}

	public static boolean deleteFiles(Library appRef, String[] rawResourceNames) {

		File performanceDirectory = new File(DIRECTORY_NAME);
		File performanceDirectoryInt = new File(DIRECTORY_NAME_INT);

		File file = null, file2 = null;
		boolean result = true, result2 = true;

		for (String files : rawResourceNames) {
			file = new File(performanceDirectory.getAbsolutePath() + "/"
					+ files);
			file2 = new File("/data/data/edu.performance.test/app_performanceDir/"
					+ files);
			result &= file.delete();
			result2 &= file2.delete();
			System.err
					.println(file.getAbsolutePath() + " : deleted? " + result + "\n" +
							file2.getAbsolutePath() + " : deleted? " + result2);
		}
		// Tosco way
		file = new File(performanceDirectory.getAbsolutePath()
				+ "/downloadedfile.zip");
		result &= file.delete();
		result &= performanceDirectory.delete();
		return result;

	}

}
