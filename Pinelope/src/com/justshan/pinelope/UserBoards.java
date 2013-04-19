package com.justshan.pinelope;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import com.justshan.pinelope.R;


public class UserBoards extends Activity {
	
	ArrayList<String> arrayBoards = new ArrayList<String>();
	String listItem;
	ListView _listview;
	SimpleAdapter _adapter;
	final Handler myHandler = new Handler();
	List<Map<String, String>> _boardData;

	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boards);
	
		 _listview = (ListView) findViewById(R.id.listView1);
		//new MyBoardsTask().execute();
		
	}
	
	private Handler theHandler = new Handler(){
		public void handleMessage(Message message){
			Object path = message.obj;
			if (message.arg1 == RESULT_OK && path != null){
				//String boardlist = (String) message.obj.toString();
				Document doc;
				String linkText = "";
				String linkHref = "";
				try {
					doc = Jsoup.connect(getIntent().getExtras().getString("BOARDS")).get();
					Elements boards = doc.select("div.user_boards ul li a ");
					_boardData = new ArrayList<Map<String, String>>();
					for (Element el : boards ) {
						linkText = el.text();
						linkHref = el.attr("href");
	
						arrayBoards.add(linkText); // add value to ArrayList
					
			        
			        _boardData = new ArrayList<Map<String, String>>();
					
				    for(int i=0;i<arrayBoards.size();i++){							
						//JSONObject s = arrayBoards.getJSONObject(i);
						Map<String, String> map = new HashMap<String, String>(2);
						map.put("name", linkText);
						map.put("username", linkHref);
						map.put("url", linkHref);

					    _boardData.add(map);
				        
					    // List adapter is set
				        _adapter = new SimpleAdapter(getApplicationContext(), _boardData, R.layout.list_item2,
				                new String[] {"name","username", "url"},
				                new int[] {R.id.text1,
				                           R.id.text2});
				        _listview.setAdapter(_adapter);
				        
//				        _listview.setOnItemClickListener(new OnItemClickListener() {
//				        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
//				        		@SuppressWarnings("unchecked")
//								HashMap<String, String> o = (HashMap<String, String>) _listview.getItemAtPosition(position);
//				       
//				        			// Info is set to the details view via EXPLICIT intent
//				        			Intent intent = new Intent(getApplicationContext(), DetailPage.class);
//				        			intent.putExtra("DetailData", o.toString());
//				        			intent.putExtra("DetailName", o.get("title"));
//				        			intent.putExtra("DetailPrice", o.get("price"));
//				        			intent.putExtra("DetailUrl", o.get("url"));
//				        			startActivity(intent);
//				        		
//							}
//						});
				    }
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    
				    }
			        
				} 
			};

	
//	private class MyBoardsTask extends AsyncTask<Void, Void, ArrayList<String>> {
//
//
//
//		/* (non-Javadoc)
//		 * @see android.os.AsyncTask#doInBackground(Params[])
//		 */
//		@Override
//		protected ArrayList<String> doInBackground(Void... params) {
//
//			Document doc;
//			String linkText = "";
//			String linkHref = "";
//			
//			try {
//				doc = Jsoup.connect(getIntent().getExtras().getString("BOARDS")).get();
//				Elements boards = doc.select("div.user_boards ul li a ");
//				_boardData = new ArrayList<Map<String, String>>();
//				for (Element el : boards ) {
//					linkText = el.text();
//					linkHref = el.attr("href");
//
//					Map<String, String> map = new HashMap<String, String>(2);
//					map.put("name", linkText);
//					map.put("username", linkHref);
//
//				    _boardData.add(map);
//
//					arrayBoards.add(linkText); // add value to ArrayList
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return arrayBoards; //<< return ArrayList from here
//
//
//		} 
//
//		/* (non-Javadoc)
//		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
//		 */
//		@Override
//		protected void onPostExecute(ArrayList<String> result) {        
//
//			// get all value from result to display in TextView
//
//			Document doc;
//			String linkText = "";
//			String linkHref = "";
//			
//			doc = Jsoup.connect(getIntent().getExtras().getString("BOARDS")).get();
//			Elements boards = doc.select("div.user_boards ul li a ");
//			_boardData = new ArrayList<Map<String, String>>();
//			for (Element el : boards ) {
//				linkText = el.text();
//				linkHref = el.attr("href");
//			
//				arrayBoards.add(linkText); // add value to ArrayList
//			}
//		
//			_boardData = new ArrayList<Map<String, String>>();
//
//		    for(int i=0; i <arrayBoards.length(); i++){							
//				Map<String, String> map = new HashMap<String, String>(2);
//				map.put("name", linkText);
//				map.put("username", linkHref);
//				map.put("url", linkHref);
//
//			    _boardData.add(map);
//
//			    // List adapter is set
//		        _adapter = new SimpleAdapter(getApplicationContext(), _boardData, R.layout.list_item2,
//		                new String[] {"name","username", "url"},
//		                new int[] {R.id.text1,
//		                           R.id.text2});
//		        _listview.setAdapter(_adapter);
//
////			ListView _listview = (ListView) findViewById(R.id.boardsView);
////			ArrayAdapter<String> _adapter = new ArrayAdapter<String>(UserBoards.this, android.R.layout.simple_list_item_1, arrayBoards);
////			
////			_listview.setAdapter(_adapter);
////			Log.i("ListView", "lv populated");
//
////			_listview.setOnItemClickListener(new OnItemClickListener() {
////				
////				@Override
////				public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
////
////					
////					if(position > 0){
////						listItem = parent.getItemAtPosition(position).toString();
////							String myParent = parent.getItemAtPosition(position).toString();	
////							
////							myParent = myParent.substring(myParent.indexOf("(") + 1);
////							myParent = myParent.substring(0, myParent.indexOf(")"));
////
////							System.out.println(myParent);
////							String theURL = ("http://m.pinterest.com/" + myParent);
////							Log.i("URL", theURL);
////							
//////							Intent intent = new Intent(FriendsActivity.this, UserBoards.class);
//////							//This is the information that will be sent.
//////							intent.putExtra("BOARDS", theURL);
//////							startActivity(intent);
////							
////
////							Log.i("name", parent.getItemAtPosition(position).toString());
////					}
////
////				}
////			});
//
//			
//		}
//	}

}
