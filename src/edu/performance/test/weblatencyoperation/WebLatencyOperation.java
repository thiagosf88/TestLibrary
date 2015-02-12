package edu.performance.test.weblatencyoperation;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Bundle;
import android.widget.TextView;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.R;
import edu.performance.test.filesequentialoperation.FileSequentialOperation;
import edu.performance.test.util.WriteNeededFiles;
import edu.performance.test.weboperation.WebViewActivity;
/**
 * This class extends PerformanceTestActivity because the http request must not to be
 * done in main thread. Need more details
 * @author thiago
 *
 */
public class WebLatencyOperation extends InternetPerformanceTestActivity {

	
	String websites;
	boolean isTheLastPage;
	

	HttpGet request;

	HttpParams httpParameters;

	HttpClient httpClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.performance_test);
		status = (TextView)findViewById(R.id.status);
		status.setText(message);
		if(getIntent().getExtras() != null){
			isTheLastPage = getIntent().getExtras().getBoolean(WebViewActivity.ISTHELASTPAGE);
			websites = getIntent().getExtras().getString(WebViewActivity.URL);			
		}		 
		status.setText("Testing latency to reach: " + websites);
		executeTest();
		
	}




	public void execute() {
		super.execute();
		
		httpParameters = new BasicHttpParams();
		httpClient = new DefaultHttpClient(httpParameters);
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);

		// testTJMwebOperation();
		
			for (int i = 0; i < 5; i++) {// aumentar para 30 depois
				
				testTJMLatency(websites);
				
			}
			
			Bundle extras = new Bundle();
			extras.putBoolean(WebViewActivity.ISTHELASTPAGE, isTheLastPage());
			
		  finishTest(extras);

	}

	
	private void testTJMLatency(String host) {

		try {
			request = new HttpGet(host);
			// request.setHeader("host", host);
			HttpResponse response = httpClient.execute(request);
			if (response.getEntity() != null) {
				response.getEntity().consumeContent();
			}

		} catch (ClientProtocolException e) {
			new Exception("From ClientProtocol : " + e.getMessage());
		} catch (IOException e) {
			new Exception("From IOException : " + e.getMessage());
		} catch (RuntimeException e) {
			new Exception("From RuntimeException : " + e.getMessage());
		} catch (Exception e){
			FileSequentialOperation rw = new FileSequentialOperation();
			String message = (e.getMessage() != null)
					&& (!e.getMessage().trim().isEmpty()) ? e.getMessage()
					: "Exception without message!!!";
	
			rw.testTJMwriteSequentialFile(
					WriteNeededFiles.REPORT_DIRECTORY_NAME + "/ErrorsL" + host.replace("http://", "")
							+ ".txt", message);
		}
		

	}
	public boolean isTheLastPage() {
		return isTheLastPage;
	}
}
