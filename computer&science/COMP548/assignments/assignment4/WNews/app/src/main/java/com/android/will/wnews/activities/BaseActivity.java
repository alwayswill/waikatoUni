package com.android.will.wnews.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.will.wnews.utils.Constants;

/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 */

/**
 * Activity that for doing basic service like checking network.
 */
public class BaseActivity extends Activity {
    public static final String TAG = "BaseActivity";
    public boolean mWaiting = false;
    public ProgressDialog mRingProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        if (savedInstanceState != null) {
            mWaiting = savedInstanceState.getBoolean(Constants.KEY_WAITING);
        }

        if (!isNetworkConnected()) {
            Toast.makeText(this, "Fail to connect to Internet.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * show the loading status over the screen.
     * @param context
     */
    public void showLoading(Context context) {
        Log.d(TAG, "showLoading");
        mWaiting = true;
        mRingProgressDialog = ProgressDialog.show(context, "Please wait ...", "Downloading data ...", true);
    }

    /**
     * hide the loading status over the screen.
     */
    public void hideLoading() {
        Log.d(TAG, "hideLoading");
        mWaiting = false;
        if (mRingProgressDialog != null && mRingProgressDialog.isShowing()) {
            mRingProgressDialog.dismiss();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        hideLoading();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.KEY_WAITING, mWaiting);
    }

    /**
     * check network, if network including WIFI and mobile is not working, we are not able to download data.
     * @return
     */
    private boolean isNetworkConnected() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(getClass().getName(), "onRestart()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getClass().getName(), "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getClass().getName(), "onResume()");
    }

    @Override
    public void onPause() {
        Log.d(getClass().getName(), "onPause()");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d(getClass().getName(), "onDestroy()");
        super.onDestroy();
    }
}
