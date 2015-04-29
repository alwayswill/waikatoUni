package com.marmont.movie.android.will.moviemarmot.interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

public interface RTResponseListener {
	public void onRTMovieListResponse(JSONArray json_array);

	public void onRTMovieDetailsResponse(JSONObject json_object);
}
