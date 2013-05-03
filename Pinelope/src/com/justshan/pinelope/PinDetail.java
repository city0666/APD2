package com.justshan.pinelope;


import java.io.InputStream;

import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PinDetail extends Activity {
	
	String _passedPinInfo;
	String _passedDesc;
	String _passedIMG;
	String _passedUrl;
	String _passedName;
	TextView tv;
	ImageView my_img;
    Bitmap mybitmap;
    ProgressDialog pd;
	
	
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
		
		
		new DisplayImageFromURL((ImageView) findViewById(R.id.imageDetail)).execute(_passedIMG);
		
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
            	savedPin.put("column", "value" );
            	savedPin.put("Name", _passedName );
            	savedPin.put("Desc", _passedDesc );
            	savedPin.put("IMG", _passedIMG );
            	savedPin.put("Url", _passedUrl );
            	savedPin.saveInBackground();
 				
              }
             }
         );
		
	}
	
	private class DisplayImageFromURL extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pd = new ProgressDialog(PinDetail.this);
            pd.setMessage("Loading...");
            pd.show();
        }
        public DisplayImageFromURL(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return mIcon11;

        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            pd.dismiss();
        }
    }

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pinelope, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    switch(item.getItemId()) {
	    case R.id.logout:
	    	ParseUser.logOut();
	    	Intent intentLogout = new Intent(this, PinelopeActivity.class);            
	    	intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	         startActivity(intentLogout);   
	        break;
	    case R.id.shoplist:
	    	Intent intentList = new Intent(this, ShoppingList.class);            
	    	intentList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	         startActivity(intentList);   
	        break;	        
    	}
	    return true;
	}

}
