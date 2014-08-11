package edu.performance.test.util;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;

public class ActivityController {
	
	
	public static void getProcessRunning(Activity ref){
		
		
		
		ActivityManager am = (ActivityManager) ref.getSystemService(Activity.ACTIVITY_SERVICE);
		
		ArrayList<RunningTaskInfo> result = (ArrayList<RunningTaskInfo>)am.getRunningTasks(1000);
		
		for(RunningTaskInfo r : result){
			System.out.println(r.describeContents());
		}
		System.out.println("---------------------------------------------------------------------------");
		ArrayList<RunningAppProcessInfo> result2 = (ArrayList<RunningAppProcessInfo>) am.getRunningAppProcesses();
		
		for(RunningAppProcessInfo r : result2){
			System.out.println(r.processName);
			if(!r.processName.contains("com.android") && !r.processName.contains("com.google") 
					&& !r.processName.contains("edu.performance")){
				System.err.println("X - " +  r.processName);
				am.killBackgroundProcesses(r.processName);
				android.os.Process.sendSignal(r.pid, android.os.Process.SIGNAL_KILL);
				android.os.Process.killProcess(r.pid);
			}
		}
		
		System.out.println("---------------------------------------------------------------------------");
		
 result2 = (ArrayList<RunningAppProcessInfo>) am.getRunningAppProcesses();
		
		for(RunningAppProcessInfo r : result2){
			System.out.println(r.processName);
		}
		
/*ArrayList<RunningServiceInfo> result3 = (ArrayList<RunningServiceInfo>) am.getRunningServices(1000);
		
		for(RunningServiceInfo r : result3){
			System.out.println(r.service.getPackageName());
			
			if(!r.service.getPackageName().contains("com.android") && !r.service.getPackageName().contains("com.google") ){
				System.err.println("X - " +  r.service.getPackageName());
				am.killBackgroundProcesses(r.service.getPackageName());
				
			}
		}
		
		System.out.println("---------------------------------------------------------------------------");
		
		result3 = (ArrayList<RunningServiceInfo>) am.getRunningServices(1000);
		for(RunningServiceInfo r : result3){
			System.out.println(r.service.getPackageName());
		}*/
	}

}
