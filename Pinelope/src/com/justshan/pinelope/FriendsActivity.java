package com.justshan.pinelope;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class FriendsActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		
		TextView tv = (TextView) findViewById(R.id.tvWho);
		tv.setText("Hello, " + getIntent().getExtras().getString("USER") + "! Who would you like to shop for today?");
		
		
		
	}
}
