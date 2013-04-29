package com.justshan.pinelope;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class PinelopeActivity extends Activity {

	EditText username;
	EditText password;
	EditText pinterestU;
	String theuser;
	Button login;
	Button signup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userentry);

		Parse.initialize(this, "etqeIZdxSX0SqLeWoABVkIEd0UOe3Q6rHzLBtt9P", "ifp8lZdLqDcL0GVzEwJ9IIco6cmkvR652uwEdtJk"); 

		login = (Button) findViewById(R.id.pinusergo);
		username = (EditText) findViewById(R.id.pinuser);
		password = (EditText) findViewById(R.id.userpass);
		signup = (Button) findViewById(R.id.pinsignup);

		
		
		
		login.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {

				String theUsername = username.getText().toString();
				String thePassword = password.getText().toString();
				
				ParseUser.logInInBackground(theUsername, thePassword, new LogInCallback() {
					
					@Override
					public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					    	Intent intent = new Intent(PinelopeActivity.this, FriendsActivity.class);
							//This is the information that will be sent.
							intent.putExtra("USER", username.getText().toString());
							startActivity(intent);
					    	
					    } else {
					      // Signup failed. Look at the ParseException to see what happened.
					    	Log.i("NO", "NOT WORKING");
					    }
					  }
					
				});				
			}
		});
		
		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(PinelopeActivity.this, SignUp.class);
				//This is the information that will be sent.
				startActivity(intent);

			}
		});
		

	}

}
