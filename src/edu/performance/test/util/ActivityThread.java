package edu.performance.test.util;

import android.os.Looper;
import edu.performance.test.PerformanceTestActivity;

public class ActivityThread extends Thread {

		boolean mRun;		
		PerformanceTestActivity activity;
//TODO FIXME Verificar se quando dá timeout o teste realmente é finalizado, 
		//pois estava apenas dormindo a thread momentaneamente mas ela voltava e gravava os resultados
		//era apresentados fora da ordem dos testes. Sempre que um resultado for mostrado fora da ordem a algo errado acontecendo
		// pode ser o timeout.


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
			Looper.prepare();
			while (mRun)

			{				
				activity.execute();
			}
			
			

		}

	


}
