package com.marmont.movie.android.will.moviemarmot.myapifilms;

import android.util.Log;

import com.marmont.movie.android.will.moviemarmot.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
    private static final String TAG = "JSONParser";

    public static Movie parseMovieDetailsJSON(JSONObject property) {
        Movie m = new Movie();
//		try {
//			m.ListingID = property.optInt("ListingId");
//			m.Body = property.optString("Body");
//			ArrayList<Integer> photo_ids = new ArrayList<Integer>();
//			ArrayList<String> photo_thumb_urls = new ArrayList<String>();
//			ArrayList<String> photo_large_urls = new ArrayList<String>();
//			JSONArray photos = property.getJSONArray("Photos");
//			for (int j = 0; j < photos.length(); j++) {
//				JSONObject photo = photos.getJSONObject(j);
//				Integer key = photo.optInt("Key");
//				JSONObject value = photo.getJSONObject("Value");
//				String thumb_url = value.optString("Thumbnail");
//				String large_url = value.optString("Large");
//				m.photo_thumb_urls.add(thumb_url);
//				m.photo_large_urls.add(large_url);
//			}
//
//		} catch (JSONException e) {
//			Log.d(TAG, "JSONException");
//			e.printStackTrace();
//		}
        return m;
    }

    public static Movie parseMovieJSON(JSONObject movie) {

        Movie m = new Movie();
        m.setTitle(movie.optString("title"));
        m.setPosterURL(movie.optString("urlPoster"));
        m.setMpaaRating(movie.optString("rated"));
        m.setSummary(movie.optString("plot"));
        m.setRating((float) (movie.optDouble("rating") / 2));
        m.setId(movie.optString("idIMDB"));
//        TODO movie all attributes
        return m;
    }

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
