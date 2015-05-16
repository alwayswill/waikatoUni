package com.android.will.wnews.apiClients;

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

	private ApiResponseListener mApiResponseListener;
	private int mSocketTimeout = 20000;//20 seconds


	public ClientFragment() {
	}

	// ensure that the hosting activity implements the response listener interface
	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach()");
		super.onAttach(activity);
		try {
			mApiResponseListener = (ApiResponseListener) activity;
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
		Log.i(TAG, "onCreate(Bundle) : " + getActivity().getApplicationContext().hashCode());
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
	public void getNewsList(int category_id) {
		final String request_url = BASE_URL+"newslist/category_id/"+category_id+"?"+API_ARGUMENTS;
		Log.d(TAG, request_url);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, request_url, null, new Listener<JSONObject>() {
			public void onResponse(JSONObject json_object) {
				Log.d(TAG, "getNewsList.onResponse");
				mApiResponseListener.onNewsListResponse(json_object);
			}
		}, new ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "getNewsList : onErrorResponse : " + error.getMessage());
				error.printStackTrace();
				mApiResponseListener.onNewsListResponse(null);
			}
		});

		request_queue.add(request);
	}

	// issue requests to trademe and return responses to the registered listener
	public void searchNews(String keywords) {
		final String request_url = BASE_URL+"search/keyword/"+keywords+"?"+API_ARGUMENTS;
		Log.d(TAG, request_url);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, request_url, null, new Listener<JSONObject>() {
			public void onResponse(JSONObject json_object) {
				Log.d(TAG, "searchNews.onResponse");
				mApiResponseListener.onNewsListResponse(json_object);
			}
		}, new ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "searchNews : onErrorResponse : " + error.getMessage());
				error.printStackTrace();
				mApiResponseListener.onNewsListResponse(null);
				mApiResponseListener.onApiErrorResponse(error);
			}
		});


		RetryPolicy policy = new DefaultRetryPolicy(mSocketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(policy);

		request_queue.add(request);
	}


	// issue requests to trademe and return responses to the registered listener
	public void userLogin(String username, String password) {
		final String request_url = BASE_URL+"login/?username="+username+"&password="+password+"&"+API_ARGUMENTS;
		Log.d(TAG, request_url);
		JsonObjectRequest request = new JsonObjectRequest(Method.GET, request_url, null, new Listener<JSONObject>() {
			public void onResponse(JSONObject json_object) {
				Log.d(TAG, "userLogin.onResponse");
				mApiResponseListener.onUserLoginResponse(json_object);
			}
		}, new ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "searchNews : onErrorResponse : " + error.getMessage());
				error.printStackTrace();
				mApiResponseListener.onUserLoginResponse(null);
				mApiResponseListener.onApiErrorResponse(error);
			}
		});

		RetryPolicy policy = new DefaultRetryPolicy(mSocketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		request.setRetryPolicy(policy);

		request_queue.add(request);
	}


}
