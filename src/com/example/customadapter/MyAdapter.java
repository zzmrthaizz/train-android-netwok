package com.example.customadapter;

import java.util.ArrayList;
import java.util.List;

import com.example.train_android_network.R;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.train_android_network.User;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class MyAdapter extends ArrayAdapter<User>{

	ArrayList<User>list = null;
	Activity context;
	int resource;
	public MyAdapter(Activity context, int resource, ArrayList<User> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.resource=resource;
		this.list=objects;
	}

	public View getView(int positon, View convertView, ViewGroup parent) {
		convertView = context.getLayoutInflater().inflate(resource, null);
		
		TextView name = (TextView) convertView.findViewById(R.id.tvname);
		TextView gender = (TextView) convertView.findViewById(R.id.tvgender);
		TextView phone = (TextView) convertView.findViewById(R.id.tvphone);
		TextView mail = (TextView) convertView.findViewById(R.id.tvmail);
		ImageView avatar=(ImageView) convertView.findViewById(R.id.img_avatar);

		User user = list.get(positon);
		name.setText(user.getName());
		gender.setText(user.getGender());
		phone.setText(user.getPhone());
		mail.setText(user.getMail());
		
		
		UrlImageViewHelper.setUrlDrawable(avatar, user.getPicture());
		
		return convertView;
	}
}
