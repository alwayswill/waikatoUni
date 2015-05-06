package com.marmont.movie.android.will.moviemarmot.myapifilms;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.marmont.movie.android.will.moviemarmot.interfaces.MYFResponseListener;
import com.marmont.movie.android.will.moviemarmot.utils.BitmapCache;

import org.json.JSONArray;

/*
 * the fragment for retrieved data from apis
 */
public class ClientFragment extends Fragment {
    private static final String TAG = "ClientFragment";

    public static final String BASE_URL = "http://www.myapifilms.com/";
    public static final String IN_THEATRES_MOVIE_LISTING_URL = BASE_URL + "imdb/inTheaters";
    public static final String OPENING_MOVIE_LISTING_URL = BASE_URL + "/imdb/comingSoon";
    public static final String IN_THEATRES_MOVIES = "inTheaters";
    public static final String OPENING_MOVIES = "opening";

    // Volley queue, cache, image loader
    private RequestQueue requestQueue = null;
    private ImageLoader imageLoader = null;
    private BitmapCache bitmapCache = null;

    private MYFResponseListener myfResponseListener;


    public ClientFragment() {
    }

    // ensure that the hosting activity implements the response listener interface
    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach()");
        super.onAttach(activity);
        try {
            myfResponseListener = (MYFResponseListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement RTResponseListener");
        }
    }

    /**
     * This method is called only once when the Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate(Bundle)");
        super.onCreate(savedInstanceState);

        // initialise the Volley queue and image loader
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        bitmapCache = new BitmapCache();
        imageLoader = new ImageLoader(requestQueue, bitmapCache);

        Log.i(TAG, "onCreate(Bundle) : " + getActivity().hashCode());
        Log.i(TAG, "onCreate(Bundle) : " + getActivity().getApplicationContext().hashCode());
        // keep state across config changes (we don't lose the queue and loader)
        setRetainInstance(true);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public BitmapCache getBitmapCache() {
        return bitmapCache;
    }

    public void cancelAllRequests() {
        requestQueue.cancelAll(this);

    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        cancelAllRequests();
    }

    /**
     * get movie list from api depending different type of moive, illustrate networking errors
     */
    public void getMovieList(String movieType) {

        String request_url = IN_THEATRES_MOVIE_LISTING_URL;

        if (movieType.equals(OPENING_MOVIES)) {
            request_url = OPENING_MOVIE_LISTING_URL;
        }

        Log.v(TAG, "getMovieList");
        Log.v(TAG, request_url);


        JsonArrayRequest request = new JsonArrayRequest(Method.GET, request_url, null, new Listener<JSONArray>() {
            public void onResponse(JSONArray json_array) {
                Log.d(TAG, "onResponse");
                Log.d(TAG, json_array.toString());
                myfResponseListener.onMYFMovieListResponse(json_array);
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "getMovieList : onMYFErrorResponse : " + error.getMessage());
                error.printStackTrace();
                myfResponseListener.onMYFMovieListResponse(null);
                myfResponseListener.onMYFErrorResponse(error);

            }
        });

        int socketTimeout = 20000;//20 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        requestQueue.add(request);
    }

}
