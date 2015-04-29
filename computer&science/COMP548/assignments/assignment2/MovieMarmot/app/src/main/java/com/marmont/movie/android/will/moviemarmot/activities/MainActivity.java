package com.marmont.movie.android.will.moviemarmot.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.marmont.movie.android.will.moviemarmot.R;
import com.marmont.movie.android.will.moviemarmot.fragments.MovieDetailsFragment;
import com.marmont.movie.android.will.moviemarmot.fragments.MovieListFragment;
import com.marmont.movie.android.will.moviemarmot.interfaces.MovieSelectionListener;
import com.marmont.movie.android.will.moviemarmot.interfaces.RTResponseListener;
import com.marmont.movie.android.will.moviemarmot.model.Movie;
import com.marmont.movie.android.will.moviemarmot.rottentomatoes.JSONParser;
import com.marmont.movie.android.will.moviemarmot.rottentomatoes.RTClientFragment;

import org.json.JSONObject;


public class MainActivity extends Activity implements RTResponseListener, MovieSelectionListener, ActionBar.OnNavigationListener {

    private static final String TAG = "MainActivity";
    private static final String Movie_DETAILS_FRAGMENT_TAG = "MovieDetailsFragment";
    private static final String RT_CLIENT_FRAGMENT_TAG = "RTClientFragment";
    private static final String Movie_LIST_FRAGMENT_TAG = "MovieListFragment";
    private static final String SELECTED_FILTER_IDX = "SELECTED_FILTER_IDX";
    private static final int NO_SELECTION = -1;

    // stores position of currently selected suburb from menu so that we can store/restore it on config change
    private int selected_movie_position = NO_SELECTION;
    private boolean movie_filter_changed = false;


    private ArrayAdapter<String> action_bar_nav_adapter;
    private FragmentManager fragment_manager;
    private RTClientFragment rt_client_fragment;

    // we set these up but aren't using them (yet)
    private ShareActionProvider share_action_provider;
    private Intent share_intent;
    private MenuItem search_menu_item;
    private SearchView search_view;

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

        if (savedInstanceState != null) {
            selected_movie_position = savedInstanceState.getInt(SELECTED_FILTER_IDX);
            getActionBar().setSelectedNavigationItem(selected_movie_position);
            Log.d(TAG, "onCreate() : retrieved selected filter position = " + selected_movie_position);
        }else{
            Log.d(TAG, "onCreate() is null");
        }


    }

    private void addNonUIFragments() {
        Log.d(getClass().getName(), "addNonUIFragments()");

        rt_client_fragment = (RTClientFragment) fragment_manager.findFragmentByTag(RT_CLIENT_FRAGMENT_TAG);

        FragmentTransaction ft = fragment_manager.beginTransaction();

        if (rt_client_fragment == null) {
            rt_client_fragment = new RTClientFragment();
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
    public void onRTMovieListResponse(JSONObject json_object) {
        Log.d(getClass().getName(), "onRTMovieListResponse()");
        MovieListFragment movie_list_fragment = (MovieListFragment) fragment_manager.findFragmentByTag(Movie_LIST_FRAGMENT_TAG);
        if (movie_list_fragment != null) {
            movie_list_fragment.update(JSONParser.parseMovieListJSON(json_object), movie_filter_changed);
        }
    }

    @Override
    public void onRTMovieDetailsResponse(JSONObject json_object) {

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {

        Log.d(TAG, "onNavigationItemSelected : " + itemPosition + ":"+itemId);

        movie_filter_changed = selected_movie_position != itemPosition;
        SlidingPaneLayout sliding_layout = (SlidingPaneLayout) findViewById(R.id.sliding_layout);

        selected_movie_position = itemPosition;
        Log.v(TAG, "selected_movie_position:"+selected_movie_position+"--------itemPosition:"+itemPosition);
        String[] movie_filter_menu_ids = getResources().getStringArray(R.array.movie_filter_menu_ids);
        String filter_id = movie_filter_menu_ids[itemPosition];

        rt_client_fragment.getMovieList(filter_id, "16", "14", "30");

        MovieListFragment movie_list_fragment = (MovieListFragment) fragment_manager.findFragmentByTag(Movie_LIST_FRAGMENT_TAG);
        if (movie_list_fragment != null) {
            movie_list_fragment.setIsLoading(true);
        }

        if (movie_filter_changed) {
            MovieDetailsFragment movie_details_fragment = (MovieDetailsFragment) fragment_manager
                    .findFragmentByTag(Movie_DETAILS_FRAGMENT_TAG);
            if (movie_details_fragment != null) {
                movie_details_fragment.clear();

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && sliding_layout.isOpen() == false){
//                    Log.d(TAG, String.valueOf(sliding_layout.isOpen()));
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


    // make sure all pending network requests are cancelled when this activity stops
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

        search_menu_item = (MenuItem) menu.findItem(R.id.action_search);
        search_view = (SearchView) search_menu_item.getActionView();

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

    public void RTHomepage(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.rottentomatoes.com"));
        startActivity(intent);
    }


    /**
     * This panel slide listener updates the action bar accordingly for each panel state.
     */
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
}
