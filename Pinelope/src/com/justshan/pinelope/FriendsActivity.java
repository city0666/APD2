package com.justshan.pinelope;

import java.io.IOException;
import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.parse.Parse;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FriendsActivity extends Activity {

	ArrayList<String> arrayFriends = new ArrayList<String>();
	String listItem;
	ListView _listview;
	SimpleAdapter _adapter;
	final Handler myHandler = new Handler();



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		
		Parse.initialize(this, "etqeIZdxSX0SqLeWoABVkIEd0UOe3Q6rHzLBtt9P", "ifp8lZdLqDcL0GVzEwJ9IIco6cmkvR652uwEdtJk");

		TextView tv = (TextView) findViewById(R.id.tvWho);
		tv.setText("Hello, " + getIntent().getExtras().getString("USER") + "! Who would you like to shop for today?");

		new MyTask().execute();

	}

	private class MyTask extends AsyncTask<Void, Void, ArrayList<String>> {



		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected ArrayList<String> doInBackground(Void... params) {

			Document doc;
			String linkText = "";
			String linkHref = "";
		

			try {
				doc = Jsoup.connect("http://m.pinterest.com/" + getIntent().getExtras().getString("USER") + "/following/").get();
				//Elements names = doc.select("li a table.pinner tbody tr td span.user_name");
				Elements usernames = doc.select("li a ");
				for (Element el : usernames ) {
					linkHref = el.attr("href");
					linkText = el.text();

					String theHref = linkHref.toString();	
					theHref = theHref.replace("/", "");

					//arrayFriends.add(linkText); // add value to ArrayList
					arrayFriends.add(linkText + " (" + theHref +")"); // add value to ArrayList
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arrayFriends; //<< return ArrayList from here


		} 

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(ArrayList<String> result) {        

			// get all value from result to display in TextView


			ListView _listview = (ListView) findViewById(R.id.listView1);
			ArrayAdapter<String> _adapter = new ArrayAdapter<String>(FriendsActivity.this, R.layout.list_friends, R.id.text1, arrayFriends);

			_listview.setAdapter(_adapter);
			Log.i("ListView", "lv populated");

			_listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 



						listItem = parent.getItemAtPosition(position).toString();
							String myParent = parent.getItemAtPosition(position).toString();	

							myParent = myParent.substring(myParent.indexOf("(") + 1);
							myParent = myParent.substring(0, myParent.indexOf(")"));

							System.out.println(myParent);
							String theURL = ("http://m.pinterest.com/" + myParent);
							Log.i("URL", theURL);

							Intent intent = new Intent(FriendsActivity.this, UserBoards.class);
							//This is the information that will be sent.
							intent.putExtra("BOARDS", theURL);
							intent.putExtra("USERNAME", myParent);
							startActivity(intent);

//							WebView boardWebView = (WebView) findViewById(R.id.webview);
//							//boardWebView.setWebViewClient(new WebClient());
//							boardWebView.loadUrl(theURL);

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
	    	ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
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