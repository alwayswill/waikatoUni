package com.android.will.wnews.apiClients;

import android.util.Log;

import com.android.will.wnews.model.News;

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
			Log.d(TAG, json.toString(2));
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

}
