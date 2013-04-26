package com.justshan.pinelope;


import com.parse.ParseObject;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PinDetail extends Activity {
	
	String _passedPinInfo;
	String _passedDesc;
	String _passedIMG;
	String _passedUrl;
	String _passedName;
	TextView tv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pindetail);
		
		Intent i = getIntent();
		_passedPinInfo = i.getStringExtra("DetailData");
		_passedDesc = i.getStringExtra("DetailDesc");
		_passedIMG = i.getStringExtra("DetailIMG");
		_passedUrl = i.getStringExtra("DetailUrl");
		_passedName = i.getStringExtra("DetailName");
		
		
		Log.i("URLNEW", _passedUrl);
		
		
		
		
		tv = (TextView) findViewById(R.id.desc);
		tv.setText(_passedDesc);
		
		Button bt1 = (Button) findViewById(R.id.openWeb);
        bt1.setOnClickListener(new OnClickListener() {
        	
        	/* This is my Implicit Intent. When you click on the button it 
        	 * will take you to a browser and load up the URL. 
        	 * In this case, the URL is passed from my main activity */
             public void onClick(View v) {
            	 Intent internetIntent = new Intent(Intent.ACTION_VIEW,
            			 
            			/* using the passed url here! */
            			 Uri.parse(_passedUrl));
            	 		//Log.i("URL txt", getIntent().getExtras().getString(_passedUrl));
            			 internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
            			 internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            			 startActivity(internetIntent);
              }
             }
         );
        
        Button btSave = (Button) findViewById(R.id.savePin);
        btSave.setOnClickListener(new OnClickListener() {
        	
        	/* This is my Implicit Intent. When you click on the button it 
        	 * will take you to a browser and load up the URL. 
        	 * In this case, the URL is passed from my main activity */
             public void onClick(View v) {
            	 
            	ParseObject savedPin = new ParseObject("SavedPin");
            	savedPin.put("Name", _passedName );
            	savedPin.put("Desc", _passedDesc );
            	savedPin.put("IMG", _passedIMG );
            	savedPin.put("Url", _passedUrl );
            	savedPin.saveInBackground();
 				
              }
             }
         );
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pinelope, menu);
		return true;
	}

}
