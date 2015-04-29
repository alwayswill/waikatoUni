package com.marmont.movie.android.will.moviemarmot.myapifilms;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 22/04/15.
 */

//public class JSONParser {
//    private static final String	TAG	= "JSONParser";
//
//    public static Movie parseMovieJSON(JSONObject json_movie) {
//
//        Movie movie = new Movie();
//        movie.title = json_movie.optString("title");
//        movie.mpaa_rating = json_movie.optString("mpaa_rating");
//        movie.id = json_movie.optString("id");
//
//        movie.synopsis = json_movie.optString("synopsis");
//
//        JSONObject json_ratings = json_movie.optJSONObject("ratings");
//        movie.rating = (float) ((5.0 * json_ratings.optInt("critics_score")) / 100.0);
//
//        JSONObject json_links = json_movie.optJSONObject("links");
//        movie.rt_url = json_links.optString("alternate");
//
//        JSONObject json_posters = json_movie.optJSONObject("posters");
//        movie.thumb_url = json_posters.optString("thumbnail");
//
//        return movie;
//    }
//
//    public static ArrayList<Movie> parseMovieListJSON(JSONObject json_movie_list) {
//        ArrayList<Movie> movies = new ArrayList<Movie>();
//
//        try {
//            Log.d(TAG, json_movie_list.toString(2));
//            JSONArray movie_list = json_movie_list.getJSONArray("movies");
//
//            for (int i = 0; i < movie_list.length(); i++) {
//
//                JSONObject movie = movie_list.getJSONObject(i);
//
//                movies.add(parseMovieJSON(movie));
//            }
//        } catch (JSONException e) {
//            Log.d(TAG, "JSONException");
//            e.printStackTrace();
//            return movies;
//        }
//        return movies;
//    }
//
//}