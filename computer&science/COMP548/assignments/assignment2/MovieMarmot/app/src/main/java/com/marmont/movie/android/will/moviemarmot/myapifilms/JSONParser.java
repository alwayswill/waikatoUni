package com.marmont.movie.android.will.moviemarmot.myapifilms;

import android.util.Log;

import com.android.volley.TimeoutError;
import com.marmont.movie.android.will.moviemarmot.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
 * class for parsing json from data of response from apis.
 */
public class JSONParser {

    private static final String TAG = "JSONParser";

    /*
     * parse movie object from json data.
     */
    public static Movie parseMovieJSON(JSONObject movie) {

        Movie m = new Movie();
        m.setTitle(movie.optString("title"));
        m.setPosterURL(movie.optString("urlPoster"));
        m.setMpaaRating(movie.optString("rated"));
        m.setSummary(movie.optString("plot"));
        m.setRating((float) (movie.optDouble("rating") / 2));
        m.setId(movie.optString("idIMDB"));
        return m;
    }

    /*
     * parse movies from data of apis.
     */
    public static ArrayList<Movie> parseMovieListJSON(JSONArray json) {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        try {
            for (int i = 0; i < json.length(); i++) {
                JSONArray movie_list = json.getJSONObject(i).getJSONArray("movies");
                for (int j = 0; j < movie_list.length(); j++) {

                    JSONObject movie = movie_list.getJSONObject(j);
                    movies.add(parseMovieJSON(movie));
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, "JSONException");
            e.printStackTrace();
        } catch (Exception se) {
            se.printStackTrace();
        }
        return movies;
    }

}
