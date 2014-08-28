package edu.performance.test.weboperation;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;

/**
 * This class extends PerformanceTestActivity but it does not start a new new
 * thread. Only its child @see edu.performance.test.weboperation.WebOperation
 * starts a new thread. This class performs two kind of tests. The first test is
 * the latency to access some website ad the another test consists in fully load
 * the website. The level in this tests is the max index (inclusive) of web
 * sites array which will be tested and this defines the load level.
 * 
 * @author thiago
 * 
 */
public class WebOperationActivity extends PerformanceTestActivity {

	boolean isTheLastPage;
	ArrayList<Intent> pagesToAccess;
	int countIntent = 0;
	private final Integer MAX_TIME = 17000;
	/**	
	 * 
	 */
	Integer level;
	String[] websites;
	public static final String URL = "URL";
	public static final String ISTHELASTPAGE = "ISTHELASTPAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pagesToAccess = new ArrayList<Intent>();

		status.setText(message);

		if (getIntent().getExtras() != null) {
			websites = getResources().getStringArray(R.array.webSites);
			if (getIntent().hasExtra(Library.LEVEL_INT)
					&& getIntent().hasExtra(Library.THELASTTEST)) {
				level = getIntent().getExtras().getInt(Library.LEVEL_INT) <= (websites.length -1) ? 
				getIntent().getExtras().getInt(Library.LEVEL_INT) : (websites.length -1);
				
				setTheLast(getIntent().getExtras().getBoolean(
						Library.THELASTTEST));
			} else {
				Bundle extras = new Bundle();
				extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
				finishTest(extras);
				return;
			}
		} else {
			Bundle extras = new Bundle();
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			finishTest(extras);
			finish();
		}

		Intent webSite = null;
		for (int n = 0; n <= level; n++) {

			if (level > n) {
				webSite = new Intent(this, WebLatencyOperation.class);
				webSite.putExtra(ISTHELASTPAGE, false);
				webSite.putExtra(PerformanceTestActivity.MAXTIME, MAX_TIME);
				webSite.putExtra(Library.THELASTTEST, false);
				webSite.putExtra(URL, websites[n]);
				webSite.putExtra(Library.STATUS, websites[n]);
				getPagesToAccess().add(webSite);

				webSite = new Intent(this, WebViewActivity.class);
				webSite.putExtra(ISTHELASTPAGE, false);
				webSite.putExtra(PerformanceTestActivity.MAXTIME, MAX_TIME);
				webSite.putExtra(Library.THELASTTEST, false);
				webSite.putExtra(URL, websites[n]);
				webSite.putExtra(Library.STATUS, websites[n]);
				getPagesToAccess().add(webSite);
			} else {
				webSite = new Intent(this, WebLatencyOperation.class);
				webSite.putExtra(ISTHELASTPAGE, false);
				webSite.putExtra(PerformanceTestActivity.MAXTIME, MAX_TIME);
				webSite.putExtra(Library.THELASTTEST, false);
				webSite.putExtra(URL, websites[n]);
				webSite.putExtra(Library.STATUS, websites[n]);
				getPagesToAccess().add(webSite);

				webSite = new Intent(this, WebViewActivity.class);
				webSite.putExtra(ISTHELASTPAGE, true);
				webSite.putExtra(PerformanceTestActivity.MAXTIME, MAX_TIME);
				webSite.putExtra(Library.THELASTTEST, false);
				webSite.putExtra(URL, websites[n]);
				webSite.putExtra(Library.STATUS, websites[n]);
				getPagesToAccess().add(webSite);
			}

		}

		/*
		 * Intent webSite = new Intent(this, WebViewActivity.class);
		 * webSite.putExtra(ISTHELASTPAGE, false);
		 * webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		 * webSite.putExtra(Library.THELASTTEST, false); webSite.putExtra(URL,
		 * websites[0]); webSite.putExtra(Library.STATUS, websites[0]);
		 * getPagesToAccess().add(webSite);
		 * 
		 * webSite = new Intent(this, WebViewActivity.class);
		 * webSite.putExtra(ISTHELASTPAGE, false);
		 * webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		 * webSite.putExtra(Library.THELASTTEST, false); webSite.putExtra(URL,
		 * websites[1]); webSite.putExtra(Library.STATUS, websites[1]);
		 * getPagesToAccess().add(webSite);
		 * 
		 * webSite = new Intent(this, WebViewActivity.class);
		 * webSite.putExtra(ISTHELASTPAGE, false);
		 * webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		 * webSite.putExtra(Library.THELASTTEST, false); webSite.putExtra(URL,
		 * websites[2]); webSite.putExtra(Library.STATUS, websites[2]);
		 * getPagesToAccess().add(webSite); webSite = new Intent(this,
		 * WebViewActivity.class); webSite.putExtra(ISTHELASTPAGE, false);
		 * webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		 * webSite.putExtra(Library.THELASTTEST, false); webSite.putExtra(URL,
		 * websites[3]); webSite.putExtra(Library.STATUS, websites[3]);
		 * getPagesToAccess().add(webSite);
		 * 
		 * webSite = new Intent(this, WebLatencyOperation.class);
		 * webSite.putExtra(ISTHELASTPAGE, false);
		 * webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		 * webSite.putExtra(Library.THELASTTEST, false); webSite.putExtra(URL,
		 * websites[0]); webSite.putExtra(Library.STATUS, websites[0]);
		 * getPagesToAccess().add(webSite); webSite = new Intent(this,
		 * WebLatencyOperation.class); webSite.putExtra(ISTHELASTPAGE, false);
		 * webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		 * webSite.putExtra(Library.THELASTTEST, false); webSite.putExtra(URL,
		 * websites[1]); webSite.putExtra(Library.STATUS, websites[1]);
		 * getPagesToAccess().add(webSite); webSite = new Intent(this,
		 * WebLatencyOperation.class); webSite.putExtra(ISTHELASTPAGE, false);
		 * webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		 * webSite.putExtra(Library.THELASTTEST, false); webSite.putExtra(URL,
		 * websites[2]); webSite.putExtra(Library.STATUS, websites[2]);
		 * getPagesToAccess().add(webSite); webSite = new Intent(this,
		 * WebLatencyOperation.class); webSite.putExtra(ISTHELASTPAGE, true);
		 * webSite.putExtra(PerformanceTestActivity.MAXTIME, 17000);
		 * webSite.putExtra(Library.THELASTTEST, false); webSite.putExtra(URL,
		 * websites[3]); webSite.putExtra(Library.STATUS, websites[3]);
		 * getPagesToAccess().add(webSite);
		 */

		execute();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (data == null)
			return;

		Bundle results = data.getExtras();

		if (!results.getBoolean(ISTHELASTPAGE) && requestCode == 1
				&& resultCode == Activity.RESULT_OK) {
			testingPages(getPagesToAccess().get(++countIntent));
		} else {
			Intent mIntent = new Intent();

			mIntent.putExtra(Library.THELASTTEST, isTheLast());
			setResult(RESULT_OK, mIntent);
			System.out.println("saindo. Is it the last? " + isTheLast());
			// wakeLock.release();
			finish();
		}

	}

	public ArrayList<Intent> getPagesToAccess() {
		return pagesToAccess;
	}

	public void setPagesToAccess(ArrayList<Intent> pagesToAccess) {
		this.pagesToAccess = pagesToAccess;
	}

	void testingPages(Intent test) {

		startActivityForResult(test, 1);
	}

	public void execute() {
		testingPages(pagesToAccess.get(0));

	}

}
