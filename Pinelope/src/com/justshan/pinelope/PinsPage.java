package com.justshan.pinelope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;

public class PinsPage extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinspage);
		
		ImageView imgFavorite = (ImageView) findViewById(R.id.imagePins);
		imgFavorite.setClickable(true); 
		imgFavorite.setOnClickListener(new OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	Intent intent = new Intent(PinsPage.this, PinDetail.class);
				//This is the information that will be sent.
				startActivity(intent);
		    }
		});
		
	}

}
