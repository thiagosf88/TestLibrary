package edu.performance.test.graphicoperation.draws;

import android.os.Bundle;
import edu.performance.test.R;
import edu.performance.test.graphicoperation.DrawActivity;

public class ImageActivity extends DrawActivity {
	
	ImageOperation operation;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);		
		
		operation = new ImageOperation(this, null);

		//Drawable myDrawable = getResources().getDrawable(android.R.drawable.ic_dialog_map);
		//Bitmap bmp = ((BitmapDrawable) myDrawable).getBitmap();
        //io.setmBitmap(bmp);
		
		//Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}




}
