package com.android.will.waikatopapers;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/**
 * Paper      : COMP548-15A(HAM)
 * Student ID : 125491
 * Name       : Shuzu Li
 * Email      : lishuzu@gmail.com
 *
 * Created by Shuzu Li on 14/03/15.
 *
 */

public class PaperBase extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getSimpleName(), "onCreate()");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(getClass().getSimpleName(), "onRestart()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getClass().getSimpleName(), "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getClass().getSimpleName(), "onResume()");
    }

    @Override
    public void onPause() {
        Log.d(getClass().getSimpleName(), "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(getClass().getSimpleName(), "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(getClass().getSimpleName(), "onDestroy()");
        super.onDestroy();
    }
}
