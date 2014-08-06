package edu.performance.test.weboperation;

import android.os.Bundle;
import android.webkit.WebView;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.R;

public class WebViewActivity extends InternetPerformanceTestActivity{
	WebView myWebView;
	String websites;
	boolean isTheLastPage;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {
			isTheLastPage = getIntent().getExtras().getBoolean(
					WebOperationActivity.ISTHELASTPAGE);
			websites = getIntent().getExtras().getString(
					WebOperationActivity.URL);
		}
		setContentView(R.layout.webview);
		myWebView = (WebView) findViewById(R.id.webview);
		myWebView.setWebViewClient(new TestWebView(this));

		execute();
	}
	
	
	public void execute(){
		
		testTJMwebOperation(websites);
		
		 
	}
	private void testTJMwebOperation(String website) {

		myWebView.loadUrl(website);

	}

	public boolean isTheLastPage() {
		return isTheLastPage;
	}
	
	
	

}
