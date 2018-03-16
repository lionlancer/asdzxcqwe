package org.bgservice.app;

import java.net.*;
import java.io.*;
import java.lang.String;
import java.nio.charset.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.google.auth.oauth2.GoogleCredentials;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

public class MyService extends BackgroundService {
	
	private final static String TAG = MyService.class.getSimpleName();
	
	private String mHelloTo = "World";
	private String userID = "";
	private Number updateCount = 0;
	
	@Override
	protected JSONObject doWork() {
		JSONObject result = new JSONObject();
		
		URL url = null;
		URLConnection con = null;
		HttpURLConnection http  = null;
		//byte[] out = null;
		//int length = null;
		OutputStream os = null;
		
		try {
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
			String now = df.format(new Date(System.currentTimeMillis())); 

			String msg = "Hello " + this.mHelloTo + " - its currently " + now;
			result.put("Message", msg);

			Log.d(TAG, msg);
			
			
		} catch (JSONException e) {
		}
		
		return result;	
	}

	@Override
	protected JSONObject getConfig() {
		JSONObject result = new JSONObject();
		
		try {
			result.put("HelloTo", this.mHelloTo);
		} catch (JSONException e) {
		}
		
		return result;
	}

	@Override
	protected void setConfig(JSONObject config) {
		try {
			if (config.has("HelloTo"))
				this.mHelloTo = config.getString("HelloTo");
			
			if (config.has("userID"))
				this.userID = config.getString("userID");
			
			if (config.has("updateCount"))
				this.updateCount = config.getInt("updateCount");
		} catch (JSONException e) {
		}
		
	}     

	@Override
	protected JSONObject initialiseLatestResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onTimerEnabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTimerDisabled() {
		// TODO Auto-generated method stub
		
	}

	/*
	public void getServiceAccountAccessToken() throws IOException {
        // https://firebase.google.com/docs/reference/dynamic-links/analytics#api_authorization
        // [START get_service_account_tokens]
        FileInputStream serviceAccount = new FileInputStream("serviceAccountKey.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        credentials.refresh();
        String accessToken = credentials.getAccessToken().getTokenValue();
        long expirationTime = credentials.getAccessToken().getExpirationTime().getTime();
        // Attach accessToken to HTTPS request in the
        //   "Authorization: Bearer" header
        // After expirationTime, you must generate a new access
        //   token
        // [END get_service_account_tokens]
    }
	*/
}
