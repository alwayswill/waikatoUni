package com.android.will.wnews.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


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

    // All Shared Preferences Keys



    // Constructor
    public UserSession(Context context){
        this.mContext = context;
        mPreference = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mPreference.edit();
    }

    //Create login session
    public void createUserLoginSession(User user){
        // Storing login value as TRUE
        mEditor.putBoolean(Constants.IS_USER_LOGIN, true);

        mEditor.putInt(Constants.KEY_USER_ID, user.id);

        // Storing name in mPreference
        mEditor.putString(Constants.KEY_USER_NAME, user.username);

        // Storing email in mPreference
        mEditor.putString(Constants.KEY_USER_EMAIL, user.email);
        // Storing notifications_new_message in mPreference
        mEditor.putBoolean(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE, user.notifications_new_message);
        // Storing notifications_new_message_vibrate in mPreference
        mEditor.putBoolean(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE_VIBRATE, user.notifications_new_message_vibrate);
        // Storing notifications_new_message_ringtone in mPreference
        mEditor.putString(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE_RINGTONE, user.notifications_new_message_ringtone);

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
        user.id = mPreference.getInt(Constants.KEY_USER_ID, 0);
        user.email = mPreference.getString(Constants.KEY_USER_EMAIL, null);
        user.username = mPreference.getString(Constants.KEY_USER_NAME, null);
        user.notifications_new_message = mPreference.getBoolean(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE, true);
        user.notifications_new_message_vibrate = mPreference.getBoolean(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE_VIBRATE, true);
        user.notifications_new_message_ringtone = mPreference.getString(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE_RINGTONE, Constants.DEFAULT_USER_NOTIFICATIONS_NEW_MESSAGE_RINGTONE);

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        mEditor.remove(Constants.KEY_USER_ID);
        mEditor.remove(Constants.KEY_USER_EMAIL);
        mEditor.remove(Constants.IS_USER_LOGIN);
        mEditor.remove(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE);
        mEditor.remove(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE_VIBRATE);
        mEditor.remove(Constants.KEY_SETTINGS_USER_NOTIFICATIONS_NEW_MESSAGE_RINGTONE);
        mEditor.commit();
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return mPreference.getBoolean(Constants.IS_USER_LOGIN, false);
    }
}
