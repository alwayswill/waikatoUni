package com.marmont.movie.android.will.moviemarmot.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/*
the listener for reponse from myapifilm.com api
 */
public interface MYFResponseListener {
    /*
     * retrieve data from the response from api
     */
    public void onMYFMovieListResponse(JSONArray json_array);

    /*
     * handle errors, especially for timeout exception
     */
    public void onMYFErrorResponse(VolleyError error);
}
