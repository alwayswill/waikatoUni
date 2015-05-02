package com.marmont.movie.android.will.moviemarmot.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface MYFResponseListener {
	public void onMYFMovieListResponse(JSONArray json_array);
    public void onMYFErrorResponse(VolleyError error);

	public void onRTMovieDetailsResponse(JSONObject json_object);
}
