package com.android.will.wnews.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.android.will.wnews.model.User;

import java.util.HashMap;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 * <p/>
 * Created by Shuzu Li on 17/05/15.
 */


public class UserSession {
    // Shared Preferences reference
    SharedPreferences mPreference;

    // Editor reference for Shared preferences
    SharedPreferences.Editor mEditor;

    // Context
    Context mContext;

    // Shared mPreference mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREFERENCE_FILE_NAME = "mNewsUserPreference";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "isUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USER_NAME = "userName";

    // Email address (make variable public to access from outside)
    public static final String KEY_USER_EMAIL = "userEmail";

    // Constructor
    public UserSession(Context context){
        this.mContext = context;
        mPreference = mContext.getSharedPreferences(PREFERENCE_FILE_NAME, PRIVATE_MODE);
        mEditor = mPreference.edit();
    }

    //Create login session
    public void createUserLoginSession(User user){
        // Storing login value as TRUE
        mEditor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in mPreference
        mEditor.putString(KEY_USER_NAME, user.username);

        // Storing email in mPreference
        mEditor.putString(KEY_USER_EMAIL, user.email);

        // commit changes
        mEditor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        return this.isUserLoggedIn();
//        if(this.isUserLoggedIn()){
//
//            return true;
//        }
//        return false;
    }



    /**
     * Get stored session data
     * */
    public User getUserDetails(){
        User user = new User();
        user.email = mPreference.getString(KEY_USER_NAME, null);
        user.username = mPreference.getString(KEY_USER_EMAIL, null);

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        mEditor.clear();
        mEditor.commit();
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return mPreference.getBoolean(IS_USER_LOGIN, false);
    }
}
