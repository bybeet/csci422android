package com.example.androidtutorialbasicforms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.BasicForms.MESSAGE";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void sendMessage(View view) {
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	EditText editText = (EditText)findViewById(R.id.edit_message);
    	intent.putExtra(EXTRA_MESSAGE, editText.getText().toString());
    	startActivity(intent);
    }
    
    public void sendName(View view) {
    	Intent intent = new Intent(this, DisplayNameActivity.class);
    	EditText editText = (EditText)findViewById(R.id.enter_name);
    	intent.putExtra(EXTRA_MESSAGE, editText.getText().toString());
    	startActivity(intent);
    }
}
