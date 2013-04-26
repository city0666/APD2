package com.justshan.pinelope;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class PinelopeActivity extends Activity {

	EditText et;
	String theuser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userentry);
		
		Parse.initialize(this, "etqeIZdxSX0SqLeWoABVkIEd0UOe3Q6rHzLBtt9P", "ifp8lZdLqDcL0GVzEwJ9IIco6cmkvR652uwEdtJk"); 
		
		ParseQuery query = new ParseQuery("UserObject");
		query.whereEqualTo("pinterest", "Pinterest");
		query.setLimit(1);
		query.findInBackground(new FindCallback() {
			
			public void done(List<ParseObject> getInfo, ParseException e) {
		        if (e == null) {
		            Log.d("username", getInfo.size() + "users" );
		            int line = 0;
					ParseObject s = getInfo.get(line);
					theuser = s.getString("username");
					Log.i("USERNAME", theuser);
					Intent intent = new Intent(PinelopeActivity.this, FriendsActivity.class);
					//This is the information that will be sent.
					intent.putExtra("USER", theuser);
					startActivity(intent);
		            
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }
		});
		    
		
		Button B = (Button) findViewById(R.id.pinusergo);
		et = (EditText) findViewById(R.id.pinuser);
		
		B.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				ParseObject userObject = new ParseObject("UserObject");
				userObject.put("username", et.getText().toString() );
				userObject.put("pinterest", "Pinterest" );
				userObject.saveInBackground();
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
