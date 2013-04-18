package com.justshan.pinelope;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FriendsActivity extends Activity {
	
	Spinner spinner;
	ArrayList<String> arrayBoards = new ArrayList<String>();
	ArrayList<String> arrayFriends = new ArrayList<String>();
	final Handler myHandler = new Handler();
	String listItem;
	ListView _listview;
	SimpleAdapter _adapter;
	JSONArray _results;
	Context _context;
	String _passedData;
	List<Map<String, String>> _friendData;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		
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
			String myData = "";

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
					arrayBoards.add(linkText + " (" + theHref +")"); // add value to ArrayList
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arrayBoards; //<< return ArrayList from here


		} 

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(ArrayList<String> result) {        

			// get all value from result to display in TextView


			//Spinner spinner = (Spinner) findViewById(R.id.spinner); // Create an ArrayAdapter using the string array and a default spinner layout
			ListView _listview = (ListView) findViewById(R.id.listView1);
			ArrayAdapter<String> _adapter = new ArrayAdapter<String>(FriendsActivity.this, android.R.layout.simple_list_item_1, arrayBoards);
			// Specify the layout to use when the list of choices appears
			//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
			_listview.setAdapter(_adapter);
			Log.i("ListView", "lv populated");

			_listview.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 

					//HashMap<String, String> o = (HashMap<String, String>) _listview.getItemAtPosition(position);
					
					if(position > 0){
						listItem = parent.getItemAtPosition(position).toString();
							String myParent = parent.getItemAtPosition(position).toString();	
							
							myParent = myParent.substring(myParent.indexOf("(") + 1);
							myParent = myParent.substring(0, myParent.indexOf(")"));

							System.out.println(myParent);
							//myParent = myParent.replace(' ', '-');
							String theURL = ("http://m.pinterest.com/" +  getIntent().getExtras().getString("USER") +"/" + myParent);
							Log.i("URL", theURL);
							
//							WebView boardWebView = (WebView) findViewById(R.id.webview);
//							//boardWebView.setWebViewClient(new WebClient());
//							boardWebView.loadUrl(theURL);

							Log.i("name", parent.getItemAtPosition(position).toString());
					}

				}
			});

			
		}
	}
}

