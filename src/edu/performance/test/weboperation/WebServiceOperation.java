package edu.performance.test.weboperation;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.os.Bundle;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;

public class WebServiceOperation extends PerformanceTest<String>{
	
	
	
	public WebServiceOperation(PerformanceTestActivity activity, String level) {
		super(level, activity);
		if(activity != null)
			activity.executeTest();
	}

	public Document testTJMWebService(String urlString) throws IOException, ParserConfigurationException, SAXException {
		URL url = null;
		Document doc = null;
		HttpURLConnection urlConnection = null;
		InputStream in = null;
		

			url = new URL(urlString);

			urlConnection = (HttpURLConnection) url.openConnection();
			// urlConnection.setRequestProperty("Accept-Charset", "ISO-8859-1");
			urlConnection.setRequestProperty("Request-Method", "GET");
			// Log.v("set", urlConnection.getContentEncoding());
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(false);
			urlConnection.connect();
			in = new BufferedInputStream(urlConnection.getInputStream());

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			doc = db.parse(in);

			doc.getDocumentElement().normalize();		
		
		urlConnection.disconnect();
		
		Bundle extras = new Bundle();			
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
		return doc;
	}

	@Override
	public void execute() {
		try{
		testTJMWebService(this.getLevel());
		} catch (Exception e) {
			if(e != null && e.getMessage() != null)
			System.out.println(e.getMessage());
			Bundle extras = new Bundle();			
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			activity.finishTest(extras);
		}
		
		
		

		
	}

}
