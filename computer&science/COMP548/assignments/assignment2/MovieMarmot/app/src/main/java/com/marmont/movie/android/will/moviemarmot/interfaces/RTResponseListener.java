package com.marmont.movie.android.will.moviemarmot.interfaces;

import org.json.JSONObject;

public interface RTResponseListener {
	public void onRTMovieListResponse(JSONObject json_object);

	public void onRTMovieDetailsResponse(JSONObject json_object);
}
