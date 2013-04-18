package com.justshan.pinelope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import android.widget.AdapterView.OnItemSelectedListener;


public class FriendsActivity extends Activity {
	
	Spinner spinner;
	ArrayList<String> arrayBoards = new ArrayList<String>();
	final Handler myHandler = new Handler();
	String spinItem;
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

			try {
				doc = Jsoup.connect("http://m.pinterest.com/" + getIntent().getExtras().getString("USER") + "/following/").get();
				Elements links = doc.select("span.user_name");
				for (Element el : links) { 
					linkText = el.text();

					arrayBoards.add(linkText); // add value to ArrayList
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arrayBoards;     //<< return ArrayList from here


		} 


		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(ArrayList<String> result) {        

			// get all value from result to display in TextView


			//Spinner spinner = (Spinner) findViewById(R.id.spinner); // Create an ArrayAdapter using the string array and a default spinner layout
			ListView _listview = (ListView) findViewById(R.id.listView1);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(FriendsActivity.this, android.R.layout.simple_list_item_1, arrayBoards);
			// Specify the layout to use when the list of choices appears
			//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Apply the adapter to the spinner
			_listview.setAdapter(adapter);
			Log.i("ListView", "lv populated");

			_listview.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
					// Your code to do something with the selected item
					
					

//					if(pos > 0){
//						spinItem = parent.getItemAtPosition(pos).toString();
//						if (spinItem.equals("Select") || spinItem == "Select"){
//						} else {
//							String myParent = parent.getItemAtPosition(pos).toString().toLowerCase();	
//							myParent = myParent.replace(' ', '-');
//							String theURL = ("http://m.pinterest.com/" +  getIntent().getExtras().getString("USER") +"/" + myParent);
//							
//							
//							WebView boardWebView = (WebView) findViewById(R.id.webview);
//							//boardWebView.setWebViewClient(new WebClient());
//							boardWebView.loadUrl(theURL);
//
//							Log.i("name", parent.getItemAtPosition(pos).toString());
//						}
//					}

				}
			});

			
		}
	}
}

