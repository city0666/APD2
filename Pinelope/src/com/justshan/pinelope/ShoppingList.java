package com.justshan.pinelope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class ShoppingList extends Activity {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoplist);

//		ImageView imgFavorite = (ImageView) findViewById(R.id.imagePins);
//		imgFavorite.setClickable(true); 
//		imgFavorite.setOnClickListener(new OnClickListener() {
//		    @Override
//		    public void onClick(View v) {
//		    	Intent intent = new Intent(ShoppingList.this, PinDetail.class);
//				//This is the information that will be sent.
//				startActivity(intent);
//		    }
//		});

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
	    case R.id.user:
	        //click on about item
	    	Intent intent = new Intent(this, PinelopeActivity.class);            
	         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
	         startActivity(intent);   
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
