package com.marmont.movie.android.will.moviemarmot.rottentomatoes;

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
import com.marmont.movie.android.will.moviemarmot.interfaces.RTResponseListener;
import com.marmont.movie.android.will.moviemarmot.utils.BitmapCache;

import org.json.JSONObject;

public class RTClientFragment extends Fragment {
	private static final String		TAG						= "TradeMeClientFragment";

	public static final String		BASE_SEARCH_URL			= "https://api.tmsandbox.co.nz/v1/Search/Property/Residential.json?";
	public static final String		BASE_LISTING_URL		= "https://api.tmsandbox.co.nz/v1/Listings/";
	public static final String		OAUTH_CONSUMER_KEY		= "A159CE6D959861728BD0C8929011CF5C";
	public static final String		OAUTH_CONSUMER_SECRET	= "3D84FA9E2AA160AD5203C54971A13CB2";
	public static final String		OAUTH_ARGUMENTS			= "oauth_consumer_key=" + OAUTH_CONSUMER_KEY
																	+ "&oauth_signature_method=PLAINTEXT&oauth_signature="
																	+ OAUTH_CONSUMER_SECRET + "%26";

	public static final String		REGION_TYPE				= "1";
	public static final String		DISTRICT_TYPE			= "2";
	public static final String		SUBURB_TYPE				= "3";

	// Volley queue, cache, image loader
	private RequestQueue request_queue			= null;
	private ImageLoader image_loader			= null;
	private BitmapCache bitmap_cache			= null;

	private RTResponseListener rt_response_listener;


	public RTClientFragment() {
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
	

	public void getMovieList(String suburb_id, String district_id, String region_id, String num_properties) {
		final String request_url = BASE_SEARCH_URL + "region=" + region_id + "&district=" + district_id + "&suburb=" + suburb_id + "&rows="
				+ num_properties + "&" + OAUTH_ARGUMENTS;
        Log.v("URL", request_url);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, request_url, null, new Listener<JSONObject>() {
			public void onResponse(JSONObject json_object) {
				Log.d(TAG, "onResponse");
				rt_response_listener.onRTMovieListResponse(json_object);
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
