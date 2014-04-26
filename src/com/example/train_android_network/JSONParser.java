package com.example.train_android_network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class JSONParser {
	public JSONObject getJsonFromUrl(String url){
		JSONObject jsonObject = null;
		try {
			InputStream is;
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);
			
			HttpResponse response = client.execute(post);	
			is = response.getEntity().getContent();
			
			BufferedReader bufferedReader = null;
			StringBuilder builder = new StringBuilder();
			
			String line;
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(is));
				while ((line = bufferedReader.readLine()) != null) {
					builder.append(line);
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}
			jsonObject = new JSONObject(builder.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jsonObject;
	}
	}
