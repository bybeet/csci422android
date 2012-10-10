package com.example.and_gestures_tutorial;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.bybeet.and_gestures_tutorial.R;

public class MainActivity extends Activity implements OnGesturePerformedListener {

	GestureLibrary mLibrary;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if(!mLibrary.load())
			finish();

		GestureOverlayView gestures = (GestureOverlayView)findViewById(R.id.gestures);
		gestures.addOnGesturePerformedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

		if(predictions.size() > 0 && predictions.get(0).score > 1.0) {
			String result = predictions.get(0).name;

			if("open".equalsIgnoreCase(result)){
				Toast.makeText(this, "Opening the document",  Toast.LENGTH_LONG).show();
			}else if("save".equalsIgnoreCase(result)){
				Toast.makeText(this, "Saving the document", Toast.LENGTH_LONG).show();
			}else if("star".equalsIgnoreCase(result)){
				Intent intent = new Intent(this, com.bybeet.and_gestures_tutorial.GameActivity.class);
				startActivity(intent);
			}else if("toast".equalsIgnoreCase(result)){
				Toast.makeText(this, "You made toast!!!", Toast.LENGTH_LONG).show();
			}
		}

	}
}
