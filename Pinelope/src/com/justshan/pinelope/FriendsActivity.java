package com.justshan.pinelope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends);
		
		TextView tv = (TextView) findViewById(R.id.tvWho);
		tv.setText("Hello, " + getIntent().getExtras().getString("USER") + "! Who would you like to shop for today?");
		
		new MyTask().execute();
		
	}
	
	private class MyTask extends AsyncTask<Void, Void, List<HashMap<String, String>>> {
		
		@Override
		protected List<HashMap<String, String>> doInBackground(Void... params) {

			List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
	        HashMap<String, String> map = new HashMap<String, String>();
			try {
				Document doc = Jsoup.connect("http://m.pinterest.com/" + getIntent().getExtras().getString("USER") + "/following/").get();
				Elements formalNames = doc.select("li a table.pinner tbody tr td span.user_name");
		        Elements userNames = doc.select("li a"); 

		        for (Element formalName : formalNames) {
		            map.put("col_1", formalName.text()); 
		            fillMaps.add(map);

		            System.out.println(formalName.text()); 

		        } 
		        for (Element userName : userNames) {
		            map.put("col_2", userName.attr("href").toString());
		            fillMaps.add(map);

		            System.out.println(userName.attr("href").toString());
		        }
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return fillMaps; 

		} 

		@Override
		protected void onPostExecute(List<HashMap<String, String>> result) { 

			String[] from = new String[] {"col_1", "col_2"};
	        int[] to = new int[] { R.id.text1, R.id.text2 };
	        ListView _listview = (ListView)findViewById(R.id.listView1);

	        SimpleAdapter _adapter = new SimpleAdapter(FriendsActivity.this, fillMaps, R.layout.friends, from, to);
	        _listview.setAdapter(_adapter);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pinelope, menu);
		return true;
	}
}

