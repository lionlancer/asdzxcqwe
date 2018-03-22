package org.bgservice.app;

import java.net.*;
import java.io.*;
import java.lang.String;
import java.nio.charset.*;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

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

		HashMap map = new HashMap();

		//URL url = null;
		String url = "";
		URLConnection con = null;
		HttpURLConnection http  = null;
		//byte[] out = null;
		//int length = null;
		OutputStreamWriter os = null;

		try {

			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String now = df.format(new Date(System.currentTimeMillis()));

			String msg = "Hello " + this.mHelloTo + " - its currently " + now;
			result.put("Message", msg);

			Log.d(TAG, msg);
			Log.d("MyService", msg);

			url = "http://lionlancer2k17.000webhostapp.com/";


			map.put("counter", updateCount+"");
			map.put("message", msg);
			//map.put("key3", "value3");

			performPostCall(url, map);


			/*
			//if(this.userID != ""){
				try{
					//url = new URL("https://onesignal-3cdb8.firebaseio.com/users/"+this.userID+".json");
					url = new URL("http://lionlancer2k17.000webhostapp.com/");
					Log.d("MyService", "http://lionlancer2k17.000webhostapp.com/, OK");
				}catch(MalformedURLException e){
					Log.d("MyService", "Error: " + e.getMessage());
					result.put("Message", "MalformedURLException thrown: " + e.getMessage());

				}
				try{
					con = url.openConnection();
					Log.d("MyService", "url.openConnection: OK");
				}catch(IOException e){
					Log.d("MyService", "Error: " + e.getMessage());
					result.put("Message", "IOException thrown: " + e.getMessage());
				}

				http = (HttpURLConnection)con;

				try{
					http.setRequestMethod("POST"); // PUT is another valid option
					Log.d("MyService", "http.setRequestMethod: OK");
				}catch(ProtocolException e){
					Log.d("MyService", "Error: " + e.getMessage());
					result.put("Message", "ProtocolException thrown: " + e.getMessage());
				}
				http.setDoOutput(true);
				//char[] message = msg.toCharArray();
				String message = "{\"counter\": \""+updateCount+"\", \"message\": \""+msg+"\"}";
				//byte[] out = "{\"position\":{\"Accuracy\":\"auto\",\"Altitude\":\"auto\",\"Latitude\":\"Unknown\",\"Longitude\":\"Unknown\",\"Accuracy\":\"auto\",\"Timestamp\":\"auto\"},\"updateCount\":\"NA\"}" .getBytes(StandardCharsets.UTF_8);
				byte[] out = message.getBytes(Charset.forName("UTF-8"));
				//byte[] out = "counter="+updateCount+"&message="+msg+"".getBytes(StandardCharsets.UTF_8);
				int length = out.length;

				//http.setFixedLengthStreamingMode(length);
				//http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				//http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

				try{
					output = new BufferedOutputStream(http.getOutputStream());

					BufferedWriter writer = new BufferedWriter (new OutputStreamWriter(output, "UTF-8"));

					String postdata = "counter="+updateCount+"&message="+msg;

					postdata = URLEncoder.encode(postdata, "UTF-8")

					writer.write(postdata);

					writer.flush();
					writer.close();

					output.close();
				}catch(){
					Log.d("MyService", "Error: " + e.getMessage());
					result.put("Message", "IOException thrown: " + e.getMessage());
				}

				try{
					//con.connect();
					http.connect();
					Log.d("MyService", "http.connect(): OK");
				}catch(IOException e){
					Log.d("MyService", "Error: " + e.getMessage());
					result.put("Message", "IOException thrown: " + e.getMessage());
				}
				//try(OutputStream os = http.getOutputStream()) {
				//	os.write(out);
				//}

				//try{
					//os = http.getOutputStream();
					//os.write(out);

				//	String postdata = "counter="+updateCount+"&message="+msg;

				//	os = new OutputStreamWriter(http.getOutputStream(), "8859_1");
				//	os.write(postdata);

				//	Log.d("MyService", "os.write(out): OK");
				//}catch(Exception e){
				//	Log.d("MyService", "Error: " + e.getMessage());
				//	result.put("Message", "Exception thrown: " + e.getMessage());
				//}

				// Do something with http.getInputStream()

				try{

					BufferedReader r = new BufferedReader(new InputStreamReader(http.getInputStream(), Charset.forName("UTF-8")));

					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						sb.append(line);
					}
					Log.d("MyService", "Returned data:");
					Log.d("MyService", sb.toString());
					//System.out.println(sb.toString());
				}catch(IOException e){
					Log.d("MyService", "Error: " + e.getMessage());
					result.put("Message", "IOException thrown: " + e.getMessage());
				}
			//}
			*/
		} catch (JSONException e) {
		}

		return result;
	}

	public String  performPostCall(String requestURL, HashMap<String, String> postDataParams) {

		URL url;
		String response = "";
		try {
			url = new URL(requestURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			Log.d("MyService", "POSTING TO: " + requestURL);
			Log.d("MyService", "POSTING DATA: " + postDataParams);

			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(os, "UTF-8"));
			writer.write(getPostDataString(postDataParams));

			writer.flush();
			writer.close();
			os.close();
			int responseCode=conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line=br.readLine()) != null) {
					response+=line;
				}
				
				Log.d("MyService", "RESPONSE: " + response);
				
			}
			else {
				response="";
				Log.d("MyService", "NO RESPONSE:");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for(Map.Entry<String, String> entry : params.entrySet()){
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}

		return result.toString();
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
