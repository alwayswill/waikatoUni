package com.marmont.movie.android.will.moviemarmot.rottentomatoes;

import android.util.Log;

import com.marmont.movie.android.will.moviemarmot.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParser {
	private static final String	TAG	= "JSONParser";

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
		m.Title = movie.optString("Title");
		m.PosterURL = movie.optString("PictureHref");
        m.MpaaRating = "G";
        m.setRating(3.5f);
//        TODO movie all attributes
		return m;
	}

	public static ArrayList<Movie> parseMovieListJSON(JSONObject json) {
		ArrayList<Movie> movies = new ArrayList<Movie>();

		try {
			Log.d(TAG, json.toString(2));
			JSONArray movie_list = json.getJSONArray("List");

			for (int i = 0; i < movie_list.length(); i++) {

				JSONObject movie = movie_list.getJSONObject(i);

				movies.add(parseMovieJSON(movie));
			}
		} catch (JSONException e) {
			Log.d(TAG, "JSONException");
			e.printStackTrace();
			return movies;
		}
		return movies;
	}

}
