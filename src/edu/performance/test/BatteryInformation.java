package edu.performance.test;

import android.app.Activity;
import android.os.Bundle;
import edu.performance.test.floatoperation.FloatOperation;

/**
 * This class is responsible to get information about battery use.
 * 
 * @author thiago
 * 
 */
public class BatteryInformation extends Activity {

	// private TextView batteryInfo;
	// private ImageView imageBatteryState;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		// batteryInfo=(TextView)findViewById(R.id.textViewBatteryInfo);
		// imageBatteryState=(ImageView)findViewById(R.id.imageViewBatteryState);

		execute();
	}

	void execute() {
		while (true) {			
			FloatOperation fop = new FloatOperation(null);
			fop.setLevel(10.0);
			fop.execute();

		}
	}

}
