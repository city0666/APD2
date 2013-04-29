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

	EditText usernameU;
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
		usernameU = (EditText) findViewById(R.id.pinuser);
		password = (EditText) findViewById(R.id.userpass);
		signup = (Button) findViewById(R.id.pinsignup);

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			String myuser = currentUser.getUsername();
		  // do stuff with the user
			Intent intent = new Intent(PinelopeActivity.this, FriendsActivity.class);
			//This is the information that will be sent.
			intent.putExtra("USER", myuser);
			startActivity(intent);
		} else {
		  // show the signup or login screen
		}
		
		
		login.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {

				String theUsername = usernameU.getText().toString();
				String thePassword = password.getText().toString();
				
				ParseUser.logInInBackground(theUsername, thePassword, new LogInCallback() {
					
					@Override
					public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					    	Intent intent = new Intent(PinelopeActivity.this, FriendsActivity.class);
							//This is the information that will be sent.
							intent.putExtra("USER", usernameU.getText().toString());
							startActivity(intent);
					    	
					    } else {
					      // Signup failed. Look at the ParseException to see what happened.
					    	Log.i("NO", "NOT WORKING");
					    	// need to add a Toast
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
