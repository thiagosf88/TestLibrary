package edu.performance.test.util;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import edu.performance.test.graphicoperation.Operation;

public class TestThread extends Thread

{

	boolean mRun;

	Canvas mcanvas;

	SurfaceHolder surfaceHolder;

	Context context;

	Operation msurfacePanel;

	public TestThread(SurfaceHolder sholder, Context ctx,
			Operation spanel)

	{

		surfaceHolder = sholder;

		context = ctx;

		mRun = false;

		msurfacePanel = spanel;

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

			mcanvas = surfaceHolder.lockCanvas();

			if (mcanvas != null)

			{

				msurfacePanel.doDraw(mcanvas);

				surfaceHolder.unlockCanvasAndPost(mcanvas);

			}

		}

	}

}
