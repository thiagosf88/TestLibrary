package edu.performance.test.util;

import edu.performance.test.PerformanceTestActivity;

public class ActivityThread extends Thread {

		boolean mRun;		
		PerformanceTestActivity activity;


		public ActivityThread(PerformanceTestActivity ctx)

		{

			activity = ctx;

			mRun = false;



		}

		public void setRunning(boolean bRun)

		{

			mRun = bRun;

		}

		@Override
		public void run()

		{ 

			super.run();

			while (mRun)

			{				
				activity.execute();
			}
			
			

		}

	


}
