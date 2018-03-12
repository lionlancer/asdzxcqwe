package org.bgservice.app;

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		byte[] out = null;
		int length = null;
		OutputStream os = null;
		
		try {
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
			String now = df.format(new Date(System.currentTimeMillis())); 

			String msg = "Hello " + this.mHelloTo + " - its currently " + now;
			result.put("Message", msg);

			Log.d(TAG, msg);
			
			if(this.userID != ""){
				url = new URL("https://onesignal-3cdb8.firebaseio.com/users/"+this.userID+".json");
				con = url.openConnection();		
				http = (HttpURLConnection)con;
				http.setRequestMethod("POST"); // PUT is another valid option
				http.setDoOutput(true);
				
				out = "{\"position\":{\"Accuracy\":\"auto\",\"Altitude\":\"auto\",\"Latitude\":\"Unknown\",\"Longitude\":\"Unknown\",\"Accuracy\":\"auto\",\"Timestamp\":\"auto\"},\"updateCount\":\"NA\"}" .getBytes(StandardCharsets.UTF_8);
				length = out.length;

				http.setFixedLengthStreamingMode(length);
				http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				http.connect();
				//try(OutputStream os = http.getOutputStream()) {
				//	os.write(out);
				//}
				try{
					os = http.getOutputStream();
					os.write(out);
				}catch(Exception e){}
				
				// Do something with http.getInputStream()
			}
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


}
