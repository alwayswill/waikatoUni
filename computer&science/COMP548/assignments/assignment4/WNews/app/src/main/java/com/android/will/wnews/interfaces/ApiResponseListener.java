package com.android.will.wnews.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ApiResponseListener {

    public void onNewsListResponse(JSONObject json_object);

    public void onNewsCategoriesResponse(JSONObject json_object);

    public void onNewsDetailsResponse(JSONObject json_object);

    /*
    * handle errors, especially for timeout exception
     */
    public void onApiErrorResponse(VolleyError error);
}
