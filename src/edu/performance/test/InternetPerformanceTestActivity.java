package edu.performance.test;

import edu.performance.test.util.InternetController;
import android.os.Bundle;

public abstract class InternetPerformanceTestActivity extends PerformanceTestActivity {
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
		//InternetController.setWifiAvailability(true, this); mudando para controle anterior para dar mais tempo para conexão
	
	}
	//TODO verificar se não é possível mudar isso tudo para dentro do onCreate.
	public void execute(){
		
		//TODO Change this to PerformanceTestActivity
		avoidingInfiniteTasks();
		
		
		while(InternetController.getInternetAvailability(this) == false);
	}
	
	protected int getMAX_TIME_MS() {
		return super.getMAX_TIME_MS();
	}
	



}
