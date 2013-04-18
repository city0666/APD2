package com.justshan.pinelope;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class PinelopeActivity extends Activity {

	EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userentry);
		
		Button B = (Button) findViewById(R.id.pinusergo);
		et = (EditText) findViewById(R.id.pinuser);
		
		B.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Sending from this class to the "second" view/class
				Intent intent = new Intent(PinelopeActivity.this, FriendsActivity.class);
				//This is the information that will be sent.
				intent.putExtra("USER", et.getText().toString());
				startActivity(intent);
			}
		});
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.pinelope, menu);
//		return true;
//	}

}
