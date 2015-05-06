package com.marmont.movie.android.will.moviemarmot.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/*
the listener for reponse from myapifilm.com api
 */
public interface MYFResponseListener {
    /*
     * retrieve data from the response from api
     */
    void onMYFMovieListResponse(JSONArray json_array);

    /*
     * handle errors, especially for timeout exception
     */
    void onMYFErrorResponse(VolleyError error);
}
