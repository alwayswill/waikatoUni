package org.stevej.android.propertyfinder.trademe;

import org.json.JSONObject;
import org.stevej.android.propertyfinder.utils.BitmapCache;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class TradeMeClientFragment extends Fragment {
	private static final String		TAG					= "TradeMeClientFragment";

	public static final String		BASE_SEARCH_URL		= "http://api.trademe.co.nz/v1/Search/Property/Residential.json?";
	public static final String		BASE_LISTING_URL	= "http://api.trademe.co.nz/v1/Listings/";

	// Volley queue and image loader
	private RequestQueue			request_queue		= null;
	private ImageLoader				image_loader		= null;

	private TradeMeResponseListener	response_listener;

	// hosting activities must implement this to handle the reponses to network requests to trademe
	public interface TradeMeResponseListener {
		public void onTradeMePropertyListResponse(JSONObject json_object);

		public void onTradeMePropertyDetailsResponse(JSONObject json_object);
	}

	public TradeMeClientFragment() {
	}

	// ensure that the hosting activity implements the response listener interface
	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach()");
		super.onAttach(activity);
		try {
			response_listener = (TradeMeResponseListener) activity;
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
		request_queue = Volley.newRequestQueue(getActivity());
		image_loader = new ImageLoader(request_queue, new BitmapCache());

		// keep state across config changes (we don't lose the queue and loader)
		setRetainInstance(true);
	}

	public ImageLoader getImageLoader() {
		return image_loader;
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

	public void getPropertyList(String suburb_id, String district_id, String region_id, String num_properties) {
		final String request_url = BASE_SEARCH_URL + "region=" + region_id + "&district=" + district_id + "&suburb=" + suburb_id + "&rows="
				+ num_properties;
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, request_url, null, new Listener<JSONObject>() {
			public void onResponse(JSONObject json_object) {
				Log.d(TAG, "onResponse");
				response_listener.onTradeMePropertyListResponse(json_object);
			}
		}, null);

		request_queue.add(request);
	}

	public void getPropertyDetails(String property_id) {
		String request_url = BASE_LISTING_URL + property_id + ".json";
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, request_url, null, new Listener<JSONObject>() {
			public void onResponse(JSONObject json_object) {
				response_listener.onTradeMePropertyDetailsResponse(json_object);
			}
		}, null);
		request_queue.add(request);
	}

}
