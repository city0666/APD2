package com.justshan.pinelope;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import lazylist.LazyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PinsPage extends Activity {
	
	GridView gridview;
	JSONArray _results;
	//ArrayList<String> arrayIMGS = new ArrayList<String>();
	public static final String KEY_NAME = "name";
	public static final String KEY_URL = "url";
	public static final String KEY_DESC = "desc";
	public static final String KEY_IMG = "img";
	LazyAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lazygrid);
	
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
    		ArrayList<HashMap<String, String>> _pinData = new ArrayList<HashMap<String, String>>();
    		try{
					JSONObject json = new JSONObject(result);					
					JSONObject productObject = json.getJSONObject("data");
	    			JSONArray _results = productObject.getJSONArray("pins");
	    			if(_results == null){
	    				Toast toast = Toast.makeText(getApplicationContext(), "Sorry! No pins found.", Toast.LENGTH_SHORT);
						toast.show();
	    			}else{
			        //Log.i("THE RESULTS", _results.toString());
			        
	    				
					
					
				    for(int i=0;i<_results.length();i++){	
				    	HashMap<String, String> map = new HashMap<String, String>(); 
						JSONObject s = _results.getJSONObject(i);
						//HashMap<String, String> map = new HashMap<String, String>(2);

						String myIMG = s.getString("images");
						String quotes = myIMG.replaceAll("\"", "");
						String slashes = quotes.replaceAll("\\\\", "");					
						String myIMG2 = slashes.split("url:")[1];
						String myIMG3 = myIMG2.split(",height")[0];
						String pinner = s.getString("pinner");
						String pinnerQuotes = pinner.replaceAll("\"", "");
						String curly = pinnerQuotes.replaceAll("\\}", "");
						String pinnerName = curly.split("full_name:")[1];
												
						map.put(KEY_DESC, s.getString("description"));
						map.put(KEY_IMG,  myIMG3);
					    map.put(KEY_URL, s.getString("link"));
					    map.put(KEY_NAME, pinnerName);

					    _pinData.add(map);
					    
					    //arrayIMGS.add(myIMG3);
					    //String value = map.get("img");
					    
					    gridview = (GridView)findViewById(R.id.gridView);
					    adapter = new LazyAdapter(PinsPage.this, _pinData);        
					    gridview.setAdapter(adapter);
				        
				        gridview.setOnItemClickListener(new OnItemClickListener() {
				        	@Override
							public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
				        		@SuppressWarnings("unchecked")
				        		HashMap<String, Object> o = (HashMap<String, Object>) ((LazyAdapter)gridview.getAdapter()).getItemEx(position);

								//HashMap<String, String> o = (HashMap<String, String>) gridview.getItemAtPosition(position);
				       
				        			Intent intent = new Intent(getApplicationContext(), PinDetail.class);
				        			intent.putExtra("DetailData", o.toString());
				        			intent.putExtra("DetailDesc", o.get(KEY_DESC).toString());
				        			intent.putExtra("DetailIMG", o.get(KEY_IMG).toString());
				        			intent.putExtra("DetailUrl", o.get(KEY_URL).toString());
				        			intent.putExtra("DetailName", o.get(KEY_NAME).toString());
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
