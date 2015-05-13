package com.android.will.wnews.interfaces;

import org.json.JSONObject;

public interface ApiResponseListener {
	public void onNewsListResponse(JSONObject json_object);

	public void onNewsDetailsResponse(JSONObject json_object);
}
