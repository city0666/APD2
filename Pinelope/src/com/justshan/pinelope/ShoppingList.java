package com.justshan.pinelope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
			map.put("line1", hashMap.getString("Name"));
			map.put("line2", hashMap.getString("Desc"));
			map.put("line3", hashMap.getString("IMG"));
			map.put("line4", hashMap.getString("Url"));
			list.add(map);
		}


		// the from array specifies which keys from the map
		// we want to view in our ListView
		String[] from = { "line1", "line2","line3", "line4" };

		// the to array specifies the TextViews from the xml layout
		// on which we want to display the values defined in the from array
		int[] to = { R.id.text1 };

		// create the adapter and assign it to the listview
		SimpleAdapter adapter = new SimpleAdapter(ShoppingList.this, list,
				R.layout.list_item2, from, to);
		_listview.setAdapter(adapter);

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
