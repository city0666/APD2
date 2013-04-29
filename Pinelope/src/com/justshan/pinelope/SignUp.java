package com.justshan.pinelope;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Activity {

	EditText usernameSU;
	EditText passwordSU;
	Button signUp;
	Button cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);

		Parse.initialize(this, "etqeIZdxSX0SqLeWoABVkIEd0UOe3Q6rHzLBtt9P", "ifp8lZdLqDcL0GVzEwJ9IIco6cmkvR652uwEdtJk"); 

		signUp = (Button) findViewById(R.id.pinusersignup);
		cancel = (Button) findViewById(R.id.cancel);
		//B.setVisibility(View.GONE);
		usernameSU = (EditText) findViewById(R.id.signuppinuser);
		passwordSU = (EditText) findViewById(R.id.signupuserpass);
		//et.setVisibility(View.GONE);

		signUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ParseUser user = new ParseUser();
				user.setUsername(usernameSU.getText().toString());
				user.setPassword(passwordSU.getText().toString());

				// other fields can be set just like with ParseObject

				user.signUpInBackground(new SignUpCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {

							Intent intent = new Intent(SignUp.this, PinelopeActivity.class);
							//This is the information that will be sent.
							//intent.putExtra("USERNAME", usernameSU.getText().toString());
							startActivity(intent);
						} else {
							// Sign up didn't succeed. Look at the ParseException
							// to figure out what went wrong
						}						
					}
				});

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				Intent intent = new Intent(SignUp.this, PinelopeActivity.class);
				//This is the information that will be sent.
				//intent.putExtra("USERNAME", usernameSU.getText().toString());
				startActivity(intent);


			}
		});
	}
}
