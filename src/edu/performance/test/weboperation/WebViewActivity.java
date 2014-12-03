package edu.performance.test.weboperation;

import android.os.Bundle;
import android.webkit.WebView;
import edu.performance.test.InternetPerformanceTestActivity;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;

public class WebViewActivity extends InternetPerformanceTestActivity{
	WebView myWebView;
	String websites;
	boolean isTheLastPage;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent().getExtras() != null) {
			if(getIntent().hasExtra(WebOperationActivity.ISTHELASTPAGE)
					&& getIntent().hasExtra(WebOperationActivity.URL)){
			setTheLastPage(getIntent().getExtras().getBoolean(
					WebOperationActivity.ISTHELASTPAGE));
			websites = getIntent().getExtras().getString(
					WebOperationActivity.URL);
			}
			else{
				Bundle extras = new Bundle();
				extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "Não foram fornecidos parâmetros mínimos: website!");
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				setResult(RESULT_CANCELED);
				finish();
			}
			
		}
		
		else{
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			setResult(RESULT_CANCELED);
			finish();
		}
		
		setContentView(R.layout.webview);
		myWebView = (WebView) findViewById(R.id.webview);
		
		myWebView.setWebViewClient(new TestWebView(this));

		execute();
	}
	
	
	public void execute(){
		super.execute();
		testTJMwebOperation(websites);	
		
		 
	}
	private void testTJMwebOperation(String website) {

		myWebView.loadUrl(website);

	}

	public boolean isTheLastPage() {
		return isTheLastPage;
	}


	public void setTheLastPage(boolean isTheLastPage) {
		this.isTheLastPage = isTheLastPage;
	}
	
	protected int getMAX_TIME_MS() {
		return super.getMAX_TIME_MS();
	}
	
	

}
