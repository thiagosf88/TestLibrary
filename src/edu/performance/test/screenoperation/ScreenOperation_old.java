package edu.performance.test.screenoperation;

import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Color;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTest;
import edu.performance.test.R;

public class ScreenOperation_old extends PerformanceTest<Integer> {

	Library appRef = null;

	public ScreenOperation_old(Library l) {
		super(new Integer(2000), null);
		appRef = l;
		myHandler = new Handler();

	}

	private LinearLayout rl;
	TextView time;
	int colorIndex = 0;
	int[] colors = { Color.BLACK, Color.BLUE, Color.GRAY, Color.GREEN,
			Color.RED, Color.YELLOW, Color.WHITE };
	int colorCounts[] = new int[colors.length + 1];
	Handler myHandler = null;
	Timer myTimer = null;

	public void setMyHandler(Handler h) {
		this.myHandler = h;
	}

	public void execute() {

		testAdisplayColoredScreens();
	}

	/*
	 * private void testAshowColoredScreens() {
	 * 
	 * time = (TextView) appRef.findViewById(R.id.status); rl = (LinearLayout)
	 * appRef.findViewById(R.id.backgroundLayout); if (rl != null) {
	 * rl.setBackgroundColor(colors[colorIndex]); // colorCounts[colorIndex]++;
	 * // while(colorCount < 30){ startTimer(); } // } }
	 */

	private void testAdisplayColoredScreens() {
		time = (TextView) appRef.findViewById(R.id.status);
		rl = (LinearLayout) appRef.findViewById(R.id.backgroundLayout);
		if (rl != null) {
			rl.setBackgroundColor(colors[colorIndex]);

			myTimer = new Timer();
			myTimer.schedule(new TimerTask() {
				@Override
				public void run() {

					UpdateGUI();
				}
			}, 0, this.getLevel());
		}

	}

	/*
	 * private void startTimer() { new CountDownTimer(this.getLevel(), 1000) {
	 * 
	 * @Override public void onTick(long leftTimeInMilliseconds) {
	 * time.setText(String.valueOf(leftTimeInMilliseconds / 1000));
	 * 
	 * }
	 * 
	 * @Override public void onFinish() {
	 * rl.setBackgroundColor(colors[(++colorIndex % colors.length)]);
	 * 
	 * // colorCounts[colorIndex]++; startTimer(); }
	 * 
	 * }.start();
	 * 
	 * }
	 */

	private void UpdateGUI() {
		// System.out.println("ant: " + appRef.getBatteryPreviousLevel()
		// + " min: " + appRef.getMIN_LEVEL_TO_EXIT());
//		if (appRef.getBatteryPreviousLevel() <= appRef.getMIN_LEVEL_TO_EXIT()) {

			myTimer.cancel();

			myHandler.removeCallbacks(myRunnable);
			myHandler.sendEmptyMessage(0);

		//} tive que comentar esse if por que estou mundando essa parte de bateria para uma classe separada
		myHandler.post(myRunnable);
	}

	final Runnable myRunnable = new Runnable() {
		public void run() {
			colorIndex = ++colorIndex % colors.length;
			colorCounts[colorIndex]++;
			rl.setBackgroundColor(colors[(colorIndex)]);
		}
	};
}
