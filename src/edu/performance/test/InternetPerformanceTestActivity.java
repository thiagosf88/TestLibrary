package edu.performance.test;

import edu.performance.test.util.InternetController;
import android.os.Bundle;

public abstract class InternetPerformanceTestActivity extends PerformanceTestActivity {
	
	
	
	protected void onPause() {
		super.onPause();
		InternetController.setWifiAvailability(false, this);
		InternetController.setMobileDataAvailability(this, false);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	}
	//TODO verificar se não é possível mudar isso tudo para dentro do onCreate.
	public void execute(){
		//InternetController.setWifiAvailability(true, this);
		
		//TODO Change this to PerformanceTestActivity
		avoidingInfiniteTasks();
		
		InternetController.setMobileDataAvailability(this, true);
		while(InternetController.getInternetAvailability(this) == false);
	}
	



}
