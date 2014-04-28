package com.example.train_android_network;

import java.io.BufferedReader;
import com.example.train_android_network.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.customadapter.MyAdapter;
import com.example.train_android_network.R.string;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	ListView etResponse;
	TextView tvIsConnected;
	String url = "http://api.randomuser.me/";
	String result = "";
	JSONObject jsonObject;
	JSONArray jsonArray;
	MyAdapter myAdapter;
	ArrayList<User> list = new ArrayList<User>();
	String data;
	Button add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_main);
		myAdapter = new MyAdapter(this, R.layout.item_user, list);

		// get reference to the views
		etResponse = (ListView) findViewById(R.id.etResponse);
		tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
		add = (Button) findViewById(R.id.add);
		etResponse.setAdapter(myAdapter);

		// check if you are connected or not
		if (isConnected()) {
			tvIsConnected.setBackgroundColor(0xFF00CC00);
			tvIsConnected.setText("You are conncted");
		} else {
			tvIsConnected.setText("You are NOT conncted");
		}
		// read data
		
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				data = readData();
				getData();
				
			}
		});
		

		

	}

	private void getData() {
		try {
			jsonObject = new JSONObject(data);
			jsonArray = jsonObject.getJSONArray("results");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject c = jsonArray.getJSONObject(i);
				JSONObject user = c.getJSONObject("user");
				String gender = user.getString("gender");
				JSONObject name = user.getJSONObject("name");
				String title = name.getString("title");
				String first = name.getString("first");
				String mail = user.getString("email");
				String phone = user.getString("phone");
				User userobj = new User();
				userobj.setGender(gender);
				userobj.setMail(mail);
				userobj.setName(title+ "."
						+ first);
				userobj.setPhone(phone);
				list.add(userobj);
				myAdapter.notifyDataSetChanged();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private String readData() {
		StringBuilder stringBuilder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);

			}
			inputStream.close();
			result = stringBuilder.toString();
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("inputStream", e.getLocalizedMessage());
		}
		return result;
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
