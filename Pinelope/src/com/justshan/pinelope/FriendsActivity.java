package com.justshan.pinelope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import lazylist.LazyAdapter;
import lazylist.MyLazyAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FriendsActivity extends Activity {

	//ArrayList<String> arrayFriends = new ArrayList<String>();
	ArrayList<HashMap<String, String>> arrayFriends = new ArrayList<HashMap<String, String>>();
	String listItem;
	ListView _listview;
	//SimpleAdapter _adapter;
	MyLazyAdapter _adapter;
	final Handler myHandler = new Handler();
	GridView gridview;

	public static final String KEY_HREF = "href";
	public static final String KEY_PINNAME = "name";
	public static final String KEY_UNAME = "username";
	public static final String KEY_FNAME = "firstname";
	public static final String KEY_LNAME = "lastname";


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		
		Parse.initialize(this, "etqeIZdxSX0SqLeWoABVkIEd0UOe3Q6rHzLBtt9P", "ifp8lZdLqDcL0GVzEwJ9IIco6cmkvR652uwEdtJk");

		TextView tv = (TextView) findViewById(R.id.tvWho);
		tv.setText("Hello, " + getIntent().getExtras().getString("USER") + "! Who would you like to shop for today?");

		new MyTask().execute();

	}
	
	private class MyTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {

			Document doc;
			String linkText = "";
			String linkHref = "";
		
			ArrayList<HashMap<String, String>> arrayFriends = new ArrayList<HashMap<String, String>>();

			try {
				doc = Jsoup.connect("http://m.pinterest.com/" + getIntent().getExtras().getString("USER") + "/following/").get();
				//Elements names = doc.select("li a table.pinner tbody tr td span.user_name");
				Elements usernames = doc.select("ul.list_view td.pinner_image img");
				for (Element el : usernames ) {
					HashMap<String, String> map = new HashMap<String, String>(2); 
					linkHref = el.attr("src");
					//Log.i("IMG", linkHref);
					linkText = el.attr("alt");

					String uname = linkHref.split("avatars/")[1];
					String unamedash = uname.replace("-", "_");
					String username = unamedash.split("_")[0];
					Log.i("uname", username);
					//String theHref = linkHref.toString();	
					//theHref = theHref.replace("/", "");
					String wholename = linkText.split("of ")[1];
					String firstname = wholename.split(" ")[0];
					String lastname = wholename.split(" ")[1];
					
					map.put(KEY_HREF, linkHref);
					//Log.i("HREF", linkHref);
					map.put(KEY_PINNAME, wholename);
					//Log.i("NAME", wholename);
					map.put(KEY_UNAME, username);
					map.put(KEY_FNAME, firstname);
					map.put(KEY_LNAME, lastname);

					//arrayFriends.add(linkText); // add value to ArrayList
					arrayFriends.add(map); // add value to ArrayList
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arrayFriends; //<< return ArrayList from here

		} 

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> arrayFriends) {        

	        gridview = (GridView)findViewById(R.id.gridviewfriends);

			_adapter = new MyLazyAdapter(FriendsActivity.this, arrayFriends);
			gridview.setAdapter(_adapter);
			
			
			Log.i("ListView", "lv populated");

			gridview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
					@SuppressWarnings("unchecked")
					HashMap<String, Object> o = (HashMap<String, Object>) ((MyLazyAdapter)gridview.getAdapter()).getItemEx(position);

							String theURL = ("http://m.pinterest.com/" + o.get(KEY_UNAME).toString() );
							String theUser = o.get(KEY_UNAME).toString();
							String pinnerName = o.get(KEY_PINNAME).toString();
							
							Log.i("URL", theURL + theUser + pinnerName);

							Intent intent = new Intent(FriendsActivity.this, UserBoards.class);
							//This is the information that will be sent.
							intent.putExtra("BOARDS", theURL);
							intent.putExtra("USERNAME", theUser);
							intent.putExtra("PINNAME", pinnerName);
							startActivity(intent);

							Log.i("name", parent.getItemAtPosition(position).toString());


				}
			});


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