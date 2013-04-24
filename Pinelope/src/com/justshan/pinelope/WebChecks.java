package com.justshan.pinelope;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class WebChecks.
 */
public class WebChecks {

	static Boolean _conn = false;
	static String _connectionType = "Unavailable";

//	public static String getConnectionType(Context context){
//		netInfo(context);
//		return _connectionType;
//	}
//
//	public static Boolean getConnectionStatus(Context context){
//		netInfo(context);
//		return _conn;
//	}

	//Checking for connectivity

	/**
 * Have network connection.
 *
 * @param context the context
 * @return true, if successful
 */
public static boolean haveNetworkConnection(final Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        final ConnectivityManager cm = (ConnectivityManager) context
                     .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
               final NetworkInfo[] netInfo = cm.getAllNetworkInfo();
               for (final NetworkInfo netInfoCheck : netInfo) {
                     if (netInfoCheck.getTypeName().equalsIgnoreCase("WIFI")) {
                            if (netInfoCheck.isConnected()) {
                                   haveConnectedWifi = true;
                            }
                     }
                     if (netInfoCheck.getTypeName().equalsIgnoreCase("MOBILE")) {
                            if (netInfoCheck.isConnected()) {
                                   haveConnectedMobile = true;
                            }
                     }
               }
        }

        return haveConnectedWifi || haveConnectedMobile;
 }

//	private static void netInfo(Context context) {
//
//	    try{
//	    	final ConnectivityManager conMgr =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//	    	final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
//	    	
//	    	if (activeNetwork != null && activeNetwork.isConnected()) {
//	    	    //notify user you are online
//	    		_connectionType = activeNetwork.getTypeName();
//	    		_conn = true;
//	    	} else {
//	    	    //notify user you are not online
//	    		_conn = false;
//	    	} 
////	        ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
////	        NetworkInfo ni = conMan.getActiveNetworkInfo();
////	        //Log.d(Log_Tag,ni.getState().toString());
////	        if(ni.isConnected()){
////	          Log.i("NETWORK",ni.toString());
////	          _connectionType = ni.getTypeName();
////	            _conn = true;
////	        } 
////	        else {
////	            _conn = false;
////	        }
//	    }
//	    catch(Exception ex)
//	    {
//	        Log.i("NETWORK", "isOnline ERROR: "+ex.toString());
//	        
//	    }
//
//	}

//	private static void netInfo(Context context) {
//		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo ni = cm.getActiveNetworkInfo();
//		if(ni!= null){
//			if(ni.isConnected()){
//				_connectionType = ni.getTypeName();
//				_conn = true;
//			} else {
//				_conn = false;
//			}
//		}
//	}
	//Build Logic to Handle Network Connectivity Issues
	/**
 * Gets the uRL string response.
 *
 * @param url the url
 * @return the uRL string response
 */
public static String getURLStringResponse(URL url){
		String response = "";
		try{
			URLConnection conn = url.openConnection();
			BufferedInputStream bin = new BufferedInputStream(conn.getInputStream());

			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer responseBuffer = new StringBuffer();

			while((bytesRead = bin.read(contentBytes)) != -1){
				response = new String(contentBytes,0,bytesRead);
				responseBuffer.append(response);
			}
			return responseBuffer.toString();		
		} catch(Exception e){
			Log.e("URL RESPONSE ERROR", "GetUtURLStringResponse");
		}

		return response;
	}

}