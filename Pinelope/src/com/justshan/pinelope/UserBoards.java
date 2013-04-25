package com.justshan.pinelope;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


public class UserBoards extends Activity {
	
	ArrayList<String> arrayBoards = new ArrayList<String>();
	String listItem;
	ListView _listview;
	SimpleAdapter _adapter;
	final Handler myHandler = new Handler();

	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boards);
	
		
		new MyBoardsTask().execute();
		
	}
	
	private class MyBoardsTask extends AsyncTask<Void, Void, ArrayList<String>> {



		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected ArrayList<String> doInBackground(Void... params) {

			Document doc;
			String linkText = "";

			try {
				doc = Jsoup.connect(getIntent().getExtras().getString("BOARDS")).get();
				Elements boards = doc.select("div.user_boards ul li a span");
				for (Element el : boards ) {
					linkText = el.text();


					//arrayFriends.add(linkText); // add value to ArrayList
					arrayBoards.add(linkText); // add value to ArrayList
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


			ListView _listview = (ListView) findViewById(R.id.boardsView);
			ArrayAdapter<String> _adapter = new ArrayAdapter<String>(UserBoards.this, R.layout.list_item2, R.id.text1, arrayBoards);
			
			_listview.setAdapter(_adapter);
			Log.i("ListView", "lv populated");

			_listview.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 

					
					String myParent = parent.getItemAtPosition(position).toString().toLowerCase().replaceAll("[^a-zA-Z0-9 ]+","");	
					myParent = myParent.replace(' ', '-');
					//String cutString = myParent.substring(0, 49);
					myParent = myParent.substring(0, Math.min(myParent.length(), 49));

					System.out.println(myParent);
					String theURL = ("https://api.pinterest.com/v3/pidgets/boards/" + getIntent().getExtras().getString("USERNAME")+ "/" + myParent + "/pins/");
					Log.i("URL", theURL);
					
							Intent intent = new Intent(UserBoards.this, PinsPage.class);
							//This is the information that will be sent.
							intent.putExtra("PINS", theURL);
							startActivity(intent);
							

					
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

}
