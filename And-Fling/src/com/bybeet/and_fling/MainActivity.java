package com.bybeet.and_fling;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Interpolator;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
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

		private static final String DEBUG_TAG = "PlayAreaView";
    	private Matrix translate;
    	private Bitmap droid;
    	private GestureDetector gestures;
    	private Matrix animateStart;
    	private OvershootInterpolator animateInterpolator;
    	private long startTime;
    	private long endTime;
    	private float totalAnimDx;
    	private float totalAnimDy;
    	
    	
    	public PlayAreaView(Context context) {
			super(context);
			translate = new Matrix();
			gestures = new GestureDetector(MainActivity.this, new GestureListener(this));
			droid = BitmapFactory.decodeResource(getResources(), R.drawable.ic_android);
			// TODO Auto-generated constructor stub
		}
    	
    	protected void onDraw(Canvas canvas) {
    		canvas.drawBitmap(droid, translate, null);
    		@SuppressWarnings("deprecation")
			Matrix m = canvas.getMatrix();
    		Log.d(DEBUG_TAG,"Matrix: " + translate.toShortString());
    		Log.d(DEBUG_TAG,"Canvas: " + m.toShortString());
    	}
    	

		public void onMove(float dx, float dy) {
			translate.postTranslate(dx, dy);
			invalidate();
		}
		
		public void onResetLocation() {
			translate.reset();
			invalidate();
		}
		
		public void onAnimateMove(float dx, float dy, long time){
			animateStart = new Matrix(translate);
			animateInterpolator = new OvershootInterpolator();
			startTime = System.currentTimeMillis();
			endTime = startTime += time;
			totalAnimDx = dx;
			totalAnimDy = dy;
			post(new Runnable() {
				@Override
				public void run() {
					onAnimateStep();
				}
			});
		}
		
		private void onAnimateStep() {
			float percentTime = (float)(System.currentTimeMillis() - startTime)/(float)(endTime - startTime);
			System.out.println(percentTime);
			float percentDistance = animateInterpolator.getInterpolation(percentTime);
			float curDx = percentDistance * totalAnimDx;
			float curDy = percentDistance * totalAnimDy;
			translate.set(animateStart);
			onMove(curDx, curDy);
			Log.v(DEBUG_TAG, "We're " + percentDistance + " of the way there.");
			if(percentTime < 1.0f){
				post(new Runnable() {
					@Override
					public void run() {
						onAnimateStep();
					}
				});
			}
		}
    	
    	@Override
    	public boolean onTouchEvent(MotionEvent event){
    		return gestures.onTouchEvent(event);
    	}
    	
    	private class GestureListener implements GestureDetector.OnGestureListener, OnDoubleTapListener {
    		
    		private static final String DEBUG_TAG = "GestureListener";
    		private PlayAreaView view;
    		
    		public GestureListener (PlayAreaView view) {
    			this.view = view;
    		}
    		
    		@Override
    		public boolean onDown(MotionEvent e) {
    			Log.v(DEBUG_TAG, "onDown");
    			return true;
    		}

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				Log.v(DEBUG_TAG, "onDoubleTap");
				view.onResetLocation();
				return true;
			}

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				Log.v(DEBUG_TAG, "onFling");
				
				final float distanceTimeFactor = 0.4f;
				final float totalDx = (distanceTimeFactor * velocityX/2);
				final float totalDy = (distanceTimeFactor * velocityY/2);
				
				view.onAnimateMove(totalDx, totalDy, (long)(1000 * distanceTimeFactor));
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				Log.v(DEBUG_TAG, "onScroll");
				view.onMove(-distanceX, -distanceY);
				return true;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
    	}

    }
}
