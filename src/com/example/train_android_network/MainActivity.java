package com.example.train_android_network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.Buffer;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.train_android_network.R.string;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	ListView etResponse;
	TextView tvIsConnected;
	String url = "http://randomuser.me";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// get reference to the views
		etResponse = (ListView) findViewById(R.id.etResponse);
		tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);

		// check if you are connected or not
		if (isConnected()) {
			tvIsConnected.setBackgroundColor(0xFF00CC00);
			tvIsConnected.setText("You are conncted");
		} else {
			tvIsConnected.setText("You are NOT conncted");
		}
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		JSONArray data = null;

		ArrayList<String> list = new ArrayList<String>();
		JSONParser jasonParser = new JSONParser();
		JSONObject jsonObject = jasonParser.getJsonFromUrl(url);

		try {
			data = jsonObject.getJSONArray("data");
			for (int i = 0; i < data.length(); i++) {
				list.add(data.getString(i));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		etResponse.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_activated_1, list));
		
	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

}
