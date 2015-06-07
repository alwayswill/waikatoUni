package com.android.will.wnews.apiClients;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.will.wnews.interfaces.UserLoginListener;
import com.android.will.wnews.utils.BitmapCache;
import com.android.will.wnews.utils.Constants;

import org.json.JSONObject;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 */

public class UserClientFragment extends Fragment {

    private static final String		TAG						= "UserClientFragment";


    // Volley queue, cache, image loader
    private RequestQueue request_queue			= null;
    private ImageLoader image_loader			= null;
    private BitmapCache bitmap_cache			= null;

    private UserLoginListener mUserLoginListener;
    private RetryPolicy mPolicy;

    public UserClientFragment() {
        // Required empty public constructor
    }

    // ensure that the hosting activity implements the response listener interface
    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach()");
        super.onAttach(activity);
        try {
            mUserLoginListener = (UserLoginListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ApiResponseListener");
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

        mPolicy = new DefaultRetryPolicy(Constants.REQUEST_SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

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
    // Userlogin required onUserLoginListenser
    public void userLogin(String username, String password) {
        final String request_url = String.format(Constants.API_USER_LOGIN, username, password);
        Log.d(TAG, request_url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject json_object) {
                Log.d(TAG, "userLogin.onResponse");
                mUserLoginListener.onUserLoginResponse(json_object);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "userLogin : onErrorResponse : " + error.getMessage());
                error.printStackTrace();
                mUserLoginListener.onUserLoginResponse(null);
                mUserLoginListener.onApiErrorResponse(error);
            }
        });

        request.setRetryPolicy(mPolicy);
        request_queue.add(request);
    }



}
