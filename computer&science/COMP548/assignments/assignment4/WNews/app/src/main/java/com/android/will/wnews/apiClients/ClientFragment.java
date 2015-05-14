package com.android.will.wnews.apiClients;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.will.wnews.interfaces.ApiResponseListener;
import com.android.will.wnews.utils.BitmapCache;

import org.json.JSONObject;

public class ClientFragment extends Fragment {
	private static final String		TAG						= "ClientFragment";

	public static final String		BASE_URL			= "http://wnews.timepic.net/wnews/api/";
	public static final String		API_KEY		= "hXVBQPSennzh46Xp";
	public static final String		API_ARGUMENTS			= "key=" + API_KEY;

	// Volley queue, cache, image loader
	private RequestQueue			request_queue			= null;
	private ImageLoader				image_loader			= null;
	private BitmapCache bitmap_cache			= null;

	private ApiResponseListener trademe_response_listener;


	public ClientFragment() {
	}

	// ensure that the hosting activity implements the response listener interface
	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach()");
		super.onAttach(activity);
		try {
			trademe_response_listener = (ApiResponseListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement TradeMeResponseListener");
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
	
	// issue requests to trademe and return responses to the registered listener
	public void getMovieList() {
		final String request_url = BASE_URL+"newslist?"+API_ARGUMENTS;
		Log.d(TAG, request_url);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, request_url, null, new Listener<JSONObject>() {
			public void onResponse(JSONObject json_object) {
				Log.d(TAG, "onResponse");
				trademe_response_listener.onNewsListResponse(json_object);
			}
		}, new ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "getMovieList : onErrorResponse : " + error.getMessage());
				error.printStackTrace();
				trademe_response_listener.onNewsListResponse(null);
			}
		});

		request_queue.add(request);
	}

}
