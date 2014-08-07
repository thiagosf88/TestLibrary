package edu.performance.test.weboperation;

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
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.R;
import edu.performance.test.fileoperation.FileOperation;
import edu.performance.test.util.WriteNeededFiles;
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
		if(getIntent().getExtras() != null){
			isTheLastPage = getIntent().getExtras().getBoolean(WebOperationActivity.ISTHELASTPAGE);
			websites = getIntent().getExtras().getString(WebOperationActivity.URL);			
		}		 
		status.setText(message);
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
			extras.putBoolean(WebOperationActivity.ISTHELASTPAGE, isTheLastPage());
			
		  finishTest(extras);

	}

	
//Creio que o problema é estar na thread pŕincipal
	private void testTJMLatency(String host) {

		try {
			request = new HttpGet(host);
			// request.setHeader("host", host);
			HttpResponse response = httpClient.execute(request);
			if (response.getEntity() != null) {
				response.getEntity().consumeContent();
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			FileOperation rw = new FileOperation();
			String message = (e.getMessage() != null)
					&& (!e.getMessage().trim().isEmpty()) ? e.getMessage()
					: "Exception without message!!!";
	
			rw.testJwriteSequentialFile(WriteNeededFiles.REPORT_DIRECTORY_NAME,
					WriteNeededFiles.REPORT_DIRECTORY_NAME + "/ErrorsL" + host.replace("http://", "")
							+ ".txt", message);
		}

	}
	public boolean isTheLastPage() {
		return isTheLastPage;
	}
}
