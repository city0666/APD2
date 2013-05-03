package com.justshan.pinelope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lazylist.LazyAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ShoppingList extends Activity {

	ListView _listview;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoplist);
		
		_listview = (ListView) findViewById(R.id.shopListView);

		Parse.initialize(this, "etqeIZdxSX0SqLeWoABVkIEd0UOe3Q6rHzLBtt9P", "ifp8lZdLqDcL0GVzEwJ9IIco6cmkvR652uwEdtJk");      

		ParseQuery query = new ParseQuery("SavedPin");
		query.whereEqualTo("column", "value");
		query.findInBackground(new FindCallback() {

			@Override
			public void done(List<ParseObject> shopList, ParseException e) {
				if (e == null) {
					initListView(shopList);
				} else {
					objectRetrievalFailed();
				}
			}

		});

	}

	protected void objectRetrievalFailed() {
		// TODO Auto-generated method stub

	}


	private void initListView(List<ParseObject> objects)
	{
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>(2);

		HashMap<String, String> map;

		for (ParseObject hashMap : objects) {
			map = new HashMap<String, String>();
			map.put("name", hashMap.getString("Name"));
			map.put("desc", hashMap.getString("Desc"));
			map.put("img", hashMap.getString("IMG"));
			map.put("url", hashMap.getString("Url"));
			list.add(map);
		}


		// the from array specifies which keys from the map
		// we want to view in our ListView
		String[] from = { "name", "desc","img", "url" };

		// the to array specifies the TextViews from the xml layout
		// on which we want to display the values defined in the from array
		int[] to = { R.id.shoptext1, R.id.shoptext2 };

		// create the adapter and assign it to the listview
		SimpleAdapter adapter = new SimpleAdapter(ShoppingList.this, list,
				R.layout.shoplistitem, from, to);
		 _listview.setAdapter(adapter);
		_listview.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
//        		@SuppressWarnings("unchecked")
//        		HashMap<String, String> o = (HashMap<String, String>) _listview.getItemAtPosition(position);
//        		
//        		 Intent internetIntent = new Intent(Intent.ACTION_VIEW,
//            			 
//             			/* using the passed url here! */
//             			 Uri.parse( o.get("DetailUrl")));
//             	 		//Log.i("URL txt", getIntent().getExtras().getString(_passedUrl));
//             			 internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
//             			 internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//             			 startActivity(internetIntent);
             			 
        		@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) _listview.getItemAtPosition(position);

        			Intent intent = new Intent(getApplicationContext(), PinDetail.class);
        			intent.putExtra("DetailData", o.toString());
        			intent.putExtra("DetailDesc", o.get("desc"));
        			intent.putExtra("DetailIMG", o.get("img"));
        			intent.putExtra("DetailUrl", o.get("url"));
        			intent.putExtra("DetailName", o.get("name"));
        			startActivity(intent);
        		
			}
		});

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
