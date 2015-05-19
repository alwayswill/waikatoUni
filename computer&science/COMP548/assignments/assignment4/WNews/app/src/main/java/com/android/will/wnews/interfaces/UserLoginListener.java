package com.android.will.wnews.interfaces;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 17/05/15.
 */
public interface UserLoginListener {
    public void onAuthenticate(String username, String password);

    public void onLoginSuccessfully();

    public void onUserLoginResponse(JSONObject json_object);

    /*
    * handle errors, especially for timeout exception
    */
    public void onApiErrorResponse(VolleyError error);
}
