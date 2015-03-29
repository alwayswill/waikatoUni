package com.android.will.waikatopapers.fragments;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.will.waikatopapers.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(getClass().getName(), "onAttach");
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        Log.d(getClass().getName(), "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(getClass().getName(), "onResume");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(getClass().getName(), "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(getClass().getName(), "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        Log.d(getClass().getName(), "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(getClass().getName(), "onStop");
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        Log.d(getClass().getName(), "onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        Log.d(getClass().getName(), "onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public void onDestroyView() {
        Log.d(getClass().getName(), "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(getClass().getName(), "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(getClass().getName(), "onDetach");
        super.onDetach();
    }
}
