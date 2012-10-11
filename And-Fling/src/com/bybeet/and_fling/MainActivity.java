package com.bybeet.and_fling;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        FrameLayout frame = (FrameLayout)findViewById(R.id.graphics_holder);
        PlayAreaView image = new PlayAreaView(this);
        frame.addView(image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private class PlayAreaView extends View {

		private static final String DEBUG_TAG = "PlayAreaView_onDraw";
    	private Matrix translate;
    	private Bitmap droid;
    	
    	public PlayAreaView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
    	
    	protected void onDraw(Canvas canvas) {
    		canvas.drawBitmap(droid, translate, null);
    		@SuppressWarnings("deprecation")
			Matrix m = canvas.getMatrix();
    		Log.d(DEBUG_TAG,"Matrix: " + translate.toShortString());
    		Log.d(DEBUG_TAG,"Canvas: " + m.toShortString());
    	}
    }
}
