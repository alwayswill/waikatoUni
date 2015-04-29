package com.marmont.movie.android.will.moviemarmot.myapifilms;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.marmont.movie.android.will.moviemarmot.interfaces.RTResponseListener;
import com.marmont.movie.android.will.moviemarmot.utils.BitmapCache;

import org.json.JSONArray;
import org.json.JSONObject;

public class ClientFragment extends Fragment {
	private static final String		TAG						= "ClientFragment";

    public static final String		BASE_URL			= "http://www.myapifilms.com/";
	public static final String		BASE_SEARCH_URL			= "http://www.myapifilms.com/title";
	public static final String		BASE_LISTING_URL		= "http://www.myapifilms.com/imdb/inTheaters";

	// Volley queue, cache, image loader
	private RequestQueue request_queue			= null;
	private ImageLoader image_loader			= null;
	private BitmapCache bitmap_cache			= null;

	private RTResponseListener rt_response_listener;


	public ClientFragment() {
	}

	// ensure that the hosting activity implements the response listener interface
	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach()");
		super.onAttach(activity);
		try {
			rt_response_listener = (RTResponseListener) activity;
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
		request_queue = Volley.newRequestQueue(getActivity().getApplicationContext());
		bitmap_cache = new BitmapCache();
		image_loader = new ImageLoader(request_queue, bitmap_cache);

		Log.i(TAG, "onCreate(Bundle) : "+getActivity().hashCode());
		Log.i(TAG, "onCreate(Bundle) : "+getActivity().getApplicationContext().hashCode());
		// keep state across config changes (we don't lose the queue and loader)
		setRetainInstance(true);
	}

	public ImageLoader getImageLoader() {
		return image_loader;
	}

	public BitmapCache getBitmapCache() {
		return bitmap_cache;
	}

	public void cancelAllRequests() {
		request_queue.cancelAll(this);

	}

	@Override
	public void onStop() {
		Log.d(TAG, "onStop");
		super.onStop();
		cancelAllRequests();
	}
	

	public void getMovieList() {
		final String request_url = BASE_LISTING_URL;
        Log.v("URL", "getMovieList");
        Log.v("URL", request_url);


		JsonArrayRequest request = new JsonArrayRequest(Method.GET, request_url, null, new Listener<JSONArray>() {
			public void onResponse(JSONArray json_array) {
				Log.d(TAG, "onResponse");
                Log.d(TAG, json_array.toString());
				rt_response_listener.onRTMovieListResponse(json_array);
			}
		}, new ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "getMovieList : onErrorResponse : " + error.getMessage());
				error.printStackTrace();
				rt_response_listener.onRTMovieListResponse(null);
			}
		});

		request_queue.add(request);
	}

}
