package com.justshan.pinelope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FriendsActivity extends Activity {

	ArrayList<String> arrayFriends = new ArrayList<String>();
	List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
	String listItem;
	ListView _listview;
	SimpleAdapter _adapter;
	List<Map<String, String>> friendData;



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);

		TextView tv = (TextView) findViewById(R.id.tvWho);
		tv.setText("Hello, " + getIntent().getExtras().getString("USER") + "! Who would you like to shop for today?");

		new MyTask().execute();

	}
	
	private class MyTask extends AsyncTask<Void, Void, List<Map<String, String>>> {

		@Override
		protected List<Map<String, String>> doInBackground(Void... params) {

			friendData = new ArrayList<Map<String, String>>();
	        Map<String, String> map = new HashMap<String, String>(2);
			try {
				Document doc = Jsoup.connect("http://m.pinterest.com/" + getIntent().getExtras().getString("USER") + "/following/").get();
				Elements formalNames = doc.select("li a table.pinner tbody tr td span.user_name");
		        Elements userNames = doc.select("li a"); 

		        for (Element formalName : formalNames) {
		            map.put("fName", formalName.text()); 
		            friendData.add(map);

		            System.out.println(formalName.text()); 

		        } 
		        for (Element userName : userNames) {
		            map.put("uName", userName.attr("href").toString());
		            friendData.add(map);

		            System.out.println(userName.attr("href").toString());
		        }

			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println( map );
			return friendData; 

		} 

		@Override
		protected void onPostExecute(List<Map<String, String>> result) { 

			String[] from = new String[] {"fName", "uName"};
	        int[] to = new int[] { R.id.text1, R.id.text2 };
	        ListView _listview = (ListView)findViewById(R.id.listView1);

	        SimpleAdapter _adapter = new SimpleAdapter(FriendsActivity.this, friendData, R.layout.list_friends, from, to);
	        _listview.setAdapter(_adapter);
		}
	}
	
//	private class MyTask extends AsyncTask<Void, Void, List<Map<String, String>>> {
//
//		@Override
//		protected List<Map<String, String>> doInBackground(Void... params) {
//
//			friendData = new ArrayList<Map<String, String>>();
//	        Map<String, String> map = new HashMap<String, String>(2);
//			try {
//				Document doc = Jsoup.connect("http://m.pinterest.com/" + getIntent().getExtras().getString("USER") + "/following/").get();
//				Elements formalNames = doc.select("li a table.pinner tbody tr td span.user_name");
//		        Elements userNames = doc.select("li a"); 
//
//		        for (Element formalName : formalNames) {
//		            map.put("fName", formalName.text()); 
//		            friendData.add(map);
//
//		            System.out.println(formalName.text()); 
//
//		        } 
//		        for (Element userName : userNames) {
//		            map.put("uName", userName.attr("href").toString());
//		            friendData.add(map);
//
//		            System.out.println(userName.attr("href").toString());
//		        }
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return friendData; 
//
//		} 
//
//		@Override
//		protected void onPostExecute(List<Map<String, String>> result) { 
//
//			String[] from = new String[] {"fName", "uName"};
//	        int[] to = new int[] { R.id.text1, R.id.text2 };
//	        ListView _listview = (ListView)findViewById(R.id.listView1);
//
//	        SimpleAdapter _adapter = new SimpleAdapter(FriendsActivity.this, friendData, R.layout.list_friends, from, to);
//	        _listview.setAdapter(_adapter);
//		}
//	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pinelope, menu);
		return true;
	}
}