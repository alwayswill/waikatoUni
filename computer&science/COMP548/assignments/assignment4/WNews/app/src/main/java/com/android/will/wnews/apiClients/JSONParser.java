package com.android.will.wnews.apiClients;

import android.util.Log;

import com.android.will.wnews.model.News;
import com.android.will.wnews.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
	private static final String	TAG	= "JSONParser";

	public static News parseNewsJSON(JSONObject news) {

		News n = new News();
		n.id = news.optInt("id");
		n.title = news.optString("title");
		n.picURL = news.optString("pic");
		n.content = news.optString("content");
        n.orginalUrl = news.optString("url");
        n.timestamp = news.optString("dateline");

		return n;
	}

	public static ArrayList<News> parseNewsListJSON(JSONObject json) {
		ArrayList<News> newses = new ArrayList<News>();

		try {
			JSONArray news_list = json.getJSONArray("datas");

			for (int i = 0; i < news_list.length(); i++) {

				JSONObject news = news_list.getJSONObject(i);

				newses.add(parseNewsJSON(news));
			}
		} catch (JSONException e) {
			Log.d(TAG, "JSONException");
			e.printStackTrace();
			return newses;
		}
		return newses;
	}


	public static User parseUserJSON(JSONObject json) {
		User user = new User();

		try {

			JSONObject response = json.getJSONObject("datas");
			JSONObject userInfo = response.getJSONObject("userinfo");
			int code = response.optInt("code");
			if (code == 1){
				user.id = userInfo.optInt("id");
				user.username = userInfo.optString("username");
				user.email = userInfo.optString("email");
				user.notifications_new_message = userInfo.optBoolean("notifications_new_message");
				user.notifications_new_message_vibrate = userInfo.optBoolean("notifications_new_message_vibrate");
				user.notifications_new_message_ringtone = userInfo.optString("notifications_new_message_ringtone");
			}else{
				return user;
			}

		} catch (JSONException e) {
			Log.d(TAG, "JSONException");
			e.printStackTrace();
			return user;
		}
		return user;
	}

}
