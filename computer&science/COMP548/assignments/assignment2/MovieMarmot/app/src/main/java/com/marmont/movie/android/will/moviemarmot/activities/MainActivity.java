package com.marmont.movie.android.will.moviemarmot.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.marmont.movie.android.will.moviemarmot.R;
import com.marmont.movie.android.will.moviemarmot.fragments.MovieDetailsFragment;
import com.marmont.movie.android.will.moviemarmot.fragments.MovieListFragment;
import com.marmont.movie.android.will.moviemarmot.interfaces.MYFResponseListener;
import com.marmont.movie.android.will.moviemarmot.interfaces.MovieSelectionListener;
import com.marmont.movie.android.will.moviemarmot.model.Movie;
import com.marmont.movie.android.will.moviemarmot.myapifilms.ClientFragment;
import com.marmont.movie.android.will.moviemarmot.myapifilms.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends Activity implements MYFResponseListener, MovieSelectionListener, ActionBar.OnNavigationListener {

    private static final String TAG = "MainActivity";
    private static final String Movie_DETAILS_FRAGMENT_TAG = "MovieDetailsFragment";
    private static final String RT_CLIENT_FRAGMENT_TAG = "ClientFragment";
    private static final String Movie_LIST_FRAGMENT_TAG = "MovieListFragment";
    private static final String SELECTED_FILTER_IDX = "SELECTED_FILTER_IDX";
    private static final int NO_SELECTION = -1;
    public static final String DATA_SOURCE_URL = "http://www.myapifilms.com";

    private int selected_movie_position = NO_SELECTION;
    private boolean movie_filter_changed = false;


    private ArrayAdapter<String> action_bar_nav_adapter;
    private FragmentManager fragment_manager;
    private ClientFragment rt_client_fragment;


    private ShareActionProvider share_action_provider;
    private Intent share_intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
        sliding_layout.setSliderFadeColor(getResources().getColor(android.R.color.transparent));
        sliding_layout.setPanelSlideListener(new SliderListener());
        sliding_layout.openPane();

        fragment_manager = getFragmentManager();
        addNonUIFragments();

        setUpShareIntent();
        initActionBar();

        if (!isNetworkConnected()) {
            Toast.makeText(this, "Fail to connect to Internet.", Toast.LENGTH_SHORT).show();
        }

        if (savedInstanceState != null) {
            selected_movie_position = savedInstanceState.getInt(SELECTED_FILTER_IDX);
            getActionBar().setSelectedNavigationItem(selected_movie_position);
            Log.d(TAG, "onCreate() : retrieved selected filter position = " + selected_movie_position);
        } else {
            Log.d(TAG, "onCreate() is null");
        }

    }

    private void addNonUIFragments() {
        Log.d(getClass().getName(), "addNonUIFragments()");

        rt_client_fragment = (ClientFragment) fragment_manager.findFragmentByTag(RT_CLIENT_FRAGMENT_TAG);

        FragmentTransaction ft = fragment_manager.beginTransaction();

        if (rt_client_fragment == null) {
            rt_client_fragment = new ClientFragment();
            ft.add(rt_client_fragment, RT_CLIENT_FRAGMENT_TAG);
        }

        ft.commit();
        fragment_manager.executePendingTransactions();
    }


    private void setUpShareIntent() {
        share_intent = new Intent(Intent.ACTION_SEND);
        share_intent.setType("*/*");
    }

    public void initActionBar() {
        Log.d(TAG, "initActionBar");
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        String[] movie_menu_list = getResources().getStringArray(R.array.movie_filter_menu);

        action_bar_nav_adapter = new ArrayAdapter<String>(actionBar.getThemedContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1);
        action_bar_nav_adapter.addAll(movie_menu_list);

        actionBar.setListNavigationCallbacks(action_bar_nav_adapter, this);
    }

    @Override
    public void onMYFMovieListResponse(JSONArray json_array) {
        Log.d(getClass().getName(), "onMYFMovieListResponse()");
        MovieListFragment movie_list_fragment = (MovieListFragment) fragment_manager.findFragmentByTag(Movie_LIST_FRAGMENT_TAG);
        if (movie_list_fragment != null) {

            movie_list_fragment.update(JSONParser.parseMovieListJSON(json_array), movie_filter_changed);
        }
    }

    @Override
    public void onMYFErrorResponse(VolleyError error) {
        Toast.makeText(this, "Network Exception, Please try again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRTMovieDetailsResponse(JSONObject json_object) {

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        String movieType = ClientFragment.IN_THEATRES_MOVIES;

        if (selected_movie_position != 0) {
            movieType = ClientFragment.OPENING_MOVIES;
        }

        Log.d(TAG, "onNavigationItemSelected : " + itemPosition + ":" + itemId);

        movie_filter_changed = selected_movie_position != itemPosition;
        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);

        selected_movie_position = itemPosition;
        Log.v(TAG, "selected_movie_position:" + selected_movie_position + "--------itemPosition:" + itemPosition);

        rt_client_fragment.getMovieList(movieType);

        MovieListFragment movie_list_fragment = (MovieListFragment) fragment_manager.findFragmentByTag(Movie_LIST_FRAGMENT_TAG);
        if (movie_list_fragment != null) {
            movie_list_fragment.setIsLoading(true);
        }

        if (movie_filter_changed) {
            MovieDetailsFragment movie_details_fragment = (MovieDetailsFragment) fragment_manager
                    .findFragmentByTag(Movie_DETAILS_FRAGMENT_TAG);
            if (movie_details_fragment != null) {
                movie_details_fragment.clear();

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && sliding_layout.isOpen() == false) {
                    Log.d(TAG, "close slide");
                    sliding_layout.openPane();
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        MovieListFragment  movie_list_fragment = (MovieListFragment) fragment_manager.findFragmentByTag(Movie_LIST_FRAGMENT_TAG);

        switch (item.getItemId()) {
            case android.R.id.home:
                SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
                if (sliding_layout.isOpen() == false) {
                    sliding_layout.openPane();
                }
                return true;

            case R.id.action_legal_notices:
                Toast.makeText(this, "Legal", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {

        Log.d(getClass().getName(), "onMovieSelected() : " + movie.Title);

        MovieDetailsFragment movie_details_fragment = (MovieDetailsFragment) fragment_manager
                .findFragmentByTag(Movie_DETAILS_FRAGMENT_TAG);
        if (movie_details_fragment != null) {
            movie_details_fragment.update(movie);
        }

        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
        sliding_layout.closePane();

    }


    public ImageLoader getImageLoader() {
        return rt_client_fragment.getImageLoader();
    }


    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        if (rt_client_fragment != null) {
            rt_client_fragment.cancelAllRequests();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        int selected_filter_idx = getActionBar().getSelectedNavigationIndex();
        outState.putInt(SELECTED_FILTER_IDX, selected_filter_idx);
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState() : stored selected filter position = " + selected_filter_idx);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu()");

        getMenuInflater().inflate(R.menu.menu_main, menu);

        share_action_provider = (ShareActionProvider) menu.findItem(R.id.action_share).getActionProvider();
        share_action_provider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        share_action_provider.setShareIntent(share_intent);

        return true;
    }


    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);
        if (sliding_layout.isOpen()) {
            super.onBackPressed();
        } else {
            sliding_layout.openPane();
        }
    }

    public void DBSourceHomepage(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(DATA_SOURCE_URL));
        startActivity(intent);
    }


    private class SliderListener extends SlidingPaneLayout.SimplePanelSlideListener {
        @Override
        public void onPanelOpened(View panel) {
            getActionBar().setHomeButtonEnabled(false);
            getActionBar().setDisplayHomeAsUpEnabled(false);
        }

        @Override
        public void onPanelClosed(View panel) {
            getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    //    check network, if network including WIFI and mobile is not working, we are not able to download movies.
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
}
