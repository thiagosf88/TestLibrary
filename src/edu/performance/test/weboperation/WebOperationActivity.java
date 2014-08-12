package edu.performance.test.weboperation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;

/**
 * This class extends PerformanceTestActivity but it does not start
 * a new new thread. Only its child @see edu.performance.test.weboperation.WebOperation
 * starts a new thread. Need more details.
 * @author thiago
 *
 */
public class WebOperationActivity extends PerformanceTestActivity {
	

	boolean isTheLastPage;
	ArrayList<Intent> pagesToAccess;
	int countIntent = 0;
	String[] websites;
	public static final String URL = "URL";
	public static final String ISTHELASTPAGE = "ISTHELASTPAGE";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		websites = getResources().getStringArray(R.array.webSites);
		
		pagesToAccess = new ArrayList<Intent>();
		
		status.setText(message);
		
		Intent webSite = new Intent(this, WebViewActivity.class);
		webSite.putExtra(ISTHELASTPAGE, false);
		webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		webSite.putExtra(Library.THELASTTEST, false);
		webSite.putExtra(URL, websites[0]);
		webSite.putExtra(Library.STATUS, websites[0]);
		getPagesToAccess().add(webSite);
		webSite = new Intent(this, WebViewActivity.class);
		webSite.putExtra(ISTHELASTPAGE, false);
		webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		webSite.putExtra(Library.THELASTTEST, false);
		webSite.putExtra(URL, websites[1]);
		webSite.putExtra(Library.STATUS, websites[1]);
		getPagesToAccess().add(webSite);
		webSite = new Intent(this, WebViewActivity.class);
		webSite.putExtra(ISTHELASTPAGE, false);
		webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		webSite.putExtra(Library.THELASTTEST, false);
		webSite.putExtra(URL, websites[2]);
		webSite.putExtra(Library.STATUS, websites[2]);
		getPagesToAccess().add(webSite);
		webSite = new Intent(this, WebViewActivity.class);
		webSite.putExtra(ISTHELASTPAGE, false);
		webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		webSite.putExtra(Library.THELASTTEST, false);
		webSite.putExtra(URL, websites[3]);
		webSite.putExtra(Library.STATUS, websites[3]);
		getPagesToAccess().add(webSite);
		
		 webSite = new Intent(this, WebLatencyOperation.class);
		webSite.putExtra(ISTHELASTPAGE, false);
		webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		webSite.putExtra(Library.THELASTTEST, false);
		webSite.putExtra(URL, websites[0]);
		webSite.putExtra(Library.STATUS, websites[0]);
		getPagesToAccess().add(webSite);
		webSite = new Intent(this, WebLatencyOperation.class);
		webSite.putExtra(ISTHELASTPAGE, false);
		webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		webSite.putExtra(Library.THELASTTEST, false);
		webSite.putExtra(URL, websites[1]);
		webSite.putExtra(Library.STATUS, websites[1]);
		getPagesToAccess().add(webSite);
		webSite = new Intent(this, WebLatencyOperation.class);
		webSite.putExtra(ISTHELASTPAGE, false);
		webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		webSite.putExtra(Library.THELASTTEST, false);
		webSite.putExtra(URL, websites[2]);
		webSite.putExtra(Library.STATUS, websites[2]);
		getPagesToAccess().add(webSite);
		webSite = new Intent(this, WebLatencyOperation.class);
		webSite.putExtra(ISTHELASTPAGE, true);
		webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		webSite.putExtra(Library.THELASTTEST, false);
		webSite.putExtra(URL, websites[3]);
		webSite.putExtra(Library.STATUS, websites[3]);
		getPagesToAccess().add(webSite);
		
		
		execute();
	}
	
	
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (data == null)
			return;

		Bundle results = data.getExtras();
		
		if (!results.getBoolean(ISTHELASTPAGE) && requestCode == 1 && resultCode == Activity.RESULT_OK) {
			testingPages(getPagesToAccess().get(++countIntent));
		}
		else{
			Intent mIntent = new Intent();

			mIntent.putExtra(Library.THELASTTEST, isTheLast());
			setResult(RESULT_OK, mIntent);
			System.out.println("saindo. Is it the last? " + isTheLast() );
			//wakeLock.release(); 
			finish();
		}
		
		
		
	}

public ArrayList<Intent> getPagesToAccess() {
	return pagesToAccess;
}

public void setPagesToAccess(ArrayList<Intent> pagesToAccess) {
	this.pagesToAccess = pagesToAccess;
}
	
void testingPages(Intent test){
	
	startActivityForResult(test, 1);
}


public void execute() {
	testingPages(pagesToAccess.get(0));
	
}	

}
