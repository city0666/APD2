package com.justshan.pinelope;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PinsPage extends Activity {
	
	ListView _listview;
	JSONArray _results;
	Context _context;
	List<Map<String, String>> _pinData;
	SimpleAdapter _adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinspage);
		
		_listview = (ListView) findViewById(R.id.listView1);
		
//		ImageView imgFavorite = (ImageView) findViewById(R.id.imagePins);
//		imgFavorite.setClickable(true); 
//		imgFavorite.setOnClickListener(new OnClickListener() {
//		    @Override
//		    public void onClick(View v) {
//		    	Intent intent = new Intent(PinsPage.this, PinDetail.class);
//				//This is the information that will be sent.
//				startActivity(intent);
//		    }
//		});
		getPins("");
		
	}
	
	private void getPins(String text){
    	
    	String baseURL = getIntent().getExtras().getString("PINS" + text);
    	
    	URL finalURL;
    	try{
    		finalURL = new URL(baseURL);
    		Log.i("my url:", baseURL);
    		PinRequest pr = new PinRequest();
    		pr.execute(finalURL);
    		
    	} catch (MalformedURLException e){
    		Log.e("BAD URL", "MALFORMED URL");
    		finalURL = null;
    	}
    }
    
    public class PinRequest extends AsyncTask<URL, Void, String>{
    	
	    /* (non-Javadoc)
	     * @see android.os.AsyncTask#doInBackground(Params[])
	     */
	    @Override
    	protected String doInBackground(URL... urls){
    		String response ="";
    		for(URL url: urls){
    			response = WebChecks.getURLStringResponse(url);
    		}
    		return response;
    	}
    	
	    /* (non-Javadoc)
	     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	     */
	    @Override
    	protected void onPostExecute(String result){
    		Log.i("URL RESPONSE", result);
    		try{
					JSONObject json = new JSONObject(result);					
					JSONObject productObject = json.getJSONObject("data");
	    			JSONArray _results = productObject.getJSONArray("pins");
	    			if(_results == null){
	    				Toast toast = Toast.makeText(getApplicationContext(), "Sorry! No pins found.", Toast.LENGTH_SHORT);
						toast.show();
	    			}else{
			        //Log.i("THE RESULTS", _results.toString());
			        
					_pinData = new ArrayList<Map<String, String>>();
					
				    for(int i=0;i<_results.length();i++){	
				    	
						JSONObject s = _results.getJSONObject(i);
						Map<String, String> map = new HashMap<String, String>(2);

						String myIMG = s.getString("images");
						String quotes = myIMG.replaceAll("\"", "");
						String slashes = quotes.replaceAll("\\\\", "");					
						String myIMG2 = slashes.split("url:")[1];
						String myIMG3 = myIMG2.split(",height")[0];
						//Log.i("myIMG3", myIMG3);
						String pinner = s.getString("pinner");
						String pinnerQuotes = pinner.replaceAll("\"", "");
						String curly = pinnerQuotes.replaceAll("\\}", "");
						String pinnerName = curly.split("full_name:")[1];
						//Log.i("pinnerName", pinnerName);
						

						
						
						map.put("desc", s.getString("description"));
						map.put("img",  myIMG3);
					    map.put("url", s.getString("link"));
					    map.put("name", pinnerName);

					    //Log.i("IMG", s.getString("images"));
					    _pinData.add(map);
				        
					    // List adapter is set
				        _adapter = new SimpleAdapter(getApplicationContext(), _pinData, R.layout.pin_item,
				                new String[] {"img","desc", "url", "name"},
				                new int[] {R.id.text1});
				        _listview.setAdapter(_adapter);
				        
				        _listview.setOnItemClickListener(new OnItemClickListener() {
				        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
				        		@SuppressWarnings("unchecked")
								HashMap<String, String> o = (HashMap<String, String>) _listview.getItemAtPosition(position);
				       
				        			// Info is set to the details view via EXPLICIT intent
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
				    
				    }
			        
				} catch (JSONException e){
					Log.e("JSON", "JSON OBJECT EXCEPTION");
				}
			}
		
	};
	
	

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pinelope, menu);
		return true;
	}


}
