package edu.performance.test.weboperation;

import edu.performance.test.PerformanceTestActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TestWebView extends WebViewClient {
	
	WebViewActivity actRef;
	boolean timeout = true;
	public TestWebView(WebViewActivity wva){
		actRef = wva;
	}

	@Override
    public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {

        view.loadUrl(urlNewString);
        return true;
    }
/*
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        running = Math.max(running, 1); // First request move it to 1.
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(--running == 0) { // just "running--;" if you add a timer.
        	System.out.println(url + " - loaded");
        	Intent mIntent = new Intent();
			mIntent.putExtra(WebOperationActivity.ISTHELASTPAGE, actRef.isTheLastPage());
			actRef.setResult(Activity.RESULT_OK, mIntent);
			actRef.finish();
        }
    }*/

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
    	new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(actRef.getMAX_TIME_MS());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(timeout) {
                    gettingOut("Timeout!!", false); 
                }
            }
        }).start();
    }
	
	public void onPageFinished(WebView view, String url) {
		timeout = false;
		gettingOut(url + " loaded!", true);
	}
	
	private void gettingOut(String message, boolean loaded){
		
		Intent mIntent = new Intent();
		Bundle extras = new Bundle();
		extras.putBoolean(WebOperationActivity.ISTHELASTPAGE, actRef.isTheLastPage());
		if(loaded){			
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
			mIntent.putExtras(extras);
			actRef.setResult(Activity.RESULT_OK, mIntent);
			//System.out.println(message);
		}
		else{

			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			extras.putString(PerformanceTestActivity.ERROR_MESSAGE, "O carregamento da página atingiu o tempo máximo");
			mIntent.putExtras(extras);
			actRef.setResult(Activity.RESULT_CANCELED, mIntent);
			//System.err.println(message);
		}
		
		actRef.finishTest(mIntent.getExtras());
	}
	
}